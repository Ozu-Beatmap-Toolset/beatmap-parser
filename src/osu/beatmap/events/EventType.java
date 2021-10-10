package osu.beatmap.events;

public enum EventType {
    BACKGROUND("0"),
    VIDEO("1"),
    BREAK("2"),
    SPRITE("Sprite"),
    ANIMATION("Animation");

    private final String value;

    EventType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
