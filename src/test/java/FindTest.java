import org.junit.Test;
import static org.junit.Assert.*;
import org.Find.Find;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.StringJoiner;

public class FindTest {
    Find find = new Find();
    File searchDir = new File(".\\files");
    String searchFileName = "file1";
    boolean subDirSearch = false;

    private String outputString(File searchDir, String searchFileName, boolean subDirSearch) {
        String[] output;
        StringJoiner result = new StringJoiner("\r\n");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        find.findFile(searchDir, searchFileName, subDirSearch);
        output = outputStream.toString().split("\r\n");
        for (String lines: output) {
            result.add(lines.substring(lines.indexOf(".")));
        }
        System.setOut(null);
        return result.toString();
    }

    @Test
    public void find() {
        assertEquals(outputString(searchDir, searchFileName, subDirSearch),
                """
                        .\\files\\file1.docx\r
                        .\\files\\file1.txt""");
        subDirSearch = true;
        assertEquals(outputString(searchDir, searchFileName, subDirSearch),
                """
                        .\\files\\dir1\\file1.txt\r
                        .\\files\\file1.docx\r
                        .\\files\\file1.txt""");
        searchFileName = "*.docx";
        assertEquals(outputString(searchDir, searchFileName, subDirSearch),
                """
                        .\\files\\file1.docx""");
        searchFileName = "*";
        assertEquals(outputString(searchDir, searchFileName, subDirSearch),
                """
                        .\\files\\dir1\\file1.txt\r
                        .\\files\\file1.docx\r
                        .\\files\\file1.txt\r
                        .\\files\\file2.txt""");
        searchFileName = "file1.txt";
        assertEquals(outputString(searchDir, searchFileName, subDirSearch),
                """
                        .\\files\\dir1\\file1.txt\r
                        .\\files\\file1.txt""");
    }
}
