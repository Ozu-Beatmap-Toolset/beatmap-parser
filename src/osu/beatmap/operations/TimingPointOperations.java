package osu.beatmap.operations;

import osu.beatmap.timing_points.GreenLineData;
import osu.beatmap.timing_points.RedLineData;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TimingPointOperations {

    public static Optional<Double> findBeatLengthAt(final List<RedLineData> redLineData, final int time) {
        final Optional<Double> beatLengthOpt = redLineData.stream()
                .sorted(Comparator.comparingInt(timingPoint -> timingPoint.time))
                .filter(timingPoint -> timingPoint.time <= time)
                .reduce((timingPoint1, timingPoint2) -> timingPoint1.time > timingPoint2.time ? timingPoint1 : timingPoint2)
                .map(timingPoint -> timingPoint.beatLength);
        if(beatLengthOpt.isPresent()) {
            return beatLengthOpt;
        }

        return redLineData.stream()
                .sorted(Comparator.comparingInt(timingPoint -> timingPoint.time))
                .map(timingPoint -> timingPoint.beatLength)
                .findFirst();
    }

    public static Optional<Integer> findTimingOffsetAt(final List<RedLineData> redLineData, final int time) {
        final Optional<Integer> offsetOpt = redLineData.stream()
                .map(timingPoint -> timingPoint.time)
                .sorted(Comparator.comparingInt(timingPointTime -> timingPointTime))
                .filter(timingPointTime -> timingPointTime <= time)
                .reduce(Math::max);
        if(offsetOpt.isPresent()) {
            return offsetOpt;
        }

        return redLineData.stream()
                .map(timingPoint -> timingPoint.time)
                .min(Comparator.comparingInt(timingPointTime -> timingPointTime));
    }

    public static RedLineData findActiveRedLine(final List<RedLineData> redLineData, final int time) {
        return redLineData.stream()
                .sorted(Comparator.comparingInt(timingPoint -> timingPoint.time))
                .filter(timingPoint -> timingPoint.time <= time)
                .reduce((timingPoint1, timingPoint2) -> timingPoint1.time > timingPoint2.time ? timingPoint1 : timingPoint2)
                .orElseGet(() -> redLineData.stream()
                        .min(Comparator.comparingInt(timingPoint -> timingPoint.time)).get());

    }

    public static Optional<GreenLineData> findActiveGreenLine(final List<GreenLineData> greenLineData, final int time) {
        return greenLineData.stream()
                .sorted(Comparator.comparingInt(timingPoint -> timingPoint.time))
                .filter(timingPoint -> timingPoint.time <= time)
                .reduce((timingPoint1, timingPoint2) -> timingPoint1.time > timingPoint2.time ? timingPoint1 : timingPoint2);

    }

}
