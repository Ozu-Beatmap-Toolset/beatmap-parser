package osu.beatmap.events;

import util.math.vector.Vector2Int;

public class VideoData extends CommonEventData {

    public int startTime;
    public String fileName;
    public Vector2Int offset;

    public VideoData(final String data) {
        final String[] splitData = data.split(",");

        this.eventType = EventType.VIDEO;
        this.startTime = Integer.parseInt(splitData[1]);
        this.fileName = splitData[2];
        if(splitData.length > 3) {
            this.offset = new Vector2Int(
                    Integer.parseInt(splitData[3]),
                    Integer.parseInt(splitData[4]));
        }
        else {
            this.offset = new Vector2Int(320, 240);
        }
    }
}
