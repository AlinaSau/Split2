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
        String[] names = {
                "files1\\ofile1",
                "files1\\ofile2",
                "files1\\ofile3",
                "files1\\ofile4",
                "files1\\ofile5",
                "files1\\ofile6",
                "files1\\ofile7"
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

        testHelp("-d -n 7 -o files1\\ofile files1\\1.txt".split(" "),
                names,
                expected
        );
    }

    @Test
    public void test2() throws IOException, CmdLineException {
        String[] names = {
                "files2\\ofile1",
                "files2\\ofile2",
                "files2\\ofile3",
                "files2\\ofile4",
                "files2\\ofile5"
        };

        String[] expected = {
                "ab",
                "cd",
                "ef",
                "gh",
                "i"
        };

        testHelp("-d -c 2 -o files2\\ofile files2\\2.txt".split(" "),
                names,
                expected
        );

    }

    @Test
    public void test3() throws IOException, CmdLineException {
        String[] names = {
                "files3\\ofile1",
                "files3\\ofile2",
                "files3\\ofile3",
                "files3\\ofile4",
                "files3\\ofile5"
        };

        String[] expected = {
                "ab",
                "cd",
                "ef",
                "gh",
                "i"
        };

        testHelp("-d -l 2 -o files3\\ofile files3\\3.txt".split(" "),
                names,
                expected
        );
        //не работает пока
    }

    @Test
    public void test4() throws IOException, CmdLineException {
        String[] names = {
                "files4\\x1",
                "files4\\x2",
                "files4\\x3",
                "files4\\x4",
                "files4\\x5"
        };

        String[] expected = {
                "ab",
                "cd",
                "ef",
                "gh",
                "i"
        };

        testHelp("-d -c 2 files4\\4.txt".split(" "),
                names,
                expected
        );
        //почему-то не кидает в нужную папку
    }

    @Test
    public void test5() throws IOException, CmdLineException {
        String[] names = {
                "files5\\ofileaa",
                "files5\\ofileab",
                "files5\\ofileac",
                "files5\\ofilead",
                "files5\\ofileae"
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

        testHelp("-n 7 -o files5\\ofile files5\\5.txt".split(" "),
                names,
                expected
        );

    }

}
