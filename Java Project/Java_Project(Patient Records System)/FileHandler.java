import java.io.*;
import java.nio.file.*;

public class FileHandler {

    public static String saveFile(String sourcePath) {
        try {
            File dir = new File("reports");
            if (!dir.exists())
                dir.mkdir();

            File source = new File(sourcePath);
            String destPath = "reports/" + source.getName();

            Files.copy(source.toPath(), Paths.get(destPath),
                    StandardCopyOption.REPLACE_EXISTING);

            return destPath;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}