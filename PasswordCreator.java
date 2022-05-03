import java.io.IOException;
import java.util.Random;

import Enums.FileSelect;

public class PasswordCreator {
    
    String fileName = "Passwords.dat";

    String adminPassword = "";
    Random random = new Random();
    
    public PasswordCreator(){
           
    }

    public boolean SetPassword(Encryptor encryptor, String password, int number){
        try{
            encryptor.toEncrypt(password, FileSelect.password, number);
        } catch (IOException fileWriteException){
        }
        return false;
    }

    /**
     * Will create an AdminPassword if one doesn't exist.
     * @param encryptor The encryptor with the user's seed that will be used for making the password.
     * @return Will return a value based on if the password already exists. If the password exists, returns false.
     * If the password doesn't exist and is made, returns true.
     */
    public boolean CreateAdminPassword(Encryptor encryptor){

        try {
            adminPassword = encryptor.getUnencrypted(FileSelect.password, 0);
        } catch (IOException FileReadIOException) {

            GeneratePassword();
            // adminPassword += "Admin";

            try {
                encryptor.toEncrypt(adminPassword, FileSelect.password, 0);
            } catch (IOException FileWriteIOException) {
            }
            
            return true;
        }

        return false;
    }

    public void GeneratePassword(){
        int length = random.nextInt(70) + 19;
        adminPassword = "";

        for(int i = 0; i < length; i++){
            if(random.nextInt(2) == 1){
                adminPassword += (char) (random.nextInt(25) + 'a');
            }else if(random.nextInt(2) == 1){
                adminPassword += (char) (random.nextInt(25) + 'A');
            }else if(random.nextInt(2) == 1){
                adminPassword += (random.nextInt(10) + 1);
            }else if(random.nextInt(2) == 1){
                adminPassword += (char) (random.nextInt(4) + '!');
            }
        }

        System.out.print("\n password: " + adminPassword + "\n");
    }

}
