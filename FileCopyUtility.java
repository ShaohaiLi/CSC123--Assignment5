//Shaohai Li (sli34@toromail.csudh.edu)
import java.io.*;
import java.util.Scanner;

public class FileCopyUtility {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter search string: ");
        String sourceFile = sc.nextLine();
        System.out.print("Enter the target directory: ");
        String targetDirectory = sc.nextLine();
        sc.close();

        try (InputStream inputStream = new FileInputStream(sourceFile);
             OutputStream outputStream = new FileOutputStream(createFile(targetDirectory, sourceFile))) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            System.out.println("File copied successfully!");
        } catch (IOException e) {
            System.out.println("Error copying file: " + e.getMessage());
        }
    }
    private static File createFile(String targetDirectory, String sourceFile) throws IOException {
        File file = new File(targetDirectory + File.separator + new File(sourceFile).getName());
        if (!file.exists()) {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        return file;
    }
        }


