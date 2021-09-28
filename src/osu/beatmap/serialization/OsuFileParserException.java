package osu.beatmap.serialization;

public class OsuFileParserException extends Throwable {
    public OsuFileParserException(final String headerName) {
        super("file header \"" + headerName +  "\" not found.");
    }
}
