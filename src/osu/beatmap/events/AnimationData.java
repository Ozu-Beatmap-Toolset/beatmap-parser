package osu.beatmap.events;

public class AnimationData extends CommonEventData {

    public AnimationData(final String data) {
        final String[] splitData = data.split(",");

        this.eventType = EventType.ANIMATION;
    }
}
