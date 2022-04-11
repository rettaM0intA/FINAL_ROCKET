import java.util.Scanner;

public class Main {
    
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
    
    public static void main(String[] args) {
       Scanner keyboard = new Scanner(System.in);
       System.out.println("How many astronauts will be on this mission?");
       astronauts = keyboard.nextDouble();

       boolean no = true;

       while(no){
       System.out.println("What is the astronauts name?");
       name = keyboard.nextLine();
       name = keyboard.nextLine();
       System.out.println("What is the astronauts Email?");
       email = keyboard.nextLine();
       System.out.println("What is the astronauts address?");
       address = keyboard.nextLine();
       System.out.println("What is the astronauts phone number?");
       phone = keyboard.nextLine();
       System.out.println("What is the astronauts date of birth?");
       birth = keyboard.nextLine();
       System.out.println("What is the astronauts next of kin?");
       kin = keyboard.nextLine();
       System.out.println("What is the astronauts rank?");
       rank = keyboard.nextLine();
       System.out.println("What does the astronaut weigh?");
       weight = keyboard.nextDouble();
       System.out.println("What is the astronauts pay?");
       pay = keyboard.nextDouble();
       System.out.println("What is the astronauts social security number?");
       snumber = keyboard.nextLine();
       snumber = keyboard.nextLine();



       //Display input information for verification.
       System.out.println("The astronauts info: \nName: " + name + "\nEmail: " + email + "\nAddress: " + address +
        "\nPhone number: " + phone + "\nDate of birth: " + birth + "\nNext of kin: " + kin + 
        "\nRank: " + rank + "\nWeight " + weight + "\nPay rate: " + pay + "\nSocial security number: " + snumber);

        System.out.println("Is this information correct? \nY/N");
        confirmation = keyboard.nextLine();
        if (Character.toLowerCase(confirmation.charAt(0)) == 'n'){
            
        }
        
       }

       
       keyboard.close();
    }

}
