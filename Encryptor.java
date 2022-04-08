import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.swing.text.AsyncBoxView.ChildLocator;

import Enums.FileSelect;

public class Encryptor {
    
    Random random;

    File astronaughtFile;
    File rocketFile;
    File passwordsFile;

    int seed;

    /**
     * Creates an empty encryptor. Is unusable.
     */
    public Encryptor(){

    }

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

    public void toEncrypt(String input, FileSelect fileSelected) throws IOException{

        File chosenFile;
        
        random = new Random(seed);

        if(fileSelected == FileSelect.astronaught){
            chosenFile = astronaughtFile;
        }else if(fileSelected == FileSelect.password){
            chosenFile = passwordsFile;
        }else{
            chosenFile = rocketFile;
        }

        FileOutputStream fOutputStream = new FileOutputStream(chosenFile.getName());
        DataOutputStream dOutputStream = new DataOutputStream(fOutputStream);

        for(int i = 0; i < input.length(); i++){
            dOutputStream.write(input.charAt(i) + random.nextInt(26));
        }

    }

    public String getUnencrypted(FileSelect fileSelected, int passwordNumber) throws IOException{

        File chosenFile;    //The actual file that will be read from.
        Byte currentByte = 0;   //the currently read byte.
        int passwordSize;   //The size of the password that is to be read.
        String output = "";     //The string that will be returned at the end of this method if no errors occur.

        random = new Random(seed);

        if(fileSelected == FileSelect.astronaught){
            chosenFile = astronaughtFile;
        }else if(fileSelected == FileSelect.password){
            chosenFile = passwordsFile;
        }else{
            chosenFile = rocketFile;
        }

        FileInputStream fileInputStream = new FileInputStream(chosenFile.getName());
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);

        try{
        dataInputStream.readByte();
        currentByte = dataInputStream.readByte();
        }catch(EOFException EndedEarlyException){
            throw new IOException();
        }

        passwordSize = currentByte.intValue();

        for(int i = 0; i < passwordSize; i++){
            currentByte = dataInputStream.readByte();
            currentByte = (byte)((int)currentByte - random.nextInt(26));
            output += currentByte.toString();
        }
        
        return output;

    }

    public int getCurrentSeed(){
        return seed;
    }

}
