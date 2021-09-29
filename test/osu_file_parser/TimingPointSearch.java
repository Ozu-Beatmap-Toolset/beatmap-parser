package osu_file_parser;

import org.junit.jupiter.api.Test;
import osu.beatmap.BeatMap;
import osu.beatmap.serialization.BeatMapParser;
import util.file.IOFile;

import java.io.File;

public class TimingPointSearch {

    private static final String ROOT = IOFile.getRootFileNameFromClass("test", TimingPointSearch.class);
    private static final String VALID_OSU_FILE_NAME = "IRyS Ch. hololive-EN - Caesura of Despair (Plads) [Insane].osu";
    private static final String VALID_OSU_FILE_PATH = ROOT + VALID_OSU_FILE_NAME;

    @Test
    public void callToFindBeatLengthFromBeatMapObjectFindsTheRightBeatLength() {
        BeatMap beatmap = BeatMapParser.decode(new File(VALID_OSU_FILE_PATH)).get();

        assert Math.abs(beatmap.findBeatLengthAt(1000).get() - 344.827586206897) < 0.0001;
    }

    @Test
    public void callToFindTimingOffsetFromBeatMapObjectFindsTheRightTimingOffset() {
        BeatMap beatmap = BeatMapParser.decode(new File(VALID_OSU_FILE_PATH)).get();

        assert beatmap.findTimingOffsetAt(3000).get() == 2104;
    }

    @Test
    public void callToFindInheritedBeatLengthFromBeatMapObjectFindsTheRightBeatLength() {
        BeatMap beatmap = BeatMapParser.decode(new File(VALID_OSU_FILE_PATH)).get();

        assert Math.abs(beatmap.findSliderVelocityAt(60000) - 0.435) < 0.0001;
    }

    @Test
    public void callToFindBeatLengthBeforeAnyTimingPointFindsTheFirstBeatLength() {
        BeatMap beatmap = BeatMapParser.decode(new File(VALID_OSU_FILE_PATH)).get();

        assert Math.abs(beatmap.findBeatLengthAt(1000).get() - 344.827586206897) < 0.0001;
    }

    @Test
    public void callToFindTimingOffsetBeforeAnyTimingPointFindsTheFirstOffset() {
        BeatMap beatmap = BeatMapParser.decode(new File(VALID_OSU_FILE_PATH)).get();

        assert beatmap.findTimingOffsetAt(1000).get() == 2104;
    }

}
