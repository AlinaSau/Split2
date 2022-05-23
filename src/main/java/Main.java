import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) throws CmdLineException, IOException {
        Args arguments = new Args(args);
        int i = 0;
        int charQuantity;

        if (arguments.fileNum != 0) {
            String text = Files.readString(arguments.inputFile.toPath());
            charQuantity = (text.length() + arguments.fileNum - 1) / arguments.fileNum;
        }
        else charQuantity = arguments.chars;

        BufferedReader br = Files.newBufferedReader(arguments.inputFile.toPath());

        while (true) {
            String currentFileName = getNextFileName(arguments.d, i, arguments.outputName);
            String nextString;
            if (arguments.lines != 0) nextString = getNextLines(br, arguments.lines);
            else nextString = getNextChars(br, charQuantity);
            if (nextString == null) break;
            BufferedWriter bw = new BufferedWriter(new FileWriter(currentFileName));
            bw.write(nextString);
            bw.close();
            i++;
        }
        br.close();
    }

    private static String getNextFileName(boolean d, int currentIteration, String outputName) {
        if (d) return outputName + (currentIteration + 1);
        else return outputName + (char)((int)'a' + currentIteration / 26) + (char)((int)'a' + currentIteration % 26);
    }

    private static String getNextLines(BufferedReader br, int quantity) {
        try {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < quantity; i++) {
                String line = br.readLine();
                if (line == null) {
                    if (result.isEmpty()) return null;
                    else break;
                } else {
                    result.append(line).append("\n");
                }
            }
            int endIndex = result.lastIndexOf("\n");
            if (endIndex == -1) endIndex = result.length() - 1;
            if (endIndex == -1) endIndex = 0;
            return result.substring(0, endIndex);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getNextChars(BufferedReader br, int quantity) {
        try {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < quantity; i++) {
                int c = br.read();
                if (c == -1) {
                    if (result.isEmpty()) return null;
                    else break;
                }
                result.append((char) c);
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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

    File inputFile;

    Args(String[] args) throws CmdLineException {
        CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument(args);
        inputFile = new File(inputName);
        if (outputName == null) outputName = inputFile.getParent() + File.separator + "x";
        if (outputName.equals("-")) outputName = inputName;
    }

}