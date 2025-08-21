import java.util.Scanner;

public class Five {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Problem5 csvAnalyzer = new Problem5();
        
        System.out.println("Enter CSV data (type 'END' on a new line when finished):");
        StringBuilder csvInput = new StringBuilder();
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.equals("END")) {
                break;
            }
            csvInput.append(line).append("\n");
        }
        
        String csvData = csvInput.toString();
        
        System.out.println("\n--- CSV Data Analysis Report ---");
        csvAnalyzer.processCSVData(csvData);
    }
}

class Problem5 {
    private String[][] parsedData;
    private int rows;
    private int cols;
    private String[] headers;

    private void parseCSVData(String csv) {
        String[] lines = new String[100];
        int lineCount = 0;
        int start = 0;
        for (int i = 0; i < csv.length(); i++) {
            char c = csv.charAt(i);
            if (c == '\n') {
                lines[lineCount++] = csv.substring(start, i);
                start = i + 1;
            }
        }
        if (start < csv.length()) {
            lines[lineCount++] = csv.substring(start);
        }

        if (lineCount > 0) {
            this.headers = new String[50];
            int headerCount = 0;
            start = 0;
            for (int i = 0; i < lines[0].length(); i++) {
                char c = lines[0].charAt(i);
                if (c == ',') {
                    headers[headerCount++] = lines[0].substring(start, i);
                    start = i + 1;
                }
            }
            if (start < lines[0].length()) {
                headers[headerCount++] = lines[0].substring(start);
            }
            this.cols = headerCount;

            this.parsedData = new String[lineCount - 1][this.cols];
            this.rows = lineCount - 1;

            for (int i = 1; i < lineCount; i++) {
                String line = lines[i];
                int colIndex = 0;
                start = 0;
                boolean inQuotes = false;
                for (int j = 0; j < line.length(); j++) {
                    char c = line.charAt(j);
                    if (c == '\"') {
                        inQuotes = !inQuotes;
                    } else if (c == ',' && !inQuotes) {
                        parsedData[i - 1][colIndex++] = line.substring(start, j);
                        start = j + 1;
                    }
                }
                if (start < line.length()) {
                    parsedData[i - 1][colIndex++] = line.substring(start);
                }
            }
        }
    }
    
    private void cleanData() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String field = parsedData[i][j];
                int start = 0;
                while (start < field.length() && field.charAt(start) == ' ') start++;
                int end = field.length() - 1;
                while (end >= 0 && field.charAt(end) == ' ') end--;
                if (end >= start) {
                    parsedData[i][j] = field.substring(start, end + 1);
                } else {
                    parsedData[i][j] = "";
                }
                if (parsedData[i][j].startsWith("\"") && parsedData[i][j].endsWith("\"")) {
                    parsedData[i][j] = parsedData[i][j].substring(1, parsedData[i][j].length() - 1);
                }
            }
        }
    }
    
    private int[] getColumnWidths() {
        int[] widths = new int[cols];
        for (int i = 0; i < cols; i++) {
            widths[i] = headers[i].length();
            for (int j = 0; j < rows; j++) {
                if (parsedData[j][i].length() > widths[i]) {
                    widths[i] = parsedData[j][i].length();
                }
            }
            if (widths[i] < 10) widths[i] = 10;
        }
        return widths;
    }
    
    private void formatAndDisplay(int[] widths) {
        StringBuilder border = new StringBuilder("+");
        StringBuilder headerLine = new StringBuilder("|");
        for (int i = 0; i < cols; i++) {
            border.append("-".repeat(widths[i] + 2)).append("+");
            headerLine.append(" ").append(headers[i]).append(" ".repeat(widths[i] - headers[i].length() + 1)).append("|");
        }
        
        System.out.println(border);
        System.out.println(headerLine);
        System.out.println(border);
        
        for (int i = 0; i < rows; i++) {
            StringBuilder dataLine = new StringBuilder("|");
            for (int j = 0; j < cols; j++) {
                String field = parsedData[i][j];
                dataLine.append(" ").append(field).append(" ".repeat(widths[j] - field.length() + 1)).append("|");
            }
            System.out.println(dataLine);
        }
        System.out.println(border);
    }
    
    private void analyzeData() {
        System.out.println("\n--- Data Summary Report ---");
        System.out.println("Total records processed: " + rows);
        
        for (int i = 0; i < cols; i++) {
            System.out.println("\nColumn: " + headers[i]);
            boolean isNumeric = true;
            for (int j = 0; j < rows; j++) {
                String field = parsedData[j][i];
                if (field.length() > 0) {
                    for (int k = 0; k < field.length(); k++) {
                        char c = field.charAt(k);
                        if (c < '0' || c > '9') {
                            isNumeric = false;
                            break;
                        }
                    }
                }
                if (!isNumeric) break;
            }
            
            if (isNumeric) {
                int min = Integer.MAX_VALUE;
                int max = Integer.MIN_VALUE;
                long sum = 0;
                int count = 0;
                for (int j = 0; j < rows; j++) {
                    String field = parsedData[j][i];
                    if (field.length() > 0) {
                        int value = Integer.parseInt(field);
                        if (value < min) min = value;
                        if (value > max) max = value;
                        sum += value;
                        count++;
                    }
                }
                System.out.println("  Min: " + (count > 0 ? min : "N/A"));
                System.out.println("  Max: " + (count > 0 ? max : "N/A"));
                System.out.printf("  Avg: %.2f\n", (count > 0 ? (double) sum / count : 0.0));
            } else {
                String[] uniqueValues = new String[rows];
                int uniqueCount = 0;
                for (int j = 0; j < rows; j++) {
                    String field = parsedData[j][i];
                    boolean found = false;
                    for (int k = 0; k < uniqueCount; k++) {
                        if (uniqueValues[k].equals(field)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        uniqueValues[uniqueCount++] = field;
                    }
                }
                System.out.println("  Unique Values: " + uniqueCount);
            }
        }
    }

    public void processCSVData(String csv) {
        parseCSVData(csv);
        cleanData();
        formatAndDisplay(getColumnWidths());
        analyzeData();
    }
}
