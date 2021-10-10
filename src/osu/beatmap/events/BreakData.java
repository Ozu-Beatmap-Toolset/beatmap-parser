package osu.beatmap.events;

import util.math.vector.Vector2Int;

public class BreakData extends CommonEventData {

    public int startTime;
    public int endTime;

    public BreakData(final String data) {
        final String[] splitData = data.split(",");

        this.eventType = EventType.BREAK;
        this.startTime = Integer.parseInt(splitData[1]);
        this.endTime =   Integer.parseInt(splitData[2]);
    }
}
