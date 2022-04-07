import java.io.File;
import java.io.IOException;
import java.util.Random;

import Enums.FileSelect;

public class Encryptor {
    
    Random random;

    File astronaughtFile;
    File rocketFile;
    File passwordsFile;

    int seed;

    /**
     * Creates a new Encryptor with a seed of 0. Should only be called if the code has not been run yet.
     * @param astroFilePathName The path of the astronaut file. Should be just the name of the file.
     * @param rocketFilePathName The path of the rocket file. Should be just the name of the file.
     * @param passwordsFilePathName The path of the password file. Should be just the name of the file.
     * @throws Exception Will be thrown if an invalid path is entered for any of the FileNames.
     */
    public Encryptor(String astroFilePathName, String rocketFilePathName, String passwordsFilePathName) throws Exception{

        String errorCodes = "";

        astronaughtFile = new File("Storage/" + astroFilePathName);
        try {
            //attempts to create a new file for the first time this is run and just in case the file is deleted.
            astronaughtFile.createNewFile();
        } catch (IOException InvalidPath) {
            //Will send error message if file path is invalid
            errorCodes += "InvalidFilePath for Astronaut data storage file.\n";
        }

        rocketFile = new File("Storage/" + rocketFilePathName);
        try {
            //attempts to create a new file for the first time this is run and just in case the file is deleted.
            rocketFile.createNewFile();
        } catch (IOException InvalidPath) {
            //Will send error message if file path is invalid
            errorCodes += "InvalidFilePath for Rocket data storage file.\n";
        }

        passwordsFile = new File("Storage/" + passwordsFilePathName);
        try {
            //attempts to create a new file for the first time this is run and just in case the file is deleted.
            passwordsFile.createNewFile();
        } catch (IOException InvalidPath){
            //Will send error message if file path is invalid
            errorCodes += "InvalidFilePath for Password data storage file.\n";
        }


        
        random = new Random();
        seed = random.nextInt(999999999);

        if(errorCodes.length() > 0){
            throw new Exception(errorCodes);
        }

    }

    /**
     * 
     * @param astroFileName The path of the astronaut file. Should be just the name of the file.
     * @param rocketFileName The path of the rocket file. Should be just the name of the file.
     * @param passwordsFileName The path of the password file. Should be just the name of the file.
     * @param seed The seed the user input. Would be given to them by the admin. 
     * The seed is used for decoding encrypted information, if it is lost all information must be re-entered with a new seed.
     */
    public Encryptor(String astroFileName, String rocketFileName, String passwordsFileName, int seed){
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

    }

    public String getUnencrypted(FileSelect fileSelected, int LineNumber){
        return "ERROR";
    }

    public int getCurrentSeed(){
        return seed;
    }

}
