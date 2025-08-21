import java.util.Scanner;

class Six {
    public static String checkCharType(char c) {
        if (c >= 'A' && c <= 'Z') {
            c = (char) (c + 32);
        }
        if (c >= 'a' && c <= 'z') {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                return "Vowel";
            } else {
                return "Consonant";
            }
        }
        return "Not a Letter";
    }

    public static String[][] create2DCharArray(String text) {
        String[][] charAndType = new String[text.length()][2];
        for (int i = 0; i < text.length(); i++) {
            charAndType[i][0] = String.valueOf(text.charAt(i));
            charAndType[i][1] = checkCharType(text.charAt(i));
        }
        return charAndType;
    }

    public static void display2DArray(String[][] array) {
        System.out.println("Character\tType");
        System.out.println("--------------------");
        for (String[] row : array) {
            System.out.println(row[0] + "\t\t" + row[1]);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a string:");
        String text = scanner.nextLine();
        
        String[][] result = create2DCharArray(text);
        display2DArray(result);
    }
}

