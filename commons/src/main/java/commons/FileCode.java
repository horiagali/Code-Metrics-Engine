package commons;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Object for the java file being processed
 */
public class FileCode {
    private String content;

    /**
     * constructor
     * @param filePath
     */
    public FileCode(Path filePath) {
        try {
            this.content = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            this.content = null;
        }
    }

    /**
     * gets the content  of the file
     * @return returns the content
     */
    public String getContent() {
        return content;
    }


}
