package osu.beatmap.operations;

import osu.beatmap.Beatmap;
import osu.beatmap.serialization.ParsedDifficulty;
import osu.beatmap.serialization.ParsedTimingPoints;
import osu.beatmap.timing_points.GreenLineData;
import osu.beatmap.timing_points.RedLineData;

import java.util.Optional;

public class SliderVelocityFinder {

    public static double findSliderVelocityAt(final Beatmap beatmap, final int time) {
        final ParsedTimingPoints timingPoints = beatmap.timingPoints;
        final ParsedDifficulty difficulty = beatmap.difficulty;
        final RedLineData redLineData = TimingPointOperations.findActiveRedLine(timingPoints.redLineData, time);
        final Optional<GreenLineData> greenLineDataOpt = TimingPointOperations.findActiveGreenLine(timingPoints.greenLineData, time);
        double pixelsPerMillis = (100 * difficulty.sliderMultiplier) / redLineData.beatLength;

        if(greenLineDataOpt.isPresent()) {
            if(redLineData.time <= greenLineDataOpt.get().time) {
                pixelsPerMillis *= -100/greenLineDataOpt.get().beatLength;
            }
        }

        return pixelsPerMillis;
    }
}
