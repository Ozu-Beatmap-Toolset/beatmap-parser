package osu.beatmap.serialization;

import osu.beatmap.timing_points.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<String> asFileContent() {
        final List<String> fileContent = new ArrayList<>();
        final List<CommonTimingPointData> timingPoints = new ArrayList<>();
        timingPoints.addAll(redLineData);
        timingPoints.addAll(greenLineData);

        final List<CommonTimingPointData> sortedTimingPoints = timingPoints.stream()
                .sorted(Comparator.comparingInt(timingPoint -> timingPoint.time))
                .collect(Collectors.toList());

        final List<String> convertedTimingPoints = sortedTimingPoints.stream()
                .map(timingPoint -> {
                    String conversion = "";

                    conversion += timingPoint.time + ",";
                    conversion += timingPoint.beatLength + ",";
                    if(timingPoint.type == TimingPointType.RED_LINE) {
                        conversion += ((RedLineData)timingPoint).meter + ",";
                    }
                    else {
                        conversion += 0 + ",";
                    }
                    conversion += timingPoint.sampleSet + ",";
                    conversion += timingPoint.sampleIndex + ",";
                    conversion += timingPoint.volume + ",";
                    if(timingPoint.type == TimingPointType.RED_LINE) {
                        conversion += 1 + ",";
                    }
                    else {
                        conversion += 0 + ",";
                    }
                    conversion += timingPoint.effects;

                    return conversion;
                })
                .collect(Collectors.toList());

        fileContent.add(BeatmapParser.TIMING_POINTS_HEADER_NAME);
        fileContent.addAll(convertedTimingPoints);
        fileContent.add("");
        fileContent.add("");

        return fileContent;
    }
}