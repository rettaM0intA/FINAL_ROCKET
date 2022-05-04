import java.io.EOFException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.Flow.Subscriber;

import Enums.FileSelect;

public class UserInput {

    private Encryptor encryptor;
    private AstronautInfo astroInfo;
    private RocketInfo rocketInfo;
    private PasswordCreator passwordCreator;

    private Scanner keyboard = new Scanner(System.in);

    private String[] inputs = new String[21];
    
    private static Double astronauts; //How many astronauts are going to be on the ship

    /**
     * Creates a user input with default variables.
     */
    public UserInput(){
        astroInfo = new AstronautInfo();
        rocketInfo = new RocketInfo();
        passwordCreator = new PasswordCreator();

        try{
            encryptor = new Encryptor(astroInfo.fileName, rocketInfo.fileName, passwordCreator.fileName);
        } catch (Exception FileAlreadyExistsException) {
            System.out.print(FileAlreadyExistsException.getMessage());
        }
    }

    /**
     * This code should be run immediatly every time the code is initiated.  
     * It checks to see if an admin password exists. It will make a new one and tell the user it if none is there.
     */
    public void FirstRun(){
        if(passwordCreator.CreateAdminPassword(encryptor)){
            try {
                System.out.print("Admin Password: " + encryptor.getUnencrypted(FileSelect.password, 0) + "\n");
                encryptor.toEncrypt(passwordCreator.GeneratePassword(), FileSelect.password, 1);
                System.out.print("\nHello and welcome new admin. The following are the key and the deletion password password. Keep them safe and only share with employees.\n"+
                "Deletion password: " + encryptor.getUnencrypted(FileSelect.password, 1) +
                "\nKey: " + encryptor.getCurrentSeed());
            } catch (IOException encryptorBroke) {
                encryptorBroke.printStackTrace();
            }
        }

    }

    /**
     * Call to use the main menu.
     * @return a number 0-4 which means the chosen option.
     * 1: wants to edit astronauts
     * 2: wants to edit rockets
     * 3: wants to configure launch settings
     * 4: wants to test a launch
     * 5: wants to quit the program
     * May add more later.
     */
    public int MainMenu(){
        inputs[0] = "-1";
        do {
            System.out.print("\nMain menu. Please enter one of the following options.\n" + 
            "1: Edit Astronauts\n" + 
            "2: Edit Rockets\n" +
            "3: Configure Simulation\n" + 
            "4: Initiate Simulation (use 3 first)\n" + 
            "5: Quit program\n");
            inputs[0] = keyboard.nextLine();
            try{
                int test = Integer.parseInt(inputs[0]);
                if(test < 0 || test > 5)
                throw new NumberFormatException();
            } catch(NumberFormatException invalidInput){
                System.out.print("\nInvalid input.\nMake sure you enter only the number you want to select");
                inputs[0] = "-1";
            }
        } while (Integer.parseInt(inputs[0]) < 0 || Integer.parseInt(inputs[0]) > 5);

        return Integer.parseInt(inputs[0]);
    }

    /**
     * Use to request the seed from the user.
     */
    public void EnterSeed(){
        
        int seed = 0;

        do {
                
            System.out.print("\nEnter the key given by the Admin.\n");
            inputs[0] = keyboard.nextLine();

            try{
            seed = Integer.parseInt(inputs[0]);
            } catch(NumberFormatException invalidInput){
                System.out.print("\nInvalid input.");
                seed = 0;
            }

            if(seed < 0){
                System.out.print("\nThe code must be a positive number.");
            }

        } while (seed <= 0);

        try {
            encryptor = new Encryptor(astroInfo.fileName, rocketInfo.fileName, passwordCreator.fileName, seed);
        } catch (Exception invalidFilePath) {
            //END CODE. THIS MEANS SOMEONE IS MESSING WITH FILES DURING OPERATION OR THERE WAS IMPROPER AUGNMENTATION OF THE CODE.
            invalidFilePath.printStackTrace();
        }
    }

    /**
     * Used by the admin to edit and generate other employee passwords.
     */
    public void EditEmployees(){

        boolean active = true;
        int passwordAmount = 1;
        inputs[0] = "";
        
        while(YesOrNoChecker(inputs[0]) == 3){
        System.out.print("\nWould you like to edit the list of employee passwords?\n"+
        "Enter Yes or No\n");
        inputs[0] = keyboard.nextLine();
        
        }
        if(YesOrNoChecker(inputs[0]) == 2){
            return;
        }

        while(active){

            System.out.print("Do you want to add a new employee or delete an employee?\n"+
            "Enter Y to add an employee.\n"+
            "Enter N to delete an employee.\n");
            inputs[0] = keyboard.nextLine();

            if(YesOrNoChecker(inputs[0]) == 1){
                System.out.print("Are you sure you want to add an employee?\n");
                inputs[0] = keyboard.nextLine();

                if(YesOrNoChecker(inputs[0]) == 1){
                    passwordAmount = encryptor.HowManyItemsInFile(FileSelect.password) + 1;
                    try {
                        encryptor.toEncrypt(passwordCreator.GeneratePassword(), FileSelect.password, passwordAmount);
                        System.out.print("New password generated. Password for employee #" + passwordAmount+
                        "\nThe password for this employee is: " + encryptor.getUnencrypted(FileSelect.password, passwordAmount) + "\n");
                    } catch (IOException passwordNumberTooHigh) {
                        System.out.print("ERROR: did not create password.");
                    }
                }

            }else if(YesOrNoChecker(inputs[0]) == 2){
                System.out.print("Are you sure you want to remove an employee?");
                inputs[0] = keyboard.nextLine();
                if(YesOrNoChecker(inputs[0]) == 1){
                    while(active){
                        System.out.print("Enter the assigned number of that employee.");
                        inputs[1] = keyboard.nextLine();

                        System.out.print("Are you sure you want to delete Employee #" + inputs[1] + "? Reminder that this would change the id of all employees after employee #"+ inputs[1] +"\nYes/No");
                        inputs[0] = keyboard.nextLine();
                        if(YesOrNoChecker(inputs[0]) != 3)
                        active = false;
                    }
                    active = true;
                    if(YesOrNoChecker(inputs[0]) == 1){
                        
                        try {
                            if(TheseStringsAreEqual(inputs[1], "0") || Integer.parseInt(inputs[1]) < 1)
                            throw new IOException();
                            encryptor.toEncrypt(passwordCreator.GeneratePassword(), FileSelect.password, Integer.parseInt(inputs[1]));
                            System.out.print("Eployee #" + inputs[1] + "succesfully deleted.");
                        } catch (IOException passwordNumberTooHigh) {
                            System.out.print("ERROR: Failed to properly delete employee.");
                        }
                    }
                }
            }

            inputs[0] = " ";
            while(YesOrNoChecker(inputs[0]) == 3){
            System.out.print("Do you still want to edit employees?\n"+
            "Y/N");
            inputs[0] = keyboard.nextLine();

            }
            if(YesOrNoChecker(inputs[0]) == 2)
            active = false;


        }

    }
    
    /**
     * Has the user create a new astronaut and add them to the list.
     */
    public void EditAstronauts() {

        inputs[15] = " ";
        boolean confirmation = false;

        while(YesOrNoChecker(inputs[15]) != 1){

            System.out.print("Do you want to add an Astronaut?\nY/N\n");
            inputs[10] = keyboard.nextLine();

            if(YesOrNoChecker(inputs[10]) == 1){
       
                inputs[10] = "";

                while(YesOrNoChecker(inputs[10]) != 2){
                    System.out.println("What is the astronauts name?");
                    inputs[0] = keyboard.nextLine();
                    System.out.println("What is the astronauts Email?");
                    inputs[1] = keyboard.nextLine();
                    System.out.println("What is the astronauts address?");
                    inputs[2] = keyboard.nextLine();
                    System.out.println("What is the astronauts phone number?");
                    inputs[3] = keyboard.nextLine();
                    System.out.println("What is the astronauts date of birth?");
                    inputs[4] = keyboard.nextLine();
                    System.out.println("What is the astronauts next of kin?");
                    inputs[5] = keyboard.nextLine();
                    System.out.println("What is the astronauts rank?");
                    inputs[6] = keyboard.nextLine();
                    confirmation = false;
                    while(!confirmation){
                        try{
                            System.out.println("What does the astronaut weigh in pounds?");
                            inputs[7] = keyboard.nextLine();
                            inputs[7] = Double.toString(Double.parseDouble(inputs[7]));
                            confirmation = true;
                        }catch(InputMismatchException invalidInput){
                            System.out.print("Invalid input. Use only numbers.\n");
                        }
                    }
                    confirmation = false;
                    while(!confirmation){
                        try{
                            System.out.println("What is the astronauts pay in cents?");
                            inputs[8] = keyboard.nextLine();
                            inputs[8] = Double.toString(Double.parseDouble(inputs[8]));
                            confirmation = true;
                        }catch(InputMismatchException invalidInput){
                            System.out.print("Invalid input. Use only numbers.\n");
                        }
                    }
                    System.out.println("What is the astronauts social security number?");
                    inputs[9] = keyboard.nextLine();

                        while(YesOrNoChecker(inputs[11]) != 1){
                        //Display input information for verification.
                            System.out.println("The astronauts info: \nName: " + inputs[0] + "\nEmail: " + inputs[1] + "\nAddress: " + inputs[2] +
                            "\nPhone number: " + inputs[3] + "\nDate of birth: " + inputs[4] + "\nNext of kin: " + inputs[5] + 
                            "\nRank: " + inputs[6] + "\nWeight " + inputs[7] + " pounds\nPay rate: " + inputs[8] + " cents\nSocial security number: " + inputs[9]);

                            inputs[11] = " ";

                            while(YesOrNoChecker(inputs[11]) == 3){
                                //Verify
                                System.out.println("Is this information correct? \nY/N");
                                inputs[11] = keyboard.nextLine();

                            }
                            if(YesOrNoChecker(inputs[11]) == 1){
                                astroInfo.CreateAstronaut(inputs, encryptor, -1);
                            }
                        }

                        inputs[11] = " ";

                        System.out.print("Do you want to make another astronaut?\nYes or No\n");
                        inputs[10] = keyboard.nextLine();
                
                }
            }else{

                System.out.print("Do you want to remove an Astronaut?\nY/N\n");
                inputs[10] = keyboard.nextLine();

                if(YesOrNoChecker(inputs[10]) == 1 ){
                    while(Double.valueOf(inputs[10]) < encryptor.HowManyItemsInFile(FileSelect.astronaut)){
                        ViewData(FileSelect.astronaut);
                        System.out.print("Enter the number of the astronaut you want to delete\n");
                        inputs[10] = keyboard.nextLine();
                    }

                }else{
                    System.out.println("Are you done editing astronauts?\n");
                    inputs[15] = keyboard.nextLine();
                }

            }
        }

    }

    public void ViewData(FileSelect selected){

        String[] inputs = new String[11];

        if(selected == FileSelect.astronaut){

            int astronautCount = encryptor.HowManyItemsInFile(FileSelect.astronaut) / 10;
            
            for(int i = 0; i < astronautCount; i++){
                for(int j = 0; j < 10; j++){
                    try {
                        inputs[j] = encryptor.getUnencrypted(FileSelect.astronaut, j);
                    } catch (IOException InvalidFilePath) {
                    }
                }
                System.out.println("\nAstronaut #" + i +"'s' info- \nName: " + inputs[0] + "\nEmail: " + inputs[1] + "\nAddress: " + inputs[2] +
                            "\nPhone number: " + inputs[3] + "\nDate of birth: " + inputs[4] + "\nNext of kin: " + inputs[5] + 
                            "\nRank: " + inputs[6] + "\nWeight " + inputs[7] + "\nPay rate: " + inputs[8] + "\nSocial security number: " + inputs[9]);
            }

        }

        int rocketCount = encryptor.HowManyItemsInFile(FileSelect.astronaut) / 3;
    }

    /**
     * UNFINISHED
     */
    public void NewRocket(){

        while(YesOrNoChecker(inputs[10]) != 2){
            System.out.println("What is the astronauts name?");
            inputs[0] = keyboard.nextLine();
            keyboard.nextLine();
            System.out.println("What is the astronauts Email?");
            inputs[1] = keyboard.nextLine();
            System.out.println("What is the astronauts address?");
            inputs[2] = keyboard.nextLine();
            System.out.println("What is the astronauts phone number?");
            inputs[3] = keyboard.nextLine();
            System.out.println("What is the astronauts date of birth?");
            inputs[4] = keyboard.nextLine();
            System.out.println("What is the astronauts next of kin?");
            inputs[5] = keyboard.nextLine();
            System.out.println("What is the astronauts rank?");
            inputs[6] = keyboard.nextLine();
            System.out.println("What does the astronaut weigh?");
            inputs[7] = Double.toString(keyboard.nextDouble());
            System.out.println("What is the astronauts pay?");
            inputs[8] = Double.toString(keyboard.nextDouble());
            System.out.println("What is the astronauts social security number?");
            inputs[9] = keyboard.nextLine();
    
                while(YesOrNoChecker(inputs[10]) != 1){
                //Display input information for verification.
                System.out.println("The astronauts info: \nName: " + inputs[0] + "\nEmail: " + inputs[1] + "\nAddress: " + inputs[2] +
                    "\nPhone number: " + inputs[3] + "\nDate of birth: " + inputs[4] + "\nNext of kin: " + inputs[5] + 
                    "\nRank: " + inputs[6] + "\nWeight " + inputs[7] + "\nPay rate: " + inputs[8] + "\nSocial security number: " + inputs[9]);
    
                    while(YesOrNoChecker(inputs[11]) == 3){
                        //Verify
                        System.out.println("Is this information correct? \nY/N");
                        inputs[11] = keyboard.nextLine();
    
                    }
                    if(YesOrNoChecker(inputs[11]) == 1){
                        astroInfo.CreateAstronaut(inputs, encryptor, -1);
                    }
                }
                System.out.print("Do you want to make another astronaut?\nYes or No\n");
                inputs[10] = keyboard.nextLine();
            
            }
    }

    /**
     * Use to check if the user is the ADMIN
     * @return true if they are the admin. False if they are not the admin.
     */
    public boolean IsAdmin(){
        inputs[0] = "-1";
        
        do{
            System.out.print("Are you the admin?\nEnter Yes/No\n");
            inputs[0] = keyboard.nextLine();
            
        }while(YesOrNoChecker(inputs[0]) == 3);

        if(YesOrNoChecker(inputs[0]) == 1){

            System.out.print("Enter the admin password.\n");
            inputs[1] = keyboard.nextLine();

            try {
                if(TheseStringsAreEqual(inputs[1], encryptor.getUnencrypted(FileSelect.password, 0))){ 
                    return true;
                }else{
                    System.out.print("Sorry, that doesn't match. Either your code or password was wrong. Just in case you bumped yes here as an employee, we must ask...\n");
                }
            } catch (IOException AdminPasswordDoesNotExist) {
                //END CODE. THIS MEANS SOMEONE IS MESSING WITH FILES DURING OPERATION.
                AdminPasswordDoesNotExist.printStackTrace();
            }

        }
        return false;
    }

    /**
     * First asks if the user is an employee. Then, has the user type in their password and sees if that password is correct.
     * @return true if they are an employee. FALSE if not
     */
    public boolean EmployeeLogin() {
        do{
            System.out.print("Are you an employee?\nEnter Yes/No\n");
            inputs[0] = keyboard.nextLine();
            
        }while(YesOrNoChecker(inputs[0]) == 3);

        if(YesOrNoChecker(inputs[0]) == 1){

            System.out.print("Enter your password\n");
            inputs[1] = keyboard.nextLine();

            int passwordAmount = 0;

            //Find out how many passwords there are by reading passwords until there is no password found.
            passwordAmount = encryptor.HowManyItemsInFile(FileSelect.astronaut);

            try{

            for(int i = 0; i < passwordAmount + 1; i++){
                inputs[2] = encryptor.getUnencrypted(FileSelect.password, i);
                if(TheseStringsAreEqual(inputs[1], inputs[2])){
                    return true;
                }
            }

            System.out.print("Invalid login code.");
            return false;
            
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        
        System.out.print("\nThen get off.");
        return false;
    }

    /**
     * Use this to check an input for Y or N in the first position (0)
     * @param input the string you want to test.
     * @return 1: is Y  2: is N  3: is something else
     */
    public int YesOrNoChecker(String input){
        if(input.length() < 1){
            return 3;
        }
        if(Character.toUpperCase(input.charAt(0)) == 'Y')
        return 1;
        else if(Character.toUpperCase(input.charAt(0)) == 'N')
        return 2;
        else
        return 3;
    }

    /**
     * Use to compare the length and characters of two strings.
     * @param input1 One of the strings to be compared
     * @param input2 The other string to be compared
     * @return true if they pass both tests. false if they are not the same.
     */
    public boolean TheseStringsAreEqual(String input1, String input2){
        if(input1.length() != input2.length()){
            return false;
        }
        for(int i = 0; i < input1.length(); i++){
            if(input1.charAt(i) != input2.charAt(i))
            return false;
        }
        return true;
    }
}
