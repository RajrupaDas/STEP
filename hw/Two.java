import java.util.Scanner;

public class Two {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Problem2 passwordAnalyzer = new Problem2();
        
        System.out.println("Enter multiple passwords to analyze (comma-separated):");
        String inputPasswords = scanner.nextLine();
        String[] passwords = new String[20];
        int count = 0;
        int start = 0;
        for (int i = 0; i < inputPasswords.length(); i++) {
            if (inputPasswords.charAt(i) == ',') {
                passwords[count++] = inputPasswords.substring(start, i);
                start = i + 1;
            }
        }
        if (start < inputPasswords.length()) {
            passwords[count++] = inputPasswords.substring(start);
        }

        String[] validPasswords = new String[count];
        for (int i = 0; i < count; i++) {
            validPasswords[i] = passwords[i].trim();
        }

        System.out.println("\n--- Password Analysis Report ---");
        passwordAnalyzer.analyzePasswords(validPasswords);

        System.out.println("\n--- Password Generator ---");
        System.out.println("Enter desired password length:");
        int length = scanner.nextInt();
        String generatedPassword = passwordAnalyzer.generateStrongPassword(length);
        System.out.println("Generated strong password: " + generatedPassword);
        System.out.println("Analysis of generated password:");
        passwordAnalyzer.analyzePasswords(new String[]{generatedPassword});
    }
}

class Problem2 {
    private String getStrengthLevel(int score) {
        if (score > 50) return "Strong";
        if (score > 20) return "Medium";
        return "Weak";
    }

    private int calculateScore(String password) {
        int length = password.length();
        int score = 0;
        int uppercase = 0;
        int lowercase = 0;
        int digits = 0;
        int specials = 0;

        for (int i = 0; i < length; i++) {
            char c = password.charAt(i);
            if (c >= 65 && c <= 90) uppercase++;
            else if (c >= 97 && c <= 122) lowercase++;
            else if (c >= 48 && c <= 57) digits++;
            else specials++;
        }

        if (length > 8) score += (length - 8) * 2;
        if (uppercase > 0) score += 10;
        if (lowercase > 0) score += 10;
        if (digits > 0) score += 10;
        if (specials > 0) score += 10;
        
        if (password.contains("123") || password.contains("abc") || password.contains("qwerty")) {
            score -= 10;
        }

        return score;
    }

    public String generateStrongPassword(int length) {
        StringBuilder password = new StringBuilder();
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specials = "!@#$%^&*()_+-=[]{}|;':\",.<>/?`~";

        password.append(uppercase.charAt((int) (Math.random() * uppercase.length())));
        password.append(lowercase.charAt((int) (Math.random() * lowercase.length())));
        password.append(digits.charAt((int) (Math.random() * digits.length())));
        password.append(specials.charAt((int) (Math.random() * specials.length())));

        String allChars = uppercase + lowercase + digits + specials;
        for (int i = 4; i < length; i++) {
            password.append(allChars.charAt((int) (Math.random() * allChars.length())));
        }

        for (int i = 0; i < length; i++) {
            int randomPos = (int) (Math.random() * length);
            char temp = password.charAt(i);
            password.setCharAt(i, password.charAt(randomPos));
            password.setCharAt(randomPos, temp);
        }

        return password.toString();
    }

    private void displayAnalysis(String password) {
        int length = password.length();
        int uppercase = 0;
        int lowercase = 0;
        int digits = 0;
        int specials = 0;

        for (int i = 0; i < length; i++) {
            char c = password.charAt(i);
            if (c >= 65 && c <= 90) uppercase++;
            else if (c >= 97 && c <= 122) lowercase++;
            else if (c >= 48 && c <= 57) digits++;
            else specials++;
        }
        
        int score = calculateScore(password);
        String strength = getStrengthLevel(score);

        System.out.printf("%-20s | %-8d | %-12d | %-12d | %-8d | %-12d | %-8d | %-10s\n",
            password, length, uppercase, lowercase, digits, specials, score, strength);
    }

    public void analyzePasswords(String[] passwords) {
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s | %-8s | %-12s | %-12s | %-8s | %-12s | %-8s | %-10s\n",
            "Password", "Length", "Uppercase", "Lowercase", "Digits", "Special", "Score", "Strength");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        for (String password : passwords) {
            displayAnalysis(password);
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
    }
}
