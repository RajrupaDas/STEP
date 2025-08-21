import java.util.Scanner;

public class Four {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Problem4 calculator = new Problem4();

        System.out.println("Enter a mathematical expression (e.g., \"(10 + 5) * 2 - 3\"):");
        String expression = scanner.nextLine();
        
        System.out.println("\n--- Expression Calculator ---");
        calculator.processExpression(expression);
    }
}

class Problem4 {
    private String[] numbers = new String[20];
    private char[] operators = new char[20];
    private int numCount = 0;
    private int opCount = 0;

    private boolean validateExpression(String expr) {
        int balance = 0;
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if ((c < '0' || c > '9') && c != '+' && c != '-' && c != '*' && c != '/' && c != ' ' && c != '(' && c != ')') {
                return false;
            }
            if (c == '(') balance++;
            else if (c == ')') balance--;
            if (balance < 0) return false;
        }
        return balance == 0;
    }

    private void parseExpression(String expr) {
        numCount = 0;
        opCount = 0;
        StringBuilder currentNum = new StringBuilder();
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c >= '0' && c <= '9') {
                currentNum.append(c);
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                if (currentNum.length() > 0) {
                    numbers[numCount++] = currentNum.toString();
                    currentNum.setLength(0);
                }
                operators[opCount++] = c;
            } else if (c == ' ') {
                if (currentNum.length() > 0) {
                    numbers[numCount++] = currentNum.toString();
                    currentNum.setLength(0);
                }
            }
        }
        if (currentNum.length() > 0) {
            numbers[numCount++] = currentNum.toString();
        }
    }

    private int evaluate(String expr) {
        parseExpression(expr);
        
        for (int i = 0; i < opCount; i++) {
            if (operators[i] == '*' || operators[i] == '/') {
                int num1 = Integer.parseInt(numbers[i]);
                int num2 = Integer.parseInt(numbers[i + 1]);
                int result = (operators[i] == '*') ? num1 * num2 : num1 / num2;
                
                numbers[i] = String.valueOf(result);
                for (int j = i + 1; j < numCount - 1; j++) numbers[j] = numbers[j + 1];
                for (int j = i; j < opCount - 1; j++) operators[j] = operators[j + 1];
                numCount--;
                opCount--;
                i--;
            }
        }
        
        int result = Integer.parseInt(numbers[0]);
        for (int i = 0; i < opCount; i++) {
            int num = Integer.parseInt(numbers[i + 1]);
            if (operators[i] == '+') result += num;
            else if (operators[i] == '-') result -= num;
        }

        return result;
    }

    public void processExpression(String expression) {
        System.out.println("Original Expression: " + expression);
        if (!validateExpression(expression)) {
            System.out.println("Error: Invalid expression format.");
            return;
        }
        
        String currentExpr = expression;
        while (currentExpr.contains("(")) {
            int start = currentExpr.lastIndexOf('(');
            int end = currentExpr.indexOf(')', start);
            String subExpr = currentExpr.substring(start + 1, end);
            int result = evaluate(subExpr);
            StringBuilder newExpr = new StringBuilder();
            newExpr.append(currentExpr.substring(0, start));
            newExpr.append(result);
            newExpr.append(currentExpr.substring(end + 1));
            currentExpr = newExpr.toString();
            System.out.println("Step: " + currentExpr);
        }

        int finalResult = evaluate(currentExpr);
        System.out.println("Final Result: " + finalResult);
    }
}
