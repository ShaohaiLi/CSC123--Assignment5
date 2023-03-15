//Shaohai Li (sli34@toromail.csudh.edu)
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileDirectoryAnalyzer {
        public static void main (String[]args){
            // check if the argument is provided
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter search string: ");

            String directoryName = args[0];
            File directory = new File(directoryName);

            // check if the directory exists and is readable
            if (!directory.exists()) {
                System.out.println("Error: Directory '" + directoryName + "' does not exist.");
                System.exit(1);
            }
            if (!directory.canRead()) {
                System.out.println("Error: No permission to read directory '" + directoryName + "'.");
                System.exit(1);
            }

            // check if the argument is a directory
            if (!directory.isDirectory()) {
                System.out.println("Error: '" + directoryName + "' is not a directory.");
                System.exit(1);
            }

            // count variables
            int totalFiles = 0;
            int totalAlphaChars = 0;
            int totalNumericChars = 0;
            int totalSpaceChars = 0;
            long totalSize = 0;

            // loop through the files in the directory
            for (File file : directory.listFiles()) {
                // skip directories
                if (file.isDirectory()) {
                    continue;
                }

                // count variables for the file
                int fileAlphaChars = 0;
                int fileNumericChars = 0;
                int fileSpaceChars = 0;
                long fileSize = file.length();
                totalSize += fileSize;

                // read the file
                try (Scanner scanner = new Scanner(file)) {
                    while (scanner.hasNext()) {
                        String line = scanner.nextLine();
                        for (int i = 0; i < line.length(); i++) {
                            char c = line.charAt(i);
                            if (Character.isAlphabetic(c)) {
                                fileAlphaChars++;
                            } else if (Character.isDigit(c)) {
                                fileNumericChars++;
                            } else if (Character.isWhitespace(c)) {
                                fileSpaceChars++;
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Error reading file: " + e.getMessage());
                }

                // output information about the file
                System.out.printf("%-20s %6d bytes %6d %6d %6d%n", file.getName(), fileSize, fileAlphaChars, fileNumericChars, fileSpaceChars);

                // update the total counts
                totalFiles++;
                totalAlphaChars += fileAlphaChars;
                totalNumericChars += fileNumericChars;
                totalSpaceChars += fileSpaceChars;
            }

            // output information about the directory
            System.out.println("Total Files : " + totalFiles);
            System.out.println("Total Alpha Chars : " + totalAlphaChars);
            System.out.println("Total Numeric Chars: " + totalNumericChars);
            System.out.println("Total Space Chars : " + totalSpaceChars);

            // output the total size on disk
            String totalSizeStr;
            if (totalSize < 1024) {
                totalSizeStr = totalSize + " bytes";
            } else if (totalSize < 1024 * 1024) {
                totalSizeStr = String.format("%.2f KB", (double) totalSize / 1024);
            } else if (totalSize < 1024 * 1024 * 1024) {
                totalSizeStr = String.format("%.2f MB", (double) totalSize / (1024 * 1024));
            } else {
                totalSizeStr = String.format("%.2f GB", (double) totalSize / (1024 * 1024 * 1024));
            }
            System.out.println("Total Size Disk: " + totalSizeStr);
        }
}
