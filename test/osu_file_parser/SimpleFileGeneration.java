package osu_file_parser;

import util.file.IOFile;

public class SimpleFileGeneration {

    private static final String ROOT = IOFile.getRootFileNameFromClass("test", SimpleFileGeneration.class);
    private static final String GENERATED_OSU_FILE_NAME = "generated osu file for test.osu";
    private static final String GENERATED_OSU_FILE_PATH = ROOT + GENERATED_OSU_FILE_NAME;

}
