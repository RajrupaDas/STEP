import java.util.Scanner;

class One {
    public static int findLength(String text) {
        int count = 0;
        try {
            while (true) {
                text.charAt(count++);
            }
        } catch (StringIndexOutOfBoundsException e) {
            return count - 1;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a string:");
        String text = scanner.next();

        int customLength = findLength(text);
        int builtInLength = text.length();

        System.out.println("Custom method length: " + customLength);
        System.out.println("Built-in method length: " + builtInLength);
    }
}

