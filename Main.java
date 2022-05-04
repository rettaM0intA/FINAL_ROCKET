import java.io.IOException;
import java.util.Scanner;

import Enums.FileSelect;

public class Main {
    
    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);
        String[] inputs = new String[10]; //Use slot 0 by default.

        boolean active = true;
        UserInput ui = new UserInput();

        while(active){
            //Call manual here


            //Has the user enter the seed. Does NOT check if seed is wrong.
            ui.EnterSeed();

            if(ui.IsAdmin()){
                
            }else if(ui.EmployeeLogin()){

            }else{
                return;
            }

            //Calls the main menu.
            inputs[0] = String.valueOf(ui.MainMenu());

            if(Integer.parseInt(inputs[0]) == 0){
                //Call user editing method that asks for ADMIN password

            }else if(Integer.parseInt(inputs[0]) == 1){
                //Call Astronaut management menu

            }else if(Integer.parseInt(inputs[0]) == 2){
                //Call Rocket management menu

            }else if(Integer.parseInt(inputs[0]) == 3){
                //Call Configure Launch menu

            }else if(Integer.parseInt(inputs[0]) == 4){
                //Call Simulator

            }else{
                active = false;
            }

        }

        return;

    }

    private static void testing(){

        UserInput ui = new UserInput();
        
        String astroFileName = "AtronautInformation.dat";
        String rocketFileName = "RocketInformation.dat";
        String passwordsFileName = "Passwords.dat";

        Scanner keyboard = new Scanner(System.in);

        String userInput = " ";

        Encryptor encryptor = new Encryptor();

        try {
            encryptor = new Encryptor(astroFileName, rocketFileName, passwordsFileName, 12763827);
        } catch (Exception FileAlreadyExistsException) {
            System.out.print(FileAlreadyExistsException.getMessage());
        }

        PasswordCreator passwordCreator = new PasswordCreator();

        passwordCreator.CreateAdminPassword(encryptor);


        System.out.print(passwordCreator.adminPassword + "\n" + encryptor.getCurrentSeed() + "\n");


        for(int i = 0; i < 4; i++){
        //astro testing
        userInput = " ";

        while(ui.YesOrNoChecker(userInput) == 3){
            System.out.print("do you want to make another User?\nY/N\n");
            userInput = keyboard.nextLine();
        }
        
        if(ui.YesOrNoChecker(userInput) == 1){
            System.out.print("Please enter name of the new user\n");
            userInput = keyboard.nextLine();

            try {
                encryptor.toEncrypt(userInput, FileSelect.astronaught, i);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.print("\nINVALID Position\n");
            }
        }

        }   

        try {
            System.out.print(encryptor.getUnencrypted(FileSelect.password, 0) + "\n");
            System.out.print(encryptor.getUnencrypted(FileSelect.astronaught, 0) + "\n");
            System.out.print(encryptor.getUnencrypted(FileSelect.astronaught, 1) + "\n");
            System.out.print(encryptor.getUnencrypted(FileSelect.astronaught, 2) + "\n");
            System.out.print(encryptor.getUnencrypted(FileSelect.astronaught, 3) + "\n");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
