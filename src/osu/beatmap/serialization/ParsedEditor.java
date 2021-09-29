package osu.beatmap.serialization;

import java.util.List;

public class ParsedEditor {

    public String bookmarks;
    public double distanceSpacing;
    public double beatDivisor;
    public int gridSize;
    public double timelineZoom;

    public ParsedEditor(final List<String> difficultyData) {
        finalizeParsing(difficultyData);
    }

    private void finalizeParsing(final List<String> fileData) {
        this.bookmarks =                          extractValueOf(fileData, "DistanceSpacing");
        this.distanceSpacing = Double.parseDouble(extractValueOf(fileData, "DistanceSpacing"));
        this.beatDivisor =     Double.parseDouble(extractValueOf(fileData, "BeatDivisor"));
        this.gridSize =          Integer.parseInt(extractValueOf(fileData, "GridSize"));
        this.timelineZoom =    Double.parseDouble(extractValueOf(fileData, "TimelineZoom"));
    }

    private String extractValueOf(final List<String> fileData, final String designator) {
        return fileData.stream()
                .map(s -> s.split(": |:"))
                .filter(s -> s[0].equals(designator))
                .map(strings -> strings.length == 2 ? strings[1] : "")
                .findFirst().orElse("");
    }
}
