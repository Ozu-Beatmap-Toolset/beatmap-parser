package osu.map.hit_objects.slider;

public class SliderControlPointException extends Throwable {
    public SliderControlPointException(int amountOfCoordinates) {
        super("error: expected 2 coordinates for control point but found " + amountOfCoordinates);
    }
}
