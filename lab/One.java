import java.util.Scanner;

public class One {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Problem1 replacer = new Problem1();

        System.out.println("Enter the main text:");
        String mainText = scanner.nextLine();
        
        System.out.println("Enter the substring to find:");
        String findStr = scanner.nextLine();
        
        System.out.println("Enter the replacement string:");
        String replaceStr = scanner.nextLine();
        
        System.out.println("\n--- Substring Replacement Report ---");
        replacer.processReplacement(mainText, findStr, replaceStr);
    }
}

class Problem1 {
    private int[] findAllOccurrences(String mainText, String findStr) {
        int[] indices = new int[mainText.length()];
        int count = 0;
        int fromIndex = 0;
        
        while (fromIndex < mainText.length()) {
            int foundIndex = mainText.indexOf(findStr, fromIndex);
            if (foundIndex == -1) {
                break;
            }
            indices[count++] = foundIndex;
            fromIndex = foundIndex + findStr.length();
        }
        
        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = indices[i];
        }
        return result;
    }

    private String replaceManually(String mainText, String findStr, String replaceStr, int[] indices) {
        if (indices.length == 0) {
            return mainText;
        }

        StringBuilder newText = new StringBuilder();
        int lastIndex = 0;

        for (int index : indices) {
            newText.append(mainText.substring(lastIndex, index));
            newText.append(replaceStr);
            lastIndex = index + findStr.length();
        }
        newText.append(mainText.substring(lastIndex));
        
        return newText.toString();
    }
    
    private boolean compareResults(String manualResult, String builtInResult) {
        return manualResult.equals(builtInResult);
    }

    public void processReplacement(String mainText, String findStr, String replaceStr) {
        int[] occurrences = findAllOccurrences(mainText, findStr);
        String manualResult = replaceManually(mainText, findStr, replaceStr, occurrences);
        String builtInResult = mainText.replace(findStr, replaceStr);
        boolean comparison = compareResults(manualResult, builtInResult);

        System.out.println("Original text: \"" + mainText + "\"");
        System.out.println("Substring found: " + findStr);
        System.out.println("Replacement string: " + replaceStr);
        
        System.out.println("\nNumber of occurrences found: " + occurrences.length);
        System.out.print("Starting positions: ");
        for (int i = 0; i < occurrences.length; i++) {
            System.out.print(occurrences[i] + (i == occurrences.length - 1 ? "" : ", "));
        }
        System.out.println();
        
        System.out.println("\nResult using manual method: \"" + manualResult + "\"");
        System.out.println("Result using built-in method: \"" + builtInResult + "\"");
        System.out.println("Comparison result: " + (comparison ? "Match" : "No Match") + ".");
    }
}
