package osu.beatmap.timing_points;

public class TimingPointTypeException extends Throwable {
    public TimingPointTypeException(final int type) {
        super("error: " + type + " is a bad timing point inheritance value.");
    }
}
