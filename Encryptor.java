import java.io.File;
import java.io.IOException;

public class Encryptor {
    
    double seed;
    File astronaughtFile;
    File rocketFile;
    File passwordsFile;

    /**
     * Creates a new Encryptor with a seed of 0.
     * @param astroFileName The path of the astronaut file. Should be just the name of the file.
     * @param rocketFileName The path of the rocket file. Should be just the name of the file.
     * @param passwordsFileName The path of the password file. Should be just the name of the file.
     */
    public Encryptor(String astroFileName, String rocketFileName, String passwordsFileName){

        astronaughtFile = new File(astroFileName);
        rocketFile = new File(rocketFileName);
        passwordsFile = new File(passwordsFileName);

        seed = 0;

    }

    public Encryptor(String astroFileName, String rocketFileName, String passwordsFileName, double seed){
        astronaughtFile = new File(astroFileName);
        rocketFile = new File(rocketFileName);
        passwordsFile = new File(passwordsFileName);

        this.seed = seed;

    }

    public void toEncrypt(String input, FileSelect fileSelected){

        File file;

        if(fileSelected == FileSelect.astronaught){
            file = astronaughtFile;
        }else if(fileSelected == FileSelect.password){
            file = passwordsFile;
        }else if(fileSelected == FileSelect.rocket){
            file = rocketFile;
        }

        if(seed == 0){
            
        }

    }

    public String getUnencrypted(){
        return "";
    }

}
