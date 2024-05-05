package commons;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.SimpleTreeVisitor;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Object for the java file being processed
 */
public class FileCode {
    private String name;
    private String content;
    private static final String METHOD_PATTERN =
            "(public|protected|private|static|\\s)+[\\w\\<\\>" +
                    "\\[\\]]*\\s+(\\w+)\\s*\\([^\\)]*\\)\\s*\\{([^\\}]*)\\}";
    private ArrayList<Method> methods = new ArrayList<>();


    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    StandardJavaFileManager fileManager =
            compiler.getStandardFileManager(
                    null,
                    null,
                    StandardCharsets.UTF_8);



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

        parseFile(filePath.toString());
        debugOut();
    }

    /**
     * prints all methods to console
     */
    private void debugOut() {
        for (Method method : methods) {
            System.out.println("Method Name: " + method.getName());
            System.out.println("Method Content: " + method.getContent());
            System.out.println("Method Complexity: " + method.getComplexity());
            System.out.println();
        }
    }

    /**
     * parses the file to add the file name and the methods
     * @param path
     * @return
     */
    private void parseFile(String path) {
        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromFiles(
                        Arrays.asList(new File(String.valueOf(path))));

        List<Method> methods = new ArrayList<>();

        JavacTask javacTask =
                (JavacTask) compiler.getTask(null, fileManager, null, null, null, compilationUnits);
        Iterable<? extends CompilationUnitTree> compilationUnitTrees;
        try {
            compilationUnitTrees = javacTask.parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (CompilationUnitTree compilationUnitTree : compilationUnitTrees) {
            for (Tree tree : compilationUnitTree.getTypeDecls()) {
                if (tree.getKind() == Tree.Kind.CLASS) {
                    ClassTree classTree = (ClassTree) tree;
                    setName(String.valueOf(classTree.getSimpleName()));
                    processClass(classTree, methods);
                }
            }
        }

        setMethods((ArrayList<Method>) methods);
    }

    /**
     * Process methods within a class
     * @param classTree
     * @param methods
     */
    private void processClass(ClassTree classTree, List<Method> methods) {
        for (Tree member : classTree.getMembers()) {
            member.accept(new SimpleTreeVisitor() {
                @Override
                public Object visitMethod(MethodTree methodTree, Object o) {
                    String methodContent = "";
                    for (Tree statement : methodTree.getBody().getStatements()) {
                        methodContent = methodContent + "\n" + statement;
                    }

                    Method method = new Method(
                            methodTree.getName().toString(), methodContent);
                    methods.add(method);
                    return null;
                }

                @Override
                public Object visitClass(ClassTree nestedClassTree, Object o) {
                    processClass(nestedClassTree, methods);
                    return null;
                }
            }, null);
        }
    }


    /**
     * checks if the name is in camelcase
     * @param name
     * @return 1 if it is camelcase 0 if not
     */
    public static boolean isCamelCase (String name) {
        String camelCasePattern = "^[a-z][a-zA-Z0-9]*$";
        return name.matches(camelCasePattern);
    }
    /**
     * Calculates the percentage of methods that are in camel case.
     *
     * @return The percentage of methods in camel case
     */
    public double percentageOfMethodsInCamelCase() {
        int totalMethods = methods.size()-1;
        int camelCaseMethods = 0;

        for (Method method : methods) {
            if (isCamelCase(method.getName())) {
                camelCaseMethods++;
            }
        }

        if (totalMethods == 0) {
            return 0;
        }


        double percentage = ((double) camelCaseMethods /
                            (totalMethods)) * 100;

            return Double.parseDouble(String.format("%.2f", percentage));

    }







    /**
     * gets the methods
     * @return returns the methods
     */
    public ArrayList<Method> getMethods() {
        return methods;
    }

    /**
     * sets methods
     * @param methods
     */
    public void setMethods(ArrayList<Method> methods) {
        this.methods = methods;
    }

    /**
     * gets the content  of the file
     * @return returns the content
     */
    public String getContent() {
        return content;
    }

    /**
     * setter
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * gets the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * equals method
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileCode fileCode = (FileCode) o;
        return Objects.equals(content, fileCode.content) &&
                Objects.equals(methods, fileCode.methods);
    }

    /**
     * hashcode
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
