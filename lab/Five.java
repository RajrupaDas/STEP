import java.util.Scanner;

public class Five {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Problem5 emailAnalyzer = new Problem5();
        
        System.out.println("Enter multiple email addresses (comma-separated):");
        String inputEmails = scanner.nextLine();
        
        String[] emails = new String[50];
        int count = 0;
        int start = 0;
        for (int i = 0; i < inputEmails.length(); i++) {
            if (inputEmails.charAt(i) == ',') {
                emails[count++] = inputEmails.substring(start, i).trim();
                start = i + 1;
            }
        }
        if (start < inputEmails.length()) {
            emails[count++] = inputEmails.substring(start).trim();
        }
        
        String[] validEmails = new String[count];
        for (int i = 0; i < count; i++) {
            validEmails[i] = emails[i];
        }
        
        System.out.println("\n--- Email Analysis Report ---");
        emailAnalyzer.analyzeEmails(validEmails);
    }
}

class Problem5 {

    private boolean validateEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex <= 0 || atIndex != email.lastIndexOf('@')) {
            return false;
        }
        
        int dotIndex = email.lastIndexOf('.');
        if (dotIndex <= atIndex + 1 || dotIndex == email.length() - 1) {
            return false;
        }
        
        if (email.substring(0, atIndex).length() == 0 || email.substring(atIndex + 1).length() == 0) {
            return false;
        }
        
        return true;
    }

    private String extractUsername(String email) {
        int atIndex = email.indexOf('@');
        return email.substring(0, atIndex);
    }

    private String extractDomain(String email) {
        int atIndex = email.indexOf('@');
        return email.substring(atIndex + 1);
    }
    
    private String extractDomainName(String domain) {
        int dotIndex = domain.indexOf('.');
        return domain.substring(0, dotIndex);
    }
    
    private String extractExtension(String domain) {
        int dotIndex = domain.indexOf('.');
        return domain.substring(dotIndex + 1);
    }
    
    public void analyzeEmails(String[] emails) {
        int validCount = 0;
        int invalidCount = 0;
        long totalUsernameLength = 0;
        
        String[] commonDomains = new String[10];
        int[] domainCounts = new int[10];
        int uniqueDomains = 0;

        System.out.println("---------------------------------------------------------------------------------------------------");
        System.out.printf("%-30s | %-20s | %-15s | %-15s | %-10s | %-10s\n",
            "Email", "Username", "Domain", "Domain Name", "Extension", "Status");
        System.out.println("---------------------------------------------------------------------------------------------------");

        for (String email : emails) {
            String status = "Invalid";
            String username = "---";
            String domain = "---";
            String domainName = "---";
            String extension = "---";

            if (validateEmail(email)) {
                validCount++;
                status = "Valid";
                username = extractUsername(email);
                domain = extractDomain(email);
                domainName = extractDomainName(domain);
                extension = extractExtension(domain);
                totalUsernameLength += username.length();
                
                boolean found = false;
                for (int i = 0; i < uniqueDomains; i++) {
                    if (commonDomains[i].equals(domain)) {
                        domainCounts[i]++;
                        found = true;
                        break;
                    }
                }
                if (!found && uniqueDomains < 10) {
                    commonDomains[uniqueDomains] = domain;
                    domainCounts[uniqueDomains]++;
                    uniqueDomains++;
                }

            } else {
                invalidCount++;
            }
            
            System.out.printf("%-30s | %-20s | %-15s | %-15s | %-10s | %-10s\n",
                email, username, domain, domainName, extension, status);
        }
        
        System.out.println("---------------------------------------------------------------------------------------------------");
        
        System.out.println("\n--- Analysis Summary ---");
        System.out.println("Total emails processed: " + emails.length);
        System.out.println("Valid emails: " + validCount);
        System.out.println("Invalid emails: " + invalidCount);
        System.out.printf("Average username length: %.2f\n", (validCount > 0 ? (double) totalUsernameLength / validCount : 0));
        
        System.out.println("\nMost Common Domains:");
        int maxCount = 0;
        String mostCommon = "N/A";
        for (int i = 0; i < uniqueDomains; i++) {
            if (domainCounts[i] > maxCount) {
                maxCount = domainCounts[i];
                mostCommon = commonDomains[i];
            }
        }
        System.out.println("- " + mostCommon + " (Count: " + maxCount + ")");
    }
}
