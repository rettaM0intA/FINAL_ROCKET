import java.io.IOException;

import Enums.FileSelect;

public class Main {
    
    public static void main(String[] args) {

        
        String astroFileName = "AtronautInformation.dat";
        String rocketFileName = "RocketInformation.dat";
        String passwordsFileName = "Passwords.dat";

        Encryptor encryptor = new Encryptor();

        try {
            encryptor = new Encryptor(astroFileName, rocketFileName, passwordsFileName, 846293528);
        } catch (Exception FileAlreadyExistsException) {
            System.out.print(FileAlreadyExistsException.getMessage());
        }

        PasswordCreator passwordCreator = new PasswordCreator();

        passwordCreator.CreateAdminPassword(encryptor);


        System.out.print(passwordCreator.adminPassword + "\n" + encryptor.getCurrentSeed() + "\n");

        try {
            System.out.print(encryptor.getUnencrypted(FileSelect.password, 0));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
