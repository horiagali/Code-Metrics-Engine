package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MethodTest {

    String normal = "if (condition) {\n" +
            "    doSomething();\n" +
            "}";

    String empty = "";
    String complex = " int totalMethods = methods.size();\n" +
            "        int camelCaseMethods = 0;\n" +
            "\n" +
            "        for (Method method : methods) {\n" +
            "            if (isCamelCase(method.getName())) {\n" +
            "                camelCaseMethods++;\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        if (totalMethods == 0 || totalMethods==1) {\n" +
            "            return 0;\n" +
            "        } else {\n" +
            "            double percentage =\n" +
            "                    ((double) camelCaseMethods /\n" +
            "                            (totalMethods-1)) * 100; /// not considering the <init> method\n" +
            "\n" +
            "            return Double.parseDouble(String.format(\"%.2f\", percentage));\n" +
            "        }";
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
    public void testFindComplexitySimple() {
        Method method = new Method("methodName", normal);

        assertEquals(3, method.getComplexity());
    }

    @Test
    public void testFindComplexityEmpty() {
        Method method = new Method("methodName", empty);

        assertEquals(1, method.getComplexity());
    }

    @Test
    public void testFindComplexityComplex() {
        Method method = new Method("methodName", complex);

        assertEquals(7, method.getComplexity());
    }

    @Test
    public void testFindComplexityWithLoopsAndConditions() {
        String code =
                "    int x = 0;\n" +
                "    while (x < 10) {\n" +
                "        if (x % 2 == 0) {\n" +
                "            System.out.println(\"Even\");\n" +
                "        } else {\n" +
                "            System.out.println(\"Odd\");\n" +
                "        }\n" +
                "        x++;\n" +
                "    }\n";
        Method method = new Method("test", code);
        assertEquals(6, method.getComplexity());
    }

    @Test
    public void testFindComplexityWithMethodCalls() {
        String code =
                "    int a = 5;\n" +
                "    int b = 10;\n" +
                "    int sum = add(a, b);\n" +
                "    System.out.println(\"Sum: \" + sum);\n" +
                "}\n" +
                "public int add(int x, int y) {\n" +
                "    return x + y;\n" ;
        Method method = new Method("test", code);
        assertEquals(3, method.getComplexity());
    }
    @Test
    public void testLengthNormal () {
        Method method = new Method("methodName", normal);

        assertEquals(3,method.length());
    }

    @Test
    public void testLengthmpty() {
        Method method = new Method("methodName", empty);

        assertEquals(1, method.length());
    }
    @Test
    public void testLengthComplex() {
        Method method = new Method("methodName", complex);

        assertEquals(18, method.length());
    }
    @Test
    public void testEqualsSameObject() {
        Method method1 = new Method("methodName", "public void methodName() {}");
        assertTrue(method1.equals(method1));
    }

    @Test
    public void testEqualsEqualObjects() {
        Method method1 = new Method("methodName", "public void methodName() {}");
        Method method2 = new Method("methodName", "public void methodName() {}");
        assertTrue(method1.equals(method2));
    }

    @Test
    public void testEqualsDifferentName() {
        Method method1 = new Method("methodName", "public void methodName() {}");
        Method method2 = new Method("otherName", "public void methodName() {}");
        assertFalse(method1.equals(method2));
    }

    @Test
    public void testEqualsDifferentContent() {
        Method method1 = new Method("methodName", "public void methodName() {}");
        Method method2 = new Method("methodName", "public void otherMethod() {}");
        assertFalse(method1.equals(method2));
    }

    @Test
    public void testEqualsDifferentComplexity() {
        Method method1 = new Method("methodName", "public void methodName() {}");
        method1.setComplexity(5);
        Method method2 = new Method("methodName", "public void methodName() {}");
        method2.setComplexity(10);
        assertFalse(method1.equals(method2));
    }

    @Test
    public void testEqualsNullObject() {
        Method method1 = new Method("methodName", "public void methodName() {}");
        assertFalse(method1.equals(null));
    }

    @Test
    public void testEqualsDifferentClassObject() {
        Method method1 = new Method("methodName", "public void methodName() {}");
        assertFalse(method1.equals("methodName"));
    }

    @Test
    public void testHashCodeEqualObjects() {
        Method method1 = new Method("methodName", "public void methodName() {}");
        Method method2 = new Method("methodName", "public void methodName() {}");
        assertEquals(method1.hashCode(), method2.hashCode());
    }

    @Test
    public void testHashCodeDifferentObjects() {
        Method method1 = new Method("methodName", "public void methodName() {}");
        Method method2 = new Method("otherName", "public void otherMethod() {}");
        assertFalse(method1.hashCode() == method2.hashCode());
    }
}



