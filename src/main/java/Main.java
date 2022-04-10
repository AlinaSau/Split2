import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws CmdLineException, IOException {
        Args arguments = new Args(args);
        List<String> fileContents;
        List<String> fileNames;
        if (arguments.fileNum != 0) {
            String text = Files.readString(new File(arguments.inputName).toPath());
            int charQuantity = (text.length() + arguments.fileNum - 1) / arguments.fileNum;
            fileContents = divideFileByChars(arguments.inputName, charQuantity);
            fileNames = getFileNames(arguments.d, arguments.fileNum, arguments.outputName);
        } else {
            if (arguments.lines != 0) {
                fileContents = divideFileByLines(arguments.inputName, arguments.lines);
            } else {
                fileContents = divideFileByChars(arguments.inputName, arguments.chars);
            }
            fileNames = getFileNames(arguments.d, fileContents.size(), arguments.outputName);
        }

        for (int i = 0; i < fileNames.size(); i++) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileNames.get(i)));
            bw.write(fileContents.get(i));
            bw.close();
        }
    }

    public static List<String> getFileNames(boolean d, int quantity, String outputName) {
        List<String> names = new ArrayList<>();
        if (d) {
            for (int i = 1; i <= quantity; i++) names.add(outputName + i);
        } else {
            int count = 1;
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++) {
                    if (count <= quantity) {
                        names.add(outputName + ((char) 96 + i) + ((char) 96 + j));
                    }
                    count++;
                }
            }
        }
        return names;
    }

    private static List<String> divideFileByLines(String inputName, int lineQuantity) throws IOException {
        List<String> lines = Files.readAllLines(new File(inputName).toPath());
        List<String> result = new ArrayList<>();
        int lineNum = (lines.size() + lineQuantity - 1) / lineQuantity;
        for (int i = 0; i < lines.size(); i += lineNum) {
            StringBuilder sb = new StringBuilder();
            for (String line: lines.subList(i, Math.min(i + lineQuantity, lines.size()))) {
                sb.append(line);
            }
            result.add(sb.toString());
        }
        return result;
    }

    private static List<String> divideFileByChars(String inputName, int charQuantity) throws IOException {
        String text = Files.readString(new File(inputName).toPath());
        List<String> result = new ArrayList<>();
        for (int i = 0; i < text.length(); i += charQuantity) {
            result.add(text.substring(i, Math.min(i + charQuantity, text.length())));
        }
        return result;
    }
}

class Args {
    @Option(name = "-o")
    String outputName;

    @Option(name = "-d")
    boolean d;

    @Option(name = "-l")
    int lines;

    @Option(name = "-c")
    int chars;

    @Option(name = "-n")
    int fileNum;

    @Argument()
    String inputName;

    Args(String[] args) throws CmdLineException {
        CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument(args);
        if (outputName == null) outputName = "x";
        if (outputName.equals("-")) outputName = inputName;
    }

}