import java.io.IOException;
import java.util.Scanner;

import Enums.FileSelect;

public class Main {
    
    public static void main(String[] args) {

        
        String astroFileName = "AtronautInformation.dat";
        String rocketFileName = "RocketInformation.dat";
        String passwordsFileName = "Passwords.dat";

        Scanner keyboard = new Scanner(System.in);

        String userInput = "";

        Encryptor encryptor = new Encryptor();

        try {
            encryptor = new Encryptor(astroFileName, rocketFileName, passwordsFileName, 846293528);
        } catch (Exception FileAlreadyExistsException) {
            System.out.print(FileAlreadyExistsException.getMessage());
        }

        PasswordCreator passwordCreator = new PasswordCreator();

        passwordCreator.CreateAdminPassword(encryptor);


        System.out.print(passwordCreator.adminPassword + "\n" + encryptor.getCurrentSeed() + "\n");

        while(userInput.length() < 1 && Character.toUpperCase(userInput.charAt(0)) != 'Y' && Character.toUpperCase(userInput.charAt(0)) != 'N'){
            System.out.print("do you want to make another User?\nY/N");
            userInput = keyboard.nextLine();
        }

        if(Character.toUpperCase(userInput.charAt(0)) != 'Y'){
            System.out.print("Please enter name of the new user");
            userInput = keyboard.nextLine();
        }

        try {
            System.out.print(encryptor.getUnencrypted(FileSelect.password, 0));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
