import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import Enums.FileSelect;

public class Encryptor {
    
    Random random;

    int randomChange = 1;

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

        Random randoma = new Random(seed);

        // randomChange = randoma.nextInt(4);
        
        if(errorCodes.length() > 0){
            throw new Exception(errorCodes);
        }

    }

    /**
     * Send information that you want encrypted to this
     * @param input The String of information that will be encrypted and stored
     * @param fileSelected the File you want. FileSelect.File  Change File to the one you want.
     * @param itemNumber The number of the item you want encrytped. If you put a number that is larger than 1 more than the current amount, it won't work.
     * @throws IOException Invalid File path. Will happen if the paths inserted when constructing the 
     * Encryptor are invalid.
     */
    public void toEncrypt(String input, FileSelect fileSelected, int itemNumber) throws IOException{

        File chosenFile;

        int itemSize = 0;
        byte[] writtingByte = new byte[1];
        int existingEntryAmount = 0;

        if(fileSelected == FileSelect.astronaught){
            chosenFile = astronaughtFile;
        }else if(fileSelected == FileSelect.password){
            chosenFile = passwordsFile;
        }else{
            chosenFile = rocketFile;
        }

        try{
            try{
            while(true){
                this.ReadFromFile(fileSelected, existingEntryAmount);
                existingEntryAmount += 1;
            }
            }catch(EOFException amountReached){
                // System.out.print("\n" + existingEntryAmount + "\n"); //uncomment to see how many exist when code is ran.
                throw new IOException();
                
            } 
        }catch(IOException amountReached){
            //This will occur when the end of the file is reached.
            // System.out.print("\n" + existingEntryAmount + "\n"); //uncomment to see how many exist when code is ran.
        }

        String[] output = new String[existingEntryAmount];

        if(existingEntryAmount > 0){
            for(int i = 0; i < existingEntryAmount; i++){
                output[i] = "";
                // itemSize = Integer.parseUnsignedInt(getUnencrypted(fileSelected, i+.5));
                
                output[i] += getUnencrypted(fileSelected, i) + "";
            }
        }

        FileOutputStream fOutputStream = new FileOutputStream(chosenFile);
        DataOutputStream dOutputStream = new DataOutputStream(fOutputStream);

        if(output.length > 0 && itemNumber > 0){
            for(int i = 0; i < itemNumber; i++){
                
                // output[i] = output[i].substring(4, output[i].length());

                // //write the length of the first previous entry.
                // writtingByte[0] = (byte)((int)output[i].length() + random.nextInt(randomChange));
                // dOutputStream.write(writtingByte);

                // output[0] = "4Alex";

                writtingByte[0] = (byte)(output[i].length());
                dOutputStream.write(writtingByte);

                //Write all pieces of the previous entries
                for(int j = 0; j < output[i].length(); j++){
                    writtingByte[0] = (byte)((int)output[i].charAt(j) + (randomChange));
                    // writtingByte[0] = (byte)(output[0].charAt(j));
                    dOutputStream.write(writtingByte);
                }

            }
        }

        if(input.length() > 0){
        
            //Write the length of the Input
            writtingByte[0] = (byte)(input.length());
            dOutputStream.write(writtingByte);

            //Write all pieces of the Input
            for(int i = 0; i < input.length(); i++){
                writtingByte[0] = (byte)((int)input.charAt(i) + (randomChange));
                // writtingByte[0] = (byte)(input.charAt(i));
                dOutputStream.write(writtingByte);
            }

        }

            
        if(itemNumber < existingEntryAmount-1){
            
            for(int i = itemNumber; i < existingEntryAmount; i++){
                
                // output[i] = output[i].substring(4, output[i].length());

                // //write the length of the first previous entry.
                // writtingByte[0] = (byte)((int)output[i].length() + random.nextInt(randomChange));
                // dOutputStream.write(writtingByte);

                // output[0] = "4Alex";

                writtingByte[0] = (byte)(output[i].length());
                dOutputStream.write(writtingByte);

                //Write all pieces of the previous entries
                for(int j = 0; j < output[i].length(); j++){
                    writtingByte[0] = (byte)((int)output[i].charAt(j) + (randomChange));
                    // writtingByte[0] = (byte)(output[0].charAt(j));
                    dOutputStream.write(writtingByte);
                }

            }
        }

        fOutputStream.close();
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
    public String getUnencrypted(FileSelect fileSelected, double itemNumber) throws IOException{


        String output = "";     //The string that will be returned at the end of this method if no errors occur.

        for(int i = 0; i < ReadFromFile(fileSelected, itemNumber).length; i++){

        output += Character.toString((char)(((int)ReadFromFile(fileSelected, itemNumber)[i]) - (randomChange)));

        }

        return output;

    }

    public byte[] ReadFromFile(FileSelect fileSelected, double itemNumber) throws IOException{

        File chosenFile;    //The actual file that will be read from.
        byte currentByte = 0;   //the currently read byte.
        int encryptedDataSize = 0;   //The size of the password that is to be read.

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
            encryptedDataSize = ((int)dataInputStream.readByte());// - (randomChange));
        }catch(EOFException EndedEarlyException){
            dataInputStream.close();
            throw new IOException();
        }

        byte[] output = new byte[encryptedDataSize];     //The string that will be returned at the end of this method if no errors occur.

        if(itemNumber != 0){
            if(itemNumber % 1 == 0.5){
                if(itemNumber == 0.5){
                    output = new byte[1];
                    try{ 
                        output[0] = currentByte;
                    }catch(IllegalArgumentException DidNotReadCorrectly){
                        dataInputStream.close();
                        throw DidNotReadCorrectly;
                    }
                    dataInputStream.close();
                    return output;
                }else{
                    for(int i = 0; i < itemNumber-.5; i++){
                        for(int j = 0; j < encryptedDataSize + 1; j++){
                            currentByte = dataInputStream.readByte();
                        }
                        
                    }

                    output[0] = currentByte;

                    dataInputStream.close();
                    return output;
                }
            }else{
                
                // try{
                    for(int i = 0; i < itemNumber; i++){
                        for(int j = 0; j < encryptedDataSize + 1; j++){
                            currentByte = dataInputStream.readByte();
                        }

                        encryptedDataSize = currentByte;
                        
                    }


                // }catch(EOFException nothingInLocation){
                //     dataInputStream.close();
                //     output = new Byte[1];
                //     output[0] = 0;
                //     return output;
                // }

                // encryptedDataSize = ReadFromFile(fileSelected, itemNumber+0.5)[0];

                output = new byte[encryptedDataSize];
                
                // for(int i = 0; i < encryptedDataSize; i++){
                    // try{
                    //     currentByte = dataInputStream.readByte();
                    // }catch(EOFException endedEarly){
                    //     //return the expected sized output
                    //     dataInputStream.close();
                    //     return output;
                    // }

                    dataInputStream.read(output, 0, encryptedDataSize);
                // }
            }
        }else{
            for(int i = 0; i < encryptedDataSize; i++){
                try{
                currentByte = dataInputStream.readByte();
                }catch(Exception x){
                    dataInputStream.close();
                    throw new IOException(String.valueOf(encryptedDataSize));
                }

                currentByte = (byte)((int)currentByte);
                try{
                    output[i] = currentByte;
                }catch(IllegalArgumentException DidNotReadCorrectly){
                    dataInputStream.close();
                    throw DidNotReadCorrectly;
                }
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
