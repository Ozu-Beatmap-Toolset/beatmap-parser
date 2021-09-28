package osu.beatmap.serialization;

import osu.beatmap.BeatMap;
import util.file.IOFile;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BeatMapParser {

    public static final String GENERAL_HEADER_NAME = "[General]";
    public static final String EDITOR_HEADER_NAME = "[Editor]";
    public static final String METADATA_HEADER_NAME = "[Metadata]";
    public static final String DIFFICULTY_HEADER_NAME = "[Difficulty]";
    public static final String EVENTS_HEADER_NAME = "[Events]";
    public static final String TIMING_POINTS_HEADER_NAME = "[TimingPoints]";
    public static final String COLORS_HEADER_NAME = "[Colours]";
    public static final String HIT_OBJECTS_HEADER_NAME = "[HitObjects]";

    public static Optional<BeatMap> decode(final File file) {
        return BeatMapDeserializer.decode(IOFile.getFileContent(file.getAbsolutePath()));
    }

    public static void encode(final BeatMap beatMap, final File file) {
        BeatMapSerializer.encode(beatMap, IOFile.getFileContent(file.getAbsolutePath()));
    }

}
