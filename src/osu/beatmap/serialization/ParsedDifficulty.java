package osu.beatmap.serialization;

import java.util.List;

public class ParsedDifficulty {

    public double hpDrainRate;
    public double circleSize;
    public double overallDifficulty;
    public double approachRate;
    public double sliderMultiplier;
    public double sliderTickRate;

    public ParsedDifficulty(final List<String> difficultyData) {
        finalizeParsing(difficultyData);
    }

    private void finalizeParsing(final List<String> fileData) {
        this.hpDrainRate =       extractValueOf(fileData, "HPDrainRate");
        this.circleSize =        extractValueOf(fileData, "CircleSize");
        this.overallDifficulty = extractValueOf(fileData, "OverallDifficulty");
        this.approachRate =      extractValueOf(fileData, "ApproachRate");
        this.sliderMultiplier =  extractValueOf(fileData, "SliderMultiplier");
        this.sliderTickRate =    extractValueOf(fileData, "SliderTickRate");
    }

    private double extractValueOf(final List<String> fileData, final String designator) {
        return Double.parseDouble(fileData.stream()
                .map(s -> s.split(": |:"))
                .filter(s -> s[0].equals(designator))
                .map(strings -> strings.length == 2 ? strings[1] : "")
                .findFirst().get());
    }
}
