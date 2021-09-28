package osu.beatmap.timing_points;

public class GreenLineData extends CommonTimingPointData {

    public GreenLineData(final String data) {
        finalizeParsing(data);
    }

    private void finalizeParsing(final String data) {
        final String[] splitData = data.split(",");

        this.time = Integer.parseInt(splitData[0]);
        this.beatLength = Double.parseDouble(splitData[1]);

        this.sampleSet = Integer.parseInt(splitData[3]);
        this.sampleIndex = Integer.parseInt(splitData[4]);
        this.volume = Integer.parseInt(splitData[5]);
        this.type = TimingPointType.GREEN_LINE;
        this.effects = Integer.parseInt(splitData[7]);
    }
}
