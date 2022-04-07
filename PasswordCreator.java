import java.util.Random;

// import Enums.FileSelect;

public class PasswordCreator {

    String adminPassword = "";
    Random random = new Random();
    
    public PasswordCreator(){
           
    }

    /*
    public boolean CreateAdminPassword(Encryptor encryptor){

        String password = encryptor.getUnencrypted(FileSelect.password, 0);

        if(password.charAt(0) == 'E' && password.charAt(1) == 'R' && password.charAt(2) == 'R' && password.charAt(3) == 'O' && password.charAt(4) == 'R'){
            GenerateAdminPassword();
            encryptor.toEncrypt(adminPassword, FileSelect.password);
        }

        return false;
    }
    */

    private void GenerateAdminPassword(){
        int length = random.nextInt(70) + 19;
        adminPassword = "";

        for(int i = 0; i < length; i++){
            adminPassword += (char) (random.nextInt(26) + 'a');
        }

    }

}
