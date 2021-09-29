package osu.beatmap.serialization;

import osu.beatmap.colours.ColorData;
import osu.beatmap.events.EventData;

import java.util.ArrayList;
import java.util.List;

public class ParsedEvents {

    public List<EventData> eventData;

    public ParsedEvents(final List<String> timingPointsSection) {
        this.eventData = new ArrayList<>();

        finalizeParsing(timingPointsSection);
    }

    private void finalizeParsing(final List<String> eventsSection) {
        eventsSection.forEach(line -> eventData.add(new EventData(line)));
    }
}