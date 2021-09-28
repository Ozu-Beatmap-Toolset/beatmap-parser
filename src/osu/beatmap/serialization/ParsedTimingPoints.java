package osu.beatmap.serialization;

import osu.beatmap.timing_points.GreenLineData;
import osu.beatmap.timing_points.RedLineData;
import osu.beatmap.timing_points.TimingPointType;
import osu.beatmap.timing_points.TimingPointTypeException;

import java.util.ArrayList;
import java.util.List;

public class ParsedTimingPoints {

    public List<RedLineData> redLineData;
    public List<GreenLineData> greenLineData;

    public ParsedTimingPoints(final List<String> timingPointsSection) {
        this.redLineData = new ArrayList<>();
        this.greenLineData = new ArrayList<>();

        finalizeParsing(timingPointsSection);
    }

    private void finalizeParsing(final List<String> timingPointsSection) {
        timingPointsSection.forEach(line -> {
            try {
                final TimingPointType timingPointType = findTypeOfTimingPoint(line);
                switch(timingPointType) {
                    case RED_LINE -> redLineData.add(new RedLineData(line));
                    case GREEN_LINE -> greenLineData.add(new GreenLineData(line));
                    default -> {}
                }
            }
            catch(final TimingPointTypeException exception) {
                exception.printStackTrace();
            }
        });
    }

    private TimingPointType findTypeOfTimingPoint(final String line) throws TimingPointTypeException {
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