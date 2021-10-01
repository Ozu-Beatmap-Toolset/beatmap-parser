package osu.beatmap.serialization;

import osu.beatmap.colours.ColorData;
import osu.beatmap.timing_points.GreenLineData;
import osu.beatmap.timing_points.RedLineData;
import osu.beatmap.timing_points.TimingPointType;
import osu.beatmap.timing_points.TimingPointTypeException;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ParsedColours {

    public List<ColorData> colorData;

    public ParsedColours(final List<String> timingPointsSection) {
        this.colorData = new ArrayList<>();

        finalizeParsing(timingPointsSection);
    }

    private void finalizeParsing(final List<String> coloursSection) {
        coloursSection.forEach(line -> {
            try {
                colorData.add(new ColorData(line));
            }
            catch(final RuntimeException exception) {
                exception.printStackTrace();
            }
        });
    }

    public List<String> asFileContent() {
        final List<String> fileContent = new ArrayList<>();

        final List<String> convertedColors = colorData.stream()
                .map(colorData -> {
                    final int indexOfColor = this.colorData.indexOf(colorData) + 1;
                    final Color color = colorData.color;
                    return "Combo" + indexOfColor + " : " + color.getRed() + "," + color.getGreen() + "," + color.getBlue();
                })
                .collect(Collectors.toList());

        fileContent.add(BeatmapParser.COLORS_HEADER_NAME);
        fileContent.addAll(convertedColors);
        fileContent.add("");

        return fileContent;
    }
}