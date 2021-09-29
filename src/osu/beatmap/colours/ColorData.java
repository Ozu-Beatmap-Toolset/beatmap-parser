package osu.beatmap.colours;

import osu.beatmap.timing_points.TimingPointType;

import java.awt.*;

public class ColorData {

    public Color color;

    public ColorData(final String data) {
        finalizeParsing(data);
    }

    private void finalizeParsing(final String data) {
        final String[] splitData = data.split(":")[1]
                .replace(" ", "")
                .split(",");

        final int red = Integer.parseInt(splitData[0]);
        final int green = Integer.parseInt(splitData[1]);
        final int blue = Integer.parseInt(splitData[2]);
        this.color = new Color(red, green, blue);
    }
}
