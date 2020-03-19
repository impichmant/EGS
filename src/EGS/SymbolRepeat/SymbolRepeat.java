package EGS.SymbolRepeat;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SymbolRepeat {

    class Line {
        private String symbol = "";
        private int lineNumber;
        private int count;

        @Override
        public String toString() {
            return "Line{" +
                    "symbol='" + symbol + '\'' +
                    ", lineNumber=" + lineNumber +
                    ", count=" + count +
                    '}';
        }

        public Line() {
        }

        public Line(String symbol) {
            this.symbol = symbol;
            lineNumber = 0;
            count = 0;
        }

        public Line(String symbol, int lineNumber, int count) {
            this.symbol = symbol;
            this.lineNumber = lineNumber;
            this.count = count;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        public int getCount() {
            return count;
        }

        public void incrementCount(int count) {
            this.count = count;
        }
    }

    private String toFind = "";
    static String inFileName = "";
    static String outFileName = "";
    private File file;
    private List<SymbolRepeat.Line> lines = new ArrayList<>();

    public void process() {

        file = new File(inFileName);

        if (file.exists()) {
            try (FileInputStream inStream = new FileInputStream(file)) {

                Scanner scanner = new Scanner(inStream);

                String tmpLine = "";
                String tmpWords[];
                int wordCount = 0;
                int lineNumber = 1;

                while (scanner.hasNextLine()) {
                    tmpLine = scanner.nextLine();

                    tmpWords = tmpLine.split(" ");

                    for (String word : tmpWords) {
                        if (word.equalsIgnoreCase(toFind))
                            wordCount++;
                    }

                    lines.add(new Line(toFind, lineNumber, wordCount));
                    lineNumber++;
                    wordCount = 0;
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                System.out.println(ex.getStackTrace());
            }
        } else {
            System.out.println("File doesn't exist.");
        }
    }

    public SymbolRepeat(String fileName, String toFind) {
        this.inFileName = fileName;
        this.toFind = toFind;
    }

    public void print() {
        for (Line line : lines)
            System.out.println(line.toString());
    }

    public void saveToFile(String outFileName) {

        File outFile = new File(outFileName);
        try (FileWriter fw = new FileWriter(outFile)) {

            for (Line line : lines)
                fw.write(line.toString() + "\n");


        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage() + outFileName);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void main(String[] args) {

        SymbolRepeat rep = new SymbolRepeat("in.txt", "hello");

        rep.process();
        rep.print();
        rep.saveToFile("out.txt");
    }
}

