import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
     * @throws Exception Will be thrown if an invalid path is entered for any of the FileNames.
     */
    public Encryptor(String astroFilePathName, String rocketFilePathName, String passwordsFilePathName, int seed) throws Exception{
        
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

        this.seed = seed;
        
        if(errorCodes.length() > 0){
            throw new Exception(errorCodes);
        }

    }

    /**
     * Send information that you want encrypted to this
     * @param input The String of information that will be encrypted and stored
     * @param fileSelected the File you want. FileSelect.File  Change File to the one you want.
     * @param itemNumber The number of the item you want encrytped. If you put a number that is larger than 1 more than the current amount, it won't work.
     * @throws IOException 
     */
    public void toEncrypt(String input, FileSelect fileSelected, int itemNumber) throws IOException{

        File chosenFile;
        
        random = new Random(seed);

        if(fileSelected == FileSelect.astronaught){
            chosenFile = astronaughtFile;
        }else if(fileSelected == FileSelect.password){
            chosenFile = passwordsFile;
        }else{
            chosenFile = rocketFile;
        }

        FileOutputStream fOutputStream = new FileOutputStream(chosenFile);
        DataOutputStream dOutputStream = new DataOutputStream(fOutputStream);

        //Write the length of the Input
        dOutputStream.writeByte((char)input.length() + random.nextInt(26));

        //Write all pieces of the Input
        for(int i = 0; i < input.length(); i++){
            dOutputStream.write(input.charAt(i) + random.nextInt(26));
        }

        dOutputStream.close();

    }

    /**
     * Reads from the chosen file, returning the information you are looking for. 
     * DO NOT RUN IF YOU USED THE EMPTY CONSTRUCTOR.
     * @param fileSelected the File you want. FileSelect.File  Change File to the one you want.
     * @param itemNumber The number of the item you are looking for. For example, 0 would be the first rocket
     * in the rocket file, 1 would be the second, etc.
     * @return A String containing the unencrypted data.
     * @throws IOException Is thrown if the files entered when this object was created are invalid. 
     * Will break if you use empty constructor
     */
    public String getUnencrypted(FileSelect fileSelected, int itemNumber) throws IOException{

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

        FileInputStream fileInputStream = new FileInputStream(chosenFile);
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);

        try{
        // dataInputStream.readByte();
        currentByte = (byte)((int)dataInputStream.readByte() - random.nextInt(26));
        }catch(EOFException EndedEarlyException){

            dataInputStream.close();
            throw new IOException();
        }

        passwordSize = currentByte.intValue();

        for(int i = 0; i < passwordSize; i++){
            currentByte = dataInputStream.readByte();
            currentByte = (byte)((int)currentByte - random.nextInt(26));
            try{
                output += Character.toString(currentByte);
            }catch(IllegalArgumentException DidNotReadCorrectly){
                dataInputStream.close();
                return "Invalid seed";
            }
        }

        dataInputStream.close();
        
        return output;

    }

    /**
     * Allows you to get what the currently used seed is. Not always going to be the correct seed.
     * @return The seed as an int.
     */
    public int getCurrentSeed(){
        return seed;
    }

}
