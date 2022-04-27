import java.util.Scanner;

public class UserInput {

    private Encryptor encryptor;

    private String[] inputs = new String[11];
    
    private static String name = "";
    private static String address = "";
    private static String email = "";
    private static String phone = ""; 
    private static String birth = "";
    private static String snumber = ""; //Serial Number
    private static String kin = ""; //Next of kin
    private static String rank = "";
    private static Double weight;
    private static Double pay;
    private static Double astronauts; //How many astronauts are going to be on the ship
    private static String confirmation = "";

    public UserInput(Encryptor encryptor){
        this.encryptor = encryptor;
    }
    
    public void newAstronaut() {
       Scanner keyboard = new Scanner(System.in);
       System.out.println("How many astronauts will be on this mission?");
       astronauts = keyboard.nextDouble();

       boolean no = true;

       while(no){
       System.out.println("What is the astronauts name?");
       inputs[0] = keyboard.nextLine();
       keyboard.nextLine();
       System.out.println("What is the astronauts Email?");
       inputs[1] = keyboard.nextLine();
       System.out.println("What is the astronauts address?");
       inputs[2] = keyboard.nextLine();
       System.out.println("What is the astronauts phone number?");
       inputs[3] = keyboard.nextLine();
       System.out.println("What is the astronauts date of birth?");
       inputs[4] = keyboard.nextLine();
       System.out.println("What is the astronauts next of kin?");
       inputs[5] = keyboard.nextLine();
       System.out.println("What is the astronauts rank?");
       inputs[6] = keyboard.nextLine();
       System.out.println("What does the astronaut weigh?");
       inputs[7] = Double.toString(keyboard.nextDouble());
       System.out.println("What is the astronauts pay?");
       inputs[8] = Double.toString(keyboard.nextDouble());
       System.out.println("What is the astronauts social security number?");
       inputs[9] = keyboard.nextLine();

        boolean tryData = true;

        while(tryData){
       //Display input information for verification.
       System.out.println("The astronauts info: \nName: " + inputs[0] + "\nEmail: " + inputs[1] + "\nAddress: " + inputs[2] +
        "\nPhone number: " + inputs[3] + "\nDate of birth: " + inputs[4] + "\nNext of kin: " + inputs[5] + 
        "\nRank: " + inputs[6] + "\nWeight " + inputs[7] + "\nPay rate: " + inputs[8] + "\nSocial security number: " + inputs[9]);

        System.out.println("Is this information correct? \nY/N");
        confirmation = keyboard.nextLine();

            if(confirmation.length() == 1 && (Character.toLowerCase(confirmation.charAt(0)) == 'y' || Character.toLowerCase(confirmation.charAt(0)) == 'n')){
                tryData = false;
            }
        }
        
        if(Character.toLowerCase(confirmation.charAt(0)) == 'y'){
            no = false;
        }
        
       }

       
       keyboard.close();
    }

}
