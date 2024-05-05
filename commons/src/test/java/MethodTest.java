import commons.Method;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MethodTest {

    @Test
    public void testMethodConstructorAndGetters() {
        String name = "methodName";
        String content = "public void methodName() {\n}";
        Method method = new Method(name, content);

        assertEquals(name, method.getName());
        assertEquals(content, method.getContent());
        assertEquals(3, method.getComplexity());
    }

    @Test
    public void testFindComplexity() {
        String content = "if (condition) {\n" +
                "    doSomething();\n" +
                "}";
        Method method = new Method("methodName", content);

        assertEquals(5, method.getComplexity());
    }

}
