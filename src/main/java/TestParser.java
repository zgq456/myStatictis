import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.FileInputStream;

public class TestParser {
    public static void main(String[] args) throws  Exception {
        // creates an input stream for the file to be parsed
        FileInputStream in = new FileInputStream("src/test/java/JDBCTemplate.java");

        // parse the file
        CompilationUnit cu = JavaParser.parse(in);

        // prints the resulting compilation unit to default system output
        System.out.println(cu.toString());
    }
}
