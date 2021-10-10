package osu.beatmap.events;

public class SpriteData extends CommonEventData {

    public SpriteData(final String data) {
        final String[] splitData = data.split(",");

        this.eventType = EventType.SPRITE;
    }
}
