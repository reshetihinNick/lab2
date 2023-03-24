package org.Find;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;

public class Find {
    @Option(name = "-r", usage = "Search file in subdirectories in current directory")
    private boolean subDirSearch;

    @Option(name = "-d", metaVar = "directory", usage = "Search file in this directory")
    private String searchDir = ".";

    @Argument(required = true, metaVar = "filename", usage = "Name of the file to search for")
    private String fileName;

    public static void main(String[] args) {
        new Find().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar org.Find.Find.jar [-r] [-d directory] filename.txt");
            parser.printUsage(System.err);
            return;
        }
        File fileSearchDir = new File(searchDir);
        if (!fileSearchDir.exists()) {
            System.err.println("Directory " + searchDir + " does not exist.");
            return;
        }
        findFile(fileSearchDir, fileName, subDirSearch);
    }

    private boolean anyFileExtension(String desiredFile, String fileName) {
        if (!fileName.contains("."))
            return desiredFile.substring(0, desiredFile.indexOf(".")).equals(fileName) &&
                    !fileName.contains(".");
        return desiredFile.substring(0, desiredFile.indexOf("."))
                        .equals(fileName.substring(0, fileName.indexOf("."))) &&
                fileName.endsWith(".*");
    }

    private boolean anyFileInDirectory(String fileName) {
        return fileName.equals("*");
    }

    private boolean anyFileWithCurrentExtension(String desiredFile, String fileName) {
        if (fileName.startsWith("*."))
            return desiredFile.substring(desiredFile.indexOf(".") + 1, desiredFile.length() - 1)
                    .equals(fileName.substring(fileName.indexOf(".") + 1, fileName.length() - 1));
        return false;
    }

    public void findFile(File searchDir, String searchFileName, boolean subDirSearch) {
        File[] files = searchDir.listFiles();
        if (files != null) {
            for (File file : files) {
                String filename = file.getName();
                if (file.isDirectory() && subDirSearch) {
                    findFile(file, searchFileName, true);
                }
                else {
                    if (file.isFile()                              &&
                        (filename.equals(searchFileName)           ||
                        anyFileExtension(filename, searchFileName) ||
                        anyFileInDirectory(searchFileName)         ||
                        anyFileWithCurrentExtension(filename, searchFileName))) {
                            System.out.println(file.getAbsolutePath());
                    }
                }
            }
        }
    }
}
