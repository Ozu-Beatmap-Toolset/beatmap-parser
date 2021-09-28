package osu.beatmap.hit_objects.slider;

public class SliderControlPointException extends Throwable {
    public SliderControlPointException(final int amountOfCoordinates) {
        super("error: expected 2 coordinates for control point but found " + amountOfCoordinates);
    }
}
