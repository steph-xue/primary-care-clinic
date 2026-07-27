package persistence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

// Ensures a data file exists at a given destination on disk, initializing it from a bundled
// classpath resource (e.g. sample clinic data) the first time the application is run
public class DataInitializer {

    // MODIFIES: file system
    // EFFECTS: if no file exists at destination, creates its parent directories (if needed)
    // and copies the contents of the given classpath resource to it;
    // throws IOException if the resource cannot be found or the file cannot be written
    public static void initializeIfMissing(String destination, String resourcePath) throws IOException {
        File file = new File(destination);
        if (file.exists()) {
            return;
        }

        File parentDir = file.getParentFile();
        if (parentDir != null) {
            parentDir.mkdirs();
        }

        try (InputStream in = DataInitializer.class.getResourceAsStream(resourcePath);
                FileOutputStream out = new FileOutputStream(file)) {
            if (in == null) {
                throw new IOException("Sample data resource not found: " + resourcePath);
            }
            in.transferTo(out);
        }
    }
}
