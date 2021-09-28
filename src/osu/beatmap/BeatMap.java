package osu.beatmap;

import osu.beatmap.parser.ParsedHitObjects;
import osu.beatmap.parser.ParsedTimingPoints;

public class BeatMap {

    public ParsedTimingPoints timingPoints;
    public ParsedHitObjects hitObjects;

    public BeatMap(final ParsedTimingPoints parsedTimingPoints, final ParsedHitObjects parsedHitObjects) {
        this.timingPoints = parsedTimingPoints;
        this.hitObjects = parsedHitObjects;
    }
}
