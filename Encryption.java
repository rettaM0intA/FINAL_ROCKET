import java.io.File;
import java.io.IOException;

public class Encryption {
    
    double seed;
    File astronaughtFile;
    File rocketFile;
    File passwordsFile;

    public Encryption(File astroFileName, String rocketFileName){

        seed = 0;

    }

    public Encryption(double seed){

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
