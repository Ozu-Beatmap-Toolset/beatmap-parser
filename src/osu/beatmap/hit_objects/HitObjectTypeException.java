package osu.beatmap.hit_objects;

public class HitObjectTypeException extends Throwable {
    public HitObjectTypeException(final int type) {
        super("error: " + type + " is a bad object type.");
    }
}
