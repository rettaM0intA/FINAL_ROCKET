import java.io.IOException;

import Enums.FileSelect;

public class AstronautInfo{

    //Create variables here
    public String fileName = "AtronautInformation.dat";

    public void CreateAstronaut(String[] inputs, Encryptor encryptor, int astronautNumber){

        if(astronautNumber < 0){
            astronautNumber = encryptor.HowManyItemsInFile(FileSelect.astronaut);
        }

        for(int i = 0; i < 10; i++){
            try {
                encryptor.toEncrypt(inputs[i], FileSelect.astronaut, i + 8 * astronautNumber);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}