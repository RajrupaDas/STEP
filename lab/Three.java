import java.util.Scanner;

public class Three {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Problem3 performanceAnalyzer = new Problem3();

        System.out.println("Enter the number of iterations (e.g., 10000, 100000):");
        int iterations = scanner.nextInt();
        
        System.out.println("\n--- Performance Analysis ---");
        performanceAnalyzer.analyzePerformance(iterations);
    }
}

class Problem3 {

    public long timeWithPlus(int iterations) {
        long startTime = System.currentTimeMillis();
        String str = "";
        for (int i = 0; i < iterations; i++) {
            str += "x";
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public long timeWithStringBuilder(int iterations) {
        long startTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append("x");
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public long timeWithStringBuffer(int iterations) {
        long startTime = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            sb.append("x");
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
    
    public void analyzePerformance(int iterations) {
        System.out.println("Number of Iterations: " + iterations);
        System.out.println("\nMeasuring performance...");
        
        long stringTime = timeWithPlus(iterations);
        long sbTime = timeWithStringBuilder(iterations);
        long sbfTime = timeWithStringBuffer(iterations);
        
        System.out.println("\n--- Performance Report ---");
        System.out.printf("%-20s | %-20s | %-20s\n", "Method", "Time Taken (ms)", "Final String Length");
        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-20s | %-20d | %-20d\n", "String (+)", stringTime, "x".length() * iterations);
        System.out.printf("%-20s | %-20d | %-20d\n", "StringBuilder", sbTime, "x".length() * iterations);
        System.out.printf("%-20s | %-20d | %-20d\n", "StringBuffer", sbfTime, "x".length() * iterations);
        System.out.println("------------------------------------------------------------------");

        System.out.println("\nAnalysis:");
        System.out.println("The `+` operator is very slow due to creating a new String object in each iteration.");
        System.out.println("`StringBuilder` is the fastest because it is mutable and not thread-safe.");
        System.out.println("`StringBuffer` is slower than `StringBuilder` because its methods are synchronized, making it thread-safe.");
    }
}
