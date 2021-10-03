package osu_file_parser;

import org.junit.jupiter.api.Test;
import osu.beatmap.Beatmap;
import osu.beatmap.operations.SliderVelocityFinder;
import osu.beatmap.operations.TimingPointOperations;
import osu.beatmap.serialization.BeatmapParser;
import util.file.IOFile;

import java.io.File;

public class TimingPointSearch {

    private static final String ROOT = IOFile.getRootFileNameFromClass("test", TimingPointSearch.class);
    private static final String VALID_OSU_FILE_NAME = "IRyS Ch. hololive-EN - Caesura of Despair (Plads) [Insane].osu";
    private static final String VALID_OSU_FILE_PATH = ROOT + VALID_OSU_FILE_NAME;

    @Test
    public void callToFindBeatLengthFromBeatMapObjectFindsTheRightBeatLength() {
        Beatmap beatmap = BeatmapParser.decode(new File(VALID_OSU_FILE_PATH)).get();

        assert Math.abs(TimingPointOperations.findBeatLengthAt(beatmap.timingPoints.redLineData, 1000).get() - 344.827586206897) < 0.0001;
    }

    @Test
    public void callToFindTimingOffsetFromBeatMapObjectFindsTheRightTimingOffset() {
        Beatmap beatmap = BeatmapParser.decode(new File(VALID_OSU_FILE_PATH)).get();

        assert TimingPointOperations.findTimingOffsetAt(beatmap.timingPoints.redLineData, 3000).get() == 2104;
    }

    @Test
    public void callToFindInheritedBeatLengthFromBeatMapObjectFindsTheRightBeatLength() {
        Beatmap beatmap = BeatmapParser.decode(new File(VALID_OSU_FILE_PATH)).get();

        assert Math.abs(SliderVelocityFinder.findSliderVelocityAt(beatmap.timingPoints, beatmap.difficulty,60000) - 0.6524999585151667) < 0.0001;
    }

    @Test
    public void callToFindBeatLengthBeforeAnyTimingPointFindsTheFirstBeatLength() {
        Beatmap beatmap = BeatmapParser.decode(new File(VALID_OSU_FILE_PATH)).get();

        assert Math.abs(TimingPointOperations.findBeatLengthAt(beatmap.timingPoints.redLineData, 1000).get() - 344.827586206897) < 0.0001;
    }

    @Test
    public void callToFindTimingOffsetBeforeAnyTimingPointFindsTheFirstOffset() {
        Beatmap beatmap = BeatmapParser.decode(new File(VALID_OSU_FILE_PATH)).get();

        assert TimingPointOperations.findTimingOffsetAt(beatmap.timingPoints.redLineData, 1000).get() == 2104;
    }

}
