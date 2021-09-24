package osu.beatmap.parser;

public class OsuFileParserException extends Throwable {
    public OsuFileParserException(String headerName) {
        super("file header \"" + headerName +  "\" not found.");
    }
}
