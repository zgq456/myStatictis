import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

public class Main {
    static int count = 0;
    public static void main(String[] args) throws Exception {
        String srcDir = "xxx";
        Collection<File> fileList = FileUtils.listFiles(new File(srcDir), new String[]{"java"}, true);
        for (File file : fileList) {
            System.out.println("file:" + file.getName());
            // creates an input stream for the file to be parsed
            FileInputStream in = new FileInputStream(file);

            // parse it
            CompilationUnit cu = JavaParser.parse(in);

            // visit and print the methods na`mes
            cu.accept(new MethodVisitor(), null);
        }

        System.out.println("###count method:" + count);
    }

    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes.
     */
    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this 
             CompilationUnit, including inner class methods */
            NodeList<AnnotationExpr> annotationExprNodeList =  n.getAnnotations();
            if(hasSpringMVCAnnonation(annotationExprNodeList)) {
                count++;
                System.out.println("  method:" + n.getName());
//                System.out.println("    annotationExprNodeList:" + annotationExprNodeList);

            }

            super.visit(n, arg);
        }

        private boolean hasSpringMVCAnnonation(NodeList<AnnotationExpr> annotationExprNodeList) {
            for(AnnotationExpr annotationExpr : annotationExprNodeList) {
                if(annotationExpr.getName().getIdentifier().contains("Mapping")) {
                    return true;
                }
            }
            return false;
        }
    }
}