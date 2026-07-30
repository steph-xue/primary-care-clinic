package persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class DataInitializerTest {
    private static final String SAMPLE_RESOURCE = "/data/sampleClinic.json";

    @TempDir
    File tempDir;

    // Tests that a missing destination file is created from the bundled sample resource
    @Test
    void testInitializeIfMissingCreatesFileFromResource() {
        try {
            File destination = new File(tempDir, "nested/clinic.json");
            DataInitializer.initializeIfMissing(destination.getPath(), SAMPLE_RESOURCE);

            assertTrue(destination.exists());
            String content = Files.readString(destination.toPath());
            assertTrue(content.contains("\"patients\""));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // Tests that an already-existing destination file is left untouched
    @Test
    void testInitializeIfMissingDoesNotOverwriteExistingFile() {
        try {
            File destination = new File(tempDir, "clinic.json");
            Files.writeString(destination.toPath(), "existing content");

            DataInitializer.initializeIfMissing(destination.getPath(), SAMPLE_RESOURCE);

            assertEquals("existing content", Files.readString(destination.toPath()));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // Tests that initialization throws an IOException when the source resource does not exist
    @Test
    void testInitializeIfMissingThrowsWhenResourceNotFound() {
        File destination = new File(tempDir, "clinic.json");
        assertThrows(IOException.class, () ->
                DataInitializer.initializeIfMissing(destination.getPath(), "/data/doesNotExist.json"));
    }
}
