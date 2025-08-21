import java.util.Scanner;

public class Four {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Problem4 cipher = new Problem4();

        System.out.println("Enter text to encrypt:");
        String text = scanner.nextLine();
        System.out.println("Enter the shift value:");
        int shift = scanner.nextInt();
        
        System.out.println("\n--- Caesar Cipher Report ---");
        cipher.processCipher(text, shift);
    }
}

class Problem4 {

    private String encrypt(String text, int shift) {
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                ch = (char) ('a' + (ch - 'a' + shift) % 26);
            } else if (ch >= 'A' && ch <= 'Z') {
                ch = (char) ('A' + (ch - 'A' + shift) % 26);
            }
            encryptedText.append(ch);
        }
        return encryptedText.toString();
    }
    
    private String decrypt(String text, int shift) {
        return encrypt(text, 26 - (shift % 26));
    }
    
    private boolean validateDecryption(String original, String decrypted) {
        return original.equals(decrypted);
    }

    private void displayAscii(String label, String text) {
        System.out.println("\n" + label + ": " + text);
        System.out.print("ASCII Values: ");
        for (int i = 0; i < text.length(); i++) {
            System.out.print((int) text.charAt(i) + " ");
        }
        System.out.println();
    }
    
    public void processCipher(String originalText, int shift) {
        String encryptedText = encrypt(originalText, shift);
        String decryptedText = decrypt(encryptedText, shift);
        boolean validation = validateDecryption(originalText, decryptedText);

        displayAscii("Original Text", originalText);
        displayAscii("Encrypted Text", encryptedText);
        displayAscii("Decrypted Text", decryptedText);
        
        System.out.println("\nDecryption Validation: " + (validation ? "Success" : "Failure"));
    }
}
