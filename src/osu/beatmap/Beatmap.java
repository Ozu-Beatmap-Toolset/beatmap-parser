package osu.beatmap;

import osu.beatmap.operations.TimingPointOperations;
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
}
