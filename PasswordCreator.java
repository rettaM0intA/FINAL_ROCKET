import java.io.IOException;
import java.util.Random;

import Enums.FileSelect;

public class PasswordCreator {

    String adminPassword = "";
    Random random = new Random();
    
    public PasswordCreator(){
           
    }

    // /*
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

            GenerateAdminPassword();
            try {
                encryptor.toEncrypt(adminPassword, FileSelect.password);
            } catch (IOException FileWriteIOException) {
            }
            
            return true;
        }

        return false;
    }
    // */

    public void GenerateAdminPassword(){
        int length = random.nextInt(70) + 19;
        adminPassword = "";

        for(int i = 0; i < length; i++){
            adminPassword += (char) (random.nextInt(26) + 'a');
        }

    }

}
