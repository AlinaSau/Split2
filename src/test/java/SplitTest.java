import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.CmdLineException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SplitTest {

    String path = "C:/Users/ALINA/IdeaProjects/split";

    public void testHelp(String[] args, String[] names, String[] expected) throws IOException, CmdLineException {
        Main.main(args);
        for (int i = 0; i < names.length; i++) {
            assertFileContent(names[i], expected[i]);
        }
    }

    private void assertFileContent(String name, String expectedContent) {
        File file = new File(name);
        String content = null;
        try {
            content = Files.readString(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(expectedContent, content);
    }

    @Test
    public void test1() throws IOException, CmdLineException {
        PrintWriter writer = new PrintWriter("fileI");
        Random rd = new Random();
        String str = "";
        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < 5; i++) {
                StringBuilder strB = new StringBuilder();
                strB.append(rd.nextFloat());
                str = strB.toString();
            }
            writer.print(str);
            writer.append("\n");
            str = "";
        }
        writer.close();

        Main.main("-d -n 8 -o files\\ofile fileI".split(" "));
        int a = new File("C:\\Users\\ALINA\\IdeaProjects\\split\\text").listFiles().length;
        assertEquals(a, 8);
    }

    @Test
    public void test2() throws IOException, CmdLineException {
        String[] names = {
                "files\\ofile1",
                "files\\ofile2",
                "files\\ofile3",
                "files\\ofile4",
                "files\\ofile5",
                "files\\ofile6",
                "files\\ofile7"
        };

        String[] expected = {
                "a",
                "b",
                "c",
                "d",
                "e",
                "f",
                "g"
        };

        testHelp("-d -n 7 -o files\\ofile files\\1.txt".split(" "),
                names,
                expected
        );
    }

}
