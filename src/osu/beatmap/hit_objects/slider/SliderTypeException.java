package osu.beatmap.hit_objects.slider;

public class SliderTypeException extends Throwable {
    public SliderTypeException(final char letter) {
        super("error: unknown slider type with letter '" + letter + "'.");
    }
}
