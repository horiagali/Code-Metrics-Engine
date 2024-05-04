package commons;

import java.util.Objects;

/**
 * Represents a method in Java code.
 */
public class Method {
    private String name;
    private String content;
    private int complexity;

    /**
     * Constructs a Method object with the given name, content, and complexity.
     *
     * @param name       The name of the method
     * @param content    The content of the method
     */
    public Method(String name, String content) {
        this.name = name;
        this.content = content;
        this.complexity = findComplexity(content);
    }

    /**
     * finds the complexity given a method
     * @param content
     * @return returns the complexity, the number of statements like if, else, for and method calls
     */
    private int findComplexity(String content) {
        int complexity = 1;

        int ifCount = content.split("\\bif\\b").length - 1;
        int elseCount = content.split("\\belse\\b").length - 1;
        int elseIfCount = content.split("\\belse if\\b").length - 1;

        complexity += ifCount + elseCount + elseIfCount;

        int forCount = content.split("\\bfor\\b").length - 1;
        int whileCount = content.split("\\bwhile\\b").length - 1;
        int doWhileCount = content.split("\\bdo\\b").length - 1;

        complexity += forCount + whileCount + doWhileCount;

        int methodCallCount = content.split("\\b[a-zA-Z_][a-zA-Z0-9_]*\\s*\\(").length ;

        complexity += methodCallCount;

        return complexity;
    }


    /**
     * Gets the name of the method.
     *
     * @return The name of the method
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the method.
     *
     * @param name The name of the method
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the content of the method.
     *
     * @return The content of the method
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the method.
     *
     * @param content The content of the method
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the complexity of the method.
     *
     * @return The complexity of the method
     */
    public int getComplexity() {
        return complexity;
    }

    /**
     * Sets the complexity of the method.
     *
     * @param complexity The complexity of the method
     */
    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o The object to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Method method = (Method) o;
        return complexity == method.complexity
                && Objects.equals(name, method.name)
                && Objects.equals(content, method.content);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, content, complexity);
    }
}
