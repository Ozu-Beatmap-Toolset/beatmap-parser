package osu.beatmap;

import osu.beatmap.parser.ParsedHitObjects;
import osu.beatmap.parser.ParsedTimingPoints;

import java.util.Comparator;
import java.util.Optional;

public class BeatMap {

    public ParsedTimingPoints timingPoints;
    public ParsedHitObjects hitObjects;

    public BeatMap(final ParsedTimingPoints parsedTimingPoints, final ParsedHitObjects parsedHitObjects) {
        this.timingPoints = parsedTimingPoints;
        this.hitObjects = parsedHitObjects;
    }

    public Optional<Double> findBeatLengthAt(final int time) {
        final Optional<Double> beatLengthOpt = timingPoints.redLineData.stream()
                .sorted(Comparator.comparingInt(timingPoint -> timingPoint.time))
                .filter(timingPoint -> timingPoint.time < time)
                .reduce((timingPoint1, timingPoint2) -> timingPoint1.time > timingPoint2.time ? timingPoint1 : timingPoint2)
                .map(timingPoint -> timingPoint.beatLength);
        if(beatLengthOpt.isPresent()) {
            return beatLengthOpt;
        }

        return timingPoints.redLineData.stream()
                .sorted(Comparator.comparingInt(timingPoint -> timingPoint.time))
                .map(timingPoint -> timingPoint.beatLength)
                .findFirst();
    }

    public Optional<Integer> findTimingOffsetAt(final int time) {
        final Optional<Integer> offsetOpt = timingPoints.redLineData.stream()
                .map(timingPoint -> timingPoint.time)
                .sorted(Comparator.comparingInt(timingPointTime -> timingPointTime))
                .filter(timingPointTime -> timingPointTime < time)
                .reduce(Math::max);
        if(offsetOpt.isPresent()) {
            return offsetOpt;
        }

        return timingPoints.redLineData.stream()
                .map(timingPoint -> timingPoint.time)
                .min(Comparator.comparingInt(timingPointTime -> timingPointTime));
    }
}
