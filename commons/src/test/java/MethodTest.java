import static org.junit.Assert.assertEquals;

import commons.Method;
import org.junit.Test;

public class MethodTest {

    @Test
    public void testMethodConstructorAndGetters() {
        String name = "methodName";
        String content = "public void methodName() {\n}";
        Method method = new Method(name, content);

        assertEquals(name, method.getName());
        assertEquals(content, method.getContent());
        assertEquals(1, method.getComplexity());
    }

    @Test
    public void testFindComplexity() {
        String content = "if (condition) {\n" +
                "    doSomething();\n" +
                "}";
        Method method = new Method("methodName", content);

        assertEquals(2, method.getComplexity());
    }

}
