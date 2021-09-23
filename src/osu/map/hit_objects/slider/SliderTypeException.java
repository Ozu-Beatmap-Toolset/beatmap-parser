package osu.map.hit_objects.slider;

public class SliderTypeException extends Throwable {
    public SliderTypeException(char letter) {
        super("error: unknown slider type with letter '" + letter + "'.");
    }
}
