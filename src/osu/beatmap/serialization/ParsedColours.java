package osu.beatmap.serialization;

import osu.beatmap.colours.ColorData;
import osu.beatmap.timing_points.GreenLineData;
import osu.beatmap.timing_points.RedLineData;
import osu.beatmap.timing_points.TimingPointType;
import osu.beatmap.timing_points.TimingPointTypeException;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
}