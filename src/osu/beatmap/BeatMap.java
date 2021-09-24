package osu.beatmap;

import osu.beatmap.parser.ParsedHitObjects;

public class BeatMap {

    public ParsedHitObjects parsedHitObjects;

    public BeatMap(final ParsedHitObjects parsedHitObjects) {
        this.parsedHitObjects = parsedHitObjects;
    }
}
