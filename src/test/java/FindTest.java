import org.junit.Test;
import static org.junit.Assert.*;
import org.Find.Find;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

public class FindTest {
    Find find = new Find();
    File searchDir = new File(".\\files");
    String searchFileName = "file1";
    boolean subDirSearch = false;

    private String outputString(File searchDir, String searchFileName, boolean subDirSearch) {
        String output;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        find.findFile(searchDir, searchFileName, subDirSearch);
        output = outputStream.toString();
        System.setOut(null);
        return output;
    }

    @Test
    public void find() {
        assertEquals(outputString(searchDir, searchFileName, subDirSearch),
                """
                        C:\\Users\\reshe\\IdeaProjects\\lab2\\.\\files\\file1.docx\r
                        C:\\Users\\reshe\\IdeaProjects\\lab2\\.\\files\\file1.txt\r
                        """);
        subDirSearch = true;
        assertEquals(outputString(searchDir, searchFileName, subDirSearch),
                """
                        C:\\Users\\reshe\\IdeaProjects\\lab2\\.\\files\\dir1\\file1.txt\r
                        C:\\Users\\reshe\\IdeaProjects\\lab2\\.\\files\\file1.docx\r
                        C:\\Users\\reshe\\IdeaProjects\\lab2\\.\\files\\file1.txt\r
                        """);
        searchFileName = "*.docx";
        assertEquals(outputString(searchDir, searchFileName, subDirSearch),
                """
                        C:\\Users\\reshe\\IdeaProjects\\lab2\\.\\files\\file1.docx\r
                        """);
        searchFileName = "*";
        assertEquals(outputString(searchDir, searchFileName, subDirSearch),
                """
                        C:\\Users\\reshe\\IdeaProjects\\lab2\\.\\files\\dir1\\file1.txt\r
                        C:\\Users\\reshe\\IdeaProjects\\lab2\\.\\files\\file1.docx\r
                        C:\\Users\\reshe\\IdeaProjects\\lab2\\.\\files\\file1.txt\r
                        C:\\Users\\reshe\\IdeaProjects\\lab2\\.\\files\\file2.txt\r
                        """);
        searchFileName = "file1.txt";
        assertEquals(outputString(searchDir, searchFileName, subDirSearch),
                """
                        C:\\Users\\reshe\\IdeaProjects\\lab2\\.\\files\\dir1\\file1.txt\r
                        C:\\Users\\reshe\\IdeaProjects\\lab2\\.\\files\\file1.txt\r
                        """);
    }
}
