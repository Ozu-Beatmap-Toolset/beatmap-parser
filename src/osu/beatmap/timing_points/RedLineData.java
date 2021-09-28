package osu.beatmap.timing_points;

public class RedLineData extends CommonTimingPointData {

    public int meter;

    public RedLineData(String data) {
        finalizeParsing(data);
    }

    private void finalizeParsing(String data) {
        String[] splitData = data.split(",");

        this.time = Integer.parseInt(splitData[0]);
        this.beatLength = Double.parseDouble(splitData[1]);
        this.meter = Integer.parseInt(splitData[2]);
        this.sampleSet = Integer.parseInt(splitData[3]);
        this.sampleIndex = Integer.parseInt(splitData[4]);
        this.volume = Integer.parseInt(splitData[5]);

        this.effects = Integer.parseInt(splitData[7]);
    }
}
