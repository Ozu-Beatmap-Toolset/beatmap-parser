package osu.beatmap.parser;

import osu.beatmap.hit_objects.HitObjectType;
import osu.beatmap.timing_points.GreenLineData;
import osu.beatmap.timing_points.RedLineData;
import osu.beatmap.timing_points.TimingPointType;
import osu.beatmap.timing_points.TimingPointTypeException;

import java.util.ArrayList;
import java.util.List;

public class ParsedTimingPoints {

    public List<RedLineData> redLineData;
    public List<GreenLineData> greenLineData;

    public ParsedTimingPoints(List<String> timingPointsSection) {
        this.redLineData = new ArrayList<>();
        this.greenLineData = new ArrayList<>();

        finalizeParsing(timingPointsSection);
    }

    private void finalizeParsing(List<String> timingPointsSection) {
        timingPointsSection.forEach(line -> {
            try {
                final int lineNumber = timingPointsSection.indexOf(line);
                final TimingPointType timingPointType = findTypeOfTimingPoint(line, lineNumber);
                switch(timingPointType) {
                    case RED_LINE -> redLineData.add(new RedLineData(line));
                    case GREEN_LINE -> greenLineData.add(new GreenLineData(line));
                    default -> {}
                }
            } catch (TimingPointTypeException exception) {
                exception.printStackTrace();
            }
        });
    }

    private TimingPointType findTypeOfTimingPoint(String line, int lineNumber) throws TimingPointTypeException {
        final String[] data = line.split(",");
        final int type = Integer.parseInt(data[6]);
        if(type == 1) {
            return TimingPointType.RED_LINE;
        }
        if(type == 0) {
            return TimingPointType.GREEN_LINE;
        }
        else throw new TimingPointTypeException(type);
    }
}