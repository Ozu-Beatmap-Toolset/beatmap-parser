package osu.beatmap;

import osu.beatmap.serialization.*;
import osu.beatmap.timing_points.GreenLineData;
import osu.beatmap.timing_points.RedLineData;

import java.util.Comparator;
import java.util.Optional;

public class Beatmap {

    public ParsedGeneral general;
    public ParsedEditor editor;
    public ParsedMetadata metadata;
    public ParsedEvents events;
    public ParsedDifficulty difficulty;
    public ParsedTimingPoints timingPoints;
    public ParsedColours colours;
    public ParsedHitObjects hitObjects;

    public Beatmap(
            final ParsedGeneral parsedGeneral,
            final ParsedEditor parsedEditor,
            final ParsedMetadata parsedMetadata,
            final ParsedDifficulty parsedDifficulty,
            final ParsedEvents parsedEvents,
            final ParsedTimingPoints parsedTimingPoints,
            final ParsedColours parsedColours,
            final ParsedHitObjects parsedHitObjects) {
        this.general = parsedGeneral;
        this.editor = parsedEditor;
        this.metadata = parsedMetadata;
        this.difficulty = parsedDifficulty;
        this.events = parsedEvents;
        this.timingPoints = parsedTimingPoints;
        this.colours = parsedColours;
        this.hitObjects = parsedHitObjects;
    }

    public Optional<Double> findBeatLengthAt(final int time) {
        final Optional<Double> beatLengthOpt = timingPoints.redLineData.stream()
                .sorted(Comparator.comparingInt(timingPoint -> timingPoint.time))
                .filter(timingPoint -> timingPoint.time <= time)
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
                .filter(timingPointTime -> timingPointTime <= time)
                .reduce(Math::max);
        if(offsetOpt.isPresent()) {
            return offsetOpt;
        }

        return timingPoints.redLineData.stream()
                .map(timingPoint -> timingPoint.time)
                .min(Comparator.comparingInt(timingPointTime -> timingPointTime));
    }

    public double findSliderVelocityAt(final int time) {
        final Optional<RedLineData> redLineData = findActiveRedLine(time);
        final Optional<GreenLineData> greenLineDataOpt = findActiveGreenLine(time);
        double pixelsPerMillis = (100 * difficulty.sliderMultiplier) / redLineData.get().beatLength;

        if(greenLineDataOpt.isPresent()) {
            if(redLineData.get().time <= greenLineDataOpt.get().time) {
                pixelsPerMillis *= -100/greenLineDataOpt.get().beatLength;
            }
        }

        return pixelsPerMillis;
    }

    private Optional<RedLineData> findActiveRedLine(final int time) {
        final Optional<RedLineData> redLineDataOpt = timingPoints.redLineData.stream()
                .sorted(Comparator.comparingInt(timingPoint -> timingPoint.time))
                .filter(timingPoint -> timingPoint.time <= time)
                .reduce((timingPoint1, timingPoint2) -> timingPoint1.time > timingPoint2.time ? timingPoint1 : timingPoint2);
        if(redLineDataOpt.isPresent()) {
            return redLineDataOpt;
        }

        return timingPoints.redLineData.stream()
                .min(Comparator.comparingInt(timingPoint -> timingPoint.time));
    }

    private Optional<GreenLineData> findActiveGreenLine(final int time) {
        return timingPoints.greenLineData.stream()
                .sorted(Comparator.comparingInt(timingPoint -> timingPoint.time))
                .filter(timingPoint -> timingPoint.time <= time)
                .reduce((timingPoint1, timingPoint2) -> timingPoint1.time > timingPoint2.time ? timingPoint1 : timingPoint2);

    }
}
