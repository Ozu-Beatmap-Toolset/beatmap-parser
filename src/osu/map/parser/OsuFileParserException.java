package osu.map.parser;

public class OsuFileParserException extends Throwable {
    public OsuFileParserException(String headerName) {
        super("file header \"" + headerName +  "\" not found.");
    }
}
