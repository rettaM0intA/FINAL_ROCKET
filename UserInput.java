import java.io.IOException;
import java.util.Scanner;

import Enums.FileSelect;

public class UserInput {

    private Encryptor encryptor;
    private AstronautInfo astroInfo;
    private RocketInfo rocketInfo;
    private PasswordCreator passwordCreator;

    private Scanner keyboard = new Scanner(System.in);

    private String[] inputs = new String[11];
    
    private static String name = "";
    private static String address = "";
    private static String email = "";
    private static String phone = ""; 
    private static String birth = "";
    private static String snumber = ""; //Serial Number
    private static String kin = ""; //Next of kin
    private static String rank = "";
    private static Double weight;
    private static Double pay;
    private static Double astronauts; //How many astronauts are going to be on the ship
    private static String confirmation = "";

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
     * Call to use the main menu.
     * @return a number 0-4 which means the chosen option.
     * 0: wants to edit users
     * 1: wants to edit astronauts
     * 2: wants to edit rockets
     * 3: wants to configure launch settings
     * 4: wants to test a launch
     * 5: wants to open User manual
     * 6: wants to quit the program
     * May add more later.
     */
    // TODO make is so when 5 is entered the program opens the User Manual

    
    public int MainMenu(){
        inputs[0] = "-1";
        do {
            System.out.print("\nMain menu. Please enter one of the following options.\n" + 
            "0: Edit Users\n" + 
            "1: Edit Astronauts\n" + 
            "2: Edit Rockets\n" +
            "3: Configure Simulation\n" + 
            "4: Initiate Simulation (use 3 first)\n" + 
            "5: Open User Manual\n" +
            "6: Quit Program\n");
            inputs[0] = keyboard.nextLine();
            try{
                int test = Integer.parseInt(inputs[0]);
                if(test < 0 || test > 6)
                throw new NumberFormatException();
            } catch(NumberFormatException invalidInput){
                System.out.print("\nInvalid input.\nMake sure you enter only the number you want to select");
                inputs[0] = "-1";
            }
        } while (Integer.parseInt(inputs[0]) < 0 || Integer.parseInt(inputs[0]) > 6);

        return Integer.parseInt(inputs[0]);
    }

    /**
     * Use to request the seed from the user.
     */
    public void EnterSeed(){
        
        int seed = 0;

        do {
                
            System.out.print("\nEnter the code given by the Admin.\n");
            inputs[0] = keyboard.nextLine();

            try{
            seed = Integer.parseInt(inputs[0]);
            } catch(NumberFormatException invalidInput){
                System.out.print("\nInvalid input.");
            }

            if(seed < 0){
                System.out.print("\nThe code must be a positive number.");
            }

        } while (seed <= 0);
    }
    
    public void NewAstronaut() {
       System.out.println("How many astronauts will be on this mission?");
       astronauts = keyboard.nextDouble();

       boolean no = true;

       while(no){
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

        boolean tryData = true;

        while(tryData){
       //Display input information for verification.
       System.out.println("The astronauts info: \nName: " + inputs[0] + "\nEmail: " + inputs[1] + "\nAddress: " + inputs[2] +
        "\nPhone number: " + inputs[3] + "\nDate of birth: " + inputs[4] + "\nNext of kin: " + inputs[5] + 
        "\nRank: " + inputs[6] + "\nWeight " + inputs[7] + "\nPay rate: " + inputs[8] + "\nSocial security number: " + inputs[9]);

        System.out.println("Is this information correct? \nY/N");
        confirmation = keyboard.nextLine();

            if(confirmation.length() == 1 && (Character.toLowerCase(confirmation.charAt(0)) == 'y' || Character.toLowerCase(confirmation.charAt(0)) == 'n')){
                tryData = false;
            }
        }
        
        if(Character.toLowerCase(confirmation.charAt(0)) == 'y'){
            no = false;
        }
        
       }

       
       keyboard.close();
    }

    public void NewRocket(){

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
            
        }while(YesOrNoChecker(inputs[0]) != 3);

        if(YesOrNoChecker(inputs[0]) == 1){

            //add stuff for checking if the admin has the right info.
            // if(){ 
                return true;
            // }

        }
        return false;
    }

    
    public boolean EmployeeLogin() {
        do{
            System.out.print("Are you an employee?\nEnter Yes/No\n");
            inputs[0] = keyboard.nextLine();
            
        }while(YesOrNoChecker(inputs[0]) != 3);

        if(YesOrNoChecker(inputs[0]) == 1){

            //add stuff for checking if the employee has the right info.
            // if(){
            //     return true;
            // }
        }

        return false;
    }

    /**
     * Use this to check an input for Y or N in the first position (0)
     * @param input the string you want to test.
     * @return 1: is Y  2: is N  3: is something else
     */
    public int YesOrNoChecker(String input){
        if(Character.toUpperCase(input.charAt(0)) == 'Y')
        return 1;
        else if(Character.toUpperCase(input.charAt(0)) == 'N')
        return 2;
        else
        return 3;
    }
}
