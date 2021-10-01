package osu.beatmap.serialization;

import osu.beatmap.Beatmap;
import util.file.IOFile;

import java.io.File;
import java.util.Optional;

public class BeatmapParser {

    public static final String VERSION = "v0.0.1 BETA";

    public static final String GENERAL_HEADER_NAME = "[General]";
    public static final String EDITOR_HEADER_NAME = "[Editor]";
    public static final String METADATA_HEADER_NAME = "[Metadata]";
    public static final String DIFFICULTY_HEADER_NAME = "[Difficulty]";
    public static final String EVENTS_HEADER_NAME = "[Events]";
    public static final String TIMING_POINTS_HEADER_NAME = "[TimingPoints]";
    public static final String COLORS_HEADER_NAME = "[Colours]";
    public static final String HIT_OBJECTS_HEADER_NAME = "[HitObjects]";

    public static Optional<Beatmap> decode(final File file) {
        return BeatmapDeserializer.decode(IOFile.getFileContent(file.getAbsolutePath()));
    }

    public static void encode(final Beatmap beatMap, final File file) {
        BeatmapSerializer.encode(beatMap, fileContent -> IOFile.createFileWithContent(file.getAbsolutePath(), fileContent));
    }

}
