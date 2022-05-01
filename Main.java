import java.io.IOException;
import java.util.Scanner;

import Enums.FileSelect;

public class Main {
    
    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);
        String[] inputs = new String[10]; //Use slot 0 by default.
        int seed = 0;

        boolean active = true;
        UserInput ui = new UserInput();

        while(active){
            //Call manual here

            ui.EnterSeed();

        }

    }

    private void testing(){
        
        String astroFileName = "AtronautInformation.dat";
        String rocketFileName = "RocketInformation.dat";
        String passwordsFileName = "Passwords.dat";

        Scanner keyboard = new Scanner(System.in);

        String userInput = " ";

        Encryptor encryptor = new Encryptor();

        try {
            encryptor = new Encryptor(astroFileName, rocketFileName, passwordsFileName, 846293528);
        } catch (Exception FileAlreadyExistsException) {
            System.out.print(FileAlreadyExistsException.getMessage());
        }

        PasswordCreator passwordCreator = new PasswordCreator();

        passwordCreator.CreateAdminPassword(encryptor);


        System.out.print(passwordCreator.adminPassword + "\n" + encryptor.getCurrentSeed() + "\n");

        while(Character.toUpperCase(userInput.charAt(0)) != 'Y' && Character.toUpperCase(userInput.charAt(0)) != 'N'){
            System.out.print("do you want to make another User?\nY/N\n");
            userInput = keyboard.nextLine();
        }

        if(Character.toUpperCase(userInput.charAt(0)) == 'Y'){
            System.out.print("Please enter name of the new user\n");
            userInput = keyboard.nextLine();

            try {
                encryptor.toEncrypt(userInput, FileSelect.astronaught, 0);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.print("\nINVALID Position\n");
            }
        }

        userInput = " ";

        while(Character.toUpperCase(userInput.charAt(0)) != 'Y' && Character.toUpperCase(userInput.charAt(0)) != 'N'){
            System.out.print("do you want to make another User?\nY/N\n");
            userInput = keyboard.nextLine();
        }
        
        if(Character.toUpperCase(userInput.charAt(0)) == 'Y'){
            System.out.print("Please enter name of the new user\n");
            userInput = keyboard.nextLine();

            try {
                encryptor.toEncrypt(userInput, FileSelect.astronaught, 1);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.print("\nINVALID Position\n");
            }
        }

        try {
            System.out.print(encryptor.getUnencrypted(FileSelect.password, 0));
            System.out.print("\n"+encryptor.getUnencrypted(FileSelect.astronaught, 0));
            System.out.print("\n"+encryptor.getUnencrypted(FileSelect.astronaught, 1));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
