import java.io.IOException;
import java.util.Scanner;

import Enums.FileSelect;

public class Main {
    
    public static void main(String[] args) {

        // testing();

        Scanner keyboard = new Scanner(System.in);
        String[] inputs = new String[10]; //Use slot 0 by default.

        boolean active = true; //make true when running code. Should only be false during testing or if an alpha particle hits the code.
        UserInput ui = new UserInput();

        //Checks if there is an admin password. If there isn't, then it declares the user as admin and makes one.
        ui.FirstRun();

        //Has the user enter the seed. Does NOT check if seed is wrong.
        ui.EnterSeed();

        if(ui.IsAdmin()){
            ui.EditEmployees();
        }else if(ui.EmployeeLogin()){

        }else{
            return;
        }

        while(active){

            //Calls the main menu.
            inputs[0] = String.valueOf(ui.MainMenu());

            if(Integer.parseInt(inputs[0]) == 1){
                //Call Astronaut management menu
                ui.EditAstronauts();

            }else if(Integer.parseInt(inputs[0]) == 2){
                //Call Rocket management menu

            }else if(Integer.parseInt(inputs[0]) == 3){
                //Call Configure Launch menu

            }else if(Integer.parseInt(inputs[0]) == 4){
                //Call Simulator

            }else{
                active = false;
            }

        }
        return;
    }

}
