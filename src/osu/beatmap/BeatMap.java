package osu.beatmap;

import osu.beatmap.parser.ParsedHitObjects;

public class BeatMap {

    public ParsedHitObjects hitObjects;

    public BeatMap(final ParsedHitObjects parsedHitObjects) {
        this.hitObjects = parsedHitObjects;
    }
}
