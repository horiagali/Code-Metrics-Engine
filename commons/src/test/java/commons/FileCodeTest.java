package commons;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(TemporaryFolderExtension.class)
public class FileCodeTest {

    @Test
    public void testConstructorAndGetters(@TempDir Path tempDir) throws IOException {
        String fileName = "Example.java";
        String content = "public class Example {\n" +
                "   void IgnOre() { }  public void method() {\n" +
                "        System.out.println(\"Hello, world!\");\n" +
                "    }\n" +
                "}";
        Path filePath = tempDir.resolve(fileName);
        Files.writeString(filePath, content);

        FileCode fileCode = new FileCode(filePath);

        assertEquals(fileName, fileCode.getName()+".java");
        assertEquals(content, fileCode.getContent());
    }

    @Test
    public void testPercentageOfMethodsInCamelCase(@TempDir Path tempDir) throws IOException {
        String fileName = "Example.java";
        String content = "public class Example {\n" +
                "  void IgnOre() { }   public void methodOne() {\n" + /// the IgnOre is there in place of the <init> method
                "    }\n" +
                "    public void method_two() {\n" +
                "    }\n" +
                "}";
        Path filePath = tempDir.resolve(fileName);
        Files.writeString(filePath, content);

        FileCode fileCode = new FileCode(filePath);

        double percentage = fileCode.percentageOfMethodsInCamelCase();
        assertEquals(50.0, percentage);
    }

    @Test
    public void testProcessClass(@TempDir Path tempDir) throws IOException {
        String fileName = "Example.java";
        String content = "public class Example {\n" +
                "   void IgnOre(){ mama}   public void method() {\n" +
                "        System.out.println(\"Hello, world!\");\n" +
                "    }\n" +
                "}";
        Path filePath = tempDir.resolve(fileName);
        Files.writeString(filePath, content);

        FileCode fileCode = new FileCode(filePath);

        ArrayList<Method> methods = fileCode.getMethods();
        assertEquals(2, methods.size());
        assertEquals(100,fileCode.percentageOfMethodsInCamelCase());
        assertEquals("method", methods.get(1).getName());
        assertEquals("System.out.println(\"Hello, world!\");", methods.get(1).getContent().trim());
    }

    @Test
    public void testIsCamelCase() {
        assertTrue(FileCode.isCamelCase("example"));
        assertTrue(FileCode.isCamelCase("camelCase"));
        assertTrue(FileCode.isCamelCase("camelCase123"));
        assertTrue(FileCode.isCamelCase("exampleMethod"));
        assertTrue(FileCode.isCamelCase("methodOne"));
        assertTrue(FileCode.isCamelCase("methodTwo"));

        // Not CamelCase
        assertFalse(FileCode.isCamelCase("Example"));
        assertFalse(FileCode.isCamelCase("ExampleMethod"));
        assertFalse(FileCode.isCamelCase("method_one"));
        assertFalse(FileCode.isCamelCase("method_Two"));
    }
}
