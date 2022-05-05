import java.io.IOException;

import Enums.FileSelect;

public class AstronautInfo{

    //Create variables here
    public String fileName = "AtronautInformation.dat";

    public void CreateAstronaut(String[] inputs, Encryptor encryptor, int astronautNumber){

        if(astronautNumber < 0){
            for(int i = 0; i < 10; i++){
                try {
                    encryptor.toEncrypt(inputs[i], FileSelect.astronaut, i + encryptor.HowManyItemsInFile(FileSelect.astronaut) - 1);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }else{

            for(int i = 0; i < 10; i++){
                try {
                    encryptor.toEncrypt(inputs[i], FileSelect.astronaut, i + 8 * astronautNumber);
                } catch (IOException InvalidFilePath) {
                    //Someone messed with save files manually or the code.
                    System.out.print("Data corruption error. Reset program. If error persists, contact creator.");
                }
            }
        }
    }

    public void RemoveAstronaut(int astronautNumber, Encryptor encryptor){

        for(int i = 0; i < 10; i++){
            try {
                encryptor.toEncrypt("", FileSelect.astronaut, i + 8 * astronautNumber);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
}