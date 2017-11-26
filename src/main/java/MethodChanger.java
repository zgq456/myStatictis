import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;

public class MethodChanger {

    public static void main(String[] args) throws Exception {
        // parse a file
        CompilationUnit cu = JavaParser.parse(new File("src/test/java/JDBCTemplate.java"));

        // visit and change the methods names and parameters
        cu.accept(new MethodChangerVisitor(), null);

        // prints the changed compilation unit
        System.out.println(cu);
    }

    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes.
     */
    private static class MethodChangerVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            // change the name of the method to upper case
            n.setName(n.getNameAsString().toUpperCase());

            // add a new parameter to the method
            n.addParameter("int", "value");
        }
    }
}