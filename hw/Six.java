import java.util.Scanner;

public class Six {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Problem6 fileOrganizer = new Problem6();

        System.out.println("Enter multiple file names with extensions (comma-separated):");
        String inputFiles = scanner.nextLine();
        String[] fileNames = new String[20];
        int count = 0;
        int start = 0;
        for (int i = 0; i < inputFiles.length(); i++) {
            if (inputFiles.charAt(i) == ',') {
                fileNames[count++] = inputFiles.substring(start, i);
                start = i + 1;
            }
        }
        if (start < inputFiles.length()) {
            fileNames[count++] = inputFiles.substring(start);
        }

        String[] validFiles = new String[count];
        for (int i = 0; i < count; i++) {
            validFiles[i] = fileNames[i].trim();
        }
        
        System.out.println("\n--- File Organization Report ---");
        fileOrganizer.organizeFiles(validFiles);
    }
}

class Problem6 {
    
    private static final String[][] CATEGORIES = {
        {"Documents", ".txt", ".doc", ".docx", ".pdf"},
        {"Images", ".jpg", ".jpeg", ".png", ".gif"},
        {"Code", ".java", ".py", ".js", ".cpp"},
        {"Archives", ".zip", ".rar", ".7z"}
    };

    private String getExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot > 0 && lastDot < fileName.length() - 1) {
            return fileName.substring(lastDot);
        }
        return "---";
    }

    private String getCategory(String extension) {
        for (String[] category : CATEGORIES) {
            for (int i = 1; i < category.length; i++) {
                if (extension.equalsIgnoreCase(category[i])) {
                    return category[0];
                }
            }
        }
        return "Unknown";
    }

    private String getNewFileName(String originalName, String category) {
        StringBuilder newName = new StringBuilder();
        newName.append(category).append("_");
        
        String cleanName = originalName;
        int lastDot = originalName.lastIndexOf('.');
        if (lastDot > 0) {
            cleanName = originalName.substring(0, lastDot);
        }
        
        newName.append(cleanName.toLowerCase().replaceAll("[^a-z0-9_]", ""));
        
        String extension = getExtension(originalName);
        if (!extension.equals("---")) {
            newName.append(extension);
        }
        
        for (int i = 0; i < newName.length(); i++) {
            char c = newName.charAt(i);
            if ((c < 'a' || c > 'z') && (c < '0' || c > '9') && c != '.' && c != '_') {
                return "Invalid_Name";
            }
        }
        
        return newName.toString();
    }

    public void organizeFiles(String[] fileNames) {
        int[] categoryCounts = new int[CATEGORIES.length + 1];
        
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("%-25s | %-15s | %-25s | %-10s\n", "Original Name", "Category", "New Name", "Status");
        System.out.println("----------------------------------------------------------------------------------");

        for (String fileName : fileNames) {
            String extension = getExtension(fileName);
            String category = getCategory(extension);
            String newName = getNewFileName(fileName, category);
            
            int categoryIndex = -1;
            for (int i = 0; i < CATEGORIES.length; i++) {
                if (CATEGORIES[i][0].equals(category)) {
                    categoryIndex = i;
                    break;
                }
            }
            if (categoryIndex != -1) {
                categoryCounts[categoryIndex]++;
            } else {
                categoryCounts[CATEGORIES.length]++;
            }
            
            System.out.printf("%-25s | %-15s | %-25s | %-10s\n", fileName, category, newName, "Processed");
        }
        System.out.println("----------------------------------------------------------------------------------");
        
        System.out.println("\n--- Organization Statistics ---");
        System.out.println("Category-wise File Counts:");
        for (int i = 0; i < CATEGORIES.length; i++) {
            System.out.printf("  %-15s: %d\n", CATEGORIES[i][0], categoryCounts[i]);
        }
        System.out.printf("  %-15s: %d\n", "Unknown", categoryCounts[CATEGORIES.length]);

        System.out.println("\n--- Batch Rename Commands (Simulated) ---");
        System.out.println("For Windows: `ren \"oldname.ext\" \"newname.ext\"`");
        System.out.println("For Unix/Linux: `mv oldname.ext newname.ext`");
    }
}
