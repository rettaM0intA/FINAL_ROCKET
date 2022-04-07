public class Main {
    
    public static void main(String[] args) {

        
        String astroFileName = "AtronautInformation.dat";
        String rocketFileName = "RocketInformation.dat";
        String passwordsFileName = "Passwords.dat";

        try {
            // Encryptor encryptor = new Encryptor(astroFileName, rocketFileName, passwordsFileName);
        } catch (Exception FileAlreadyExistsException) {
            System.out.print(FileAlreadyExistsException.getMessage());
        }

        PasswordCreator passwordCreator = new PasswordCreator();

    }

}
