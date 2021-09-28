package osu.beatmap.parser;

import osu.beatmap.BeatMap;
import util.file.IOFile;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Parser {

    public static final String GENERAL_HEADER_NAME = "[General]";
    public static final String EDITOR_HEADER_NAME = "[Editor]";
    public static final String METADATA_HEADER_NAME = "[Metadata]";
    public static final String DIFFICULTY_HEADER_NAME = "[Difficulty]";
    public static final String EVENTS_HEADER_NAME = "[Events]";
    public static final String TIMING_POINTS_HEADER_NAME = "[TimingPoints]";
    public static final String COLORS_HEADER_NAME = "[Colours]";
    public static final String HIT_OBJECTS_HEADER_NAME = "[HitObjects]";

    public static Optional<BeatMap> decode(final File file) {
        return decode(IOFile.getFileContent(file.getAbsolutePath()));
    }

    private static Optional<BeatMap> decode(final List<String> lines) {
        try {
            final List<String> generalSection =      extractOsuFileSection(GENERAL_HEADER_NAME, lines);
            final List<String> editorSection =       extractOsuFileSection(EDITOR_HEADER_NAME, lines);
            final List<String> metaDataSection =     extractOsuFileSection(METADATA_HEADER_NAME, lines);
            final List<String> difficultySection =   extractOsuFileSection(DIFFICULTY_HEADER_NAME, lines);
            final List<String> eventsSection =       extractOsuFileSection(EVENTS_HEADER_NAME, lines);
            final List<String> timingPointsSection = extractOsuFileSection(TIMING_POINTS_HEADER_NAME, lines);
            final List<String> colorsSection =       extractOsuFileSection(COLORS_HEADER_NAME, lines);
            final List<String> hitObjectsSection =   extractOsuFileSection(HIT_OBJECTS_HEADER_NAME, lines);

            final ParsedTimingPoints parsedTimingPoints = new ParsedTimingPoints(timingPointsSection);
            final ParsedHitObjects parsedHitObjects = new ParsedHitObjects(hitObjectsSection);

            return Optional.of(new BeatMap(parsedTimingPoints, parsedHitObjects));
        }
        catch (final OsuFileParserException exception) {
            exception.printStackTrace();
            return Optional.empty();
        }
    }

    private static List<String> extractOsuFileSection(final String nameOfSectionToExtract, final List<String> lines) throws OsuFileParserException {
        final int headerLineNumber = findHeaderLineNumber(nameOfSectionToExtract, lines);
        throwOnInvalidLineNumber(nameOfSectionToExtract, headerLineNumber);
        final int endOfSectionLineNumber = findEndOfSectionLineNumber(headerLineNumber, lines);


        return lines.subList(1 + headerLineNumber, endOfSectionLineNumber)
                .stream()
                .filter(s -> !s.equals(""))
                .collect(Collectors.toList());
    }

    private static void throwOnInvalidLineNumber(final String headerName, final int headerLineNumber) throws OsuFileParserException {
        if(headerLineNumber < 0) {
            throw new OsuFileParserException(headerName);
        }
    }

    private static int findHeaderLineNumber(String header, List<String> lines) {
        int indexOfLineContainingHeaderName = -1;

        for(int i = 0; i < lines.size(); i++) {
            final String line = lines.get(i);
            if (line.contains(header)) {
                indexOfLineContainingHeaderName = i;
                break;
            }
        }

        return indexOfLineContainingHeaderName;
    }

    private static int findEndOfSectionLineNumber(int headerLineNumber, List<String> lines) {
        int indexOfTheLastLineOfTheSection = -1;

        for(int i = headerLineNumber + 1; i < lines.size(); i++) {
            final String line = lines.get(i);
            if(line.contains("[")) {
                indexOfTheLastLineOfTheSection = i - 1;
                break;
            }
        }
        if(indexOfTheLastLineOfTheSection < 0) {
            indexOfTheLastLineOfTheSection = lines.size();
        }

        return indexOfTheLastLineOfTheSection;
    }

}
