package osu.map.hit_objects;

public class HitObjectTypeException extends Throwable {
    public HitObjectTypeException(int type) {
        super("error: " + type + " is a bad object type.");
    }
}
