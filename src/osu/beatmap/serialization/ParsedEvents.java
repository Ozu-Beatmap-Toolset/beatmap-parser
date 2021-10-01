package osu.beatmap.serialization;

import osu.beatmap.events.EventData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParsedEvents {

    public List<EventData> eventData;

    public ParsedEvents(final List<String> timingPointsSection) {
        this.eventData = new ArrayList<>();

        finalizeParsing(timingPointsSection);
    }

    private void finalizeParsing(final List<String> eventsSection) {
        eventsSection.forEach(line -> eventData.add(new EventData(line)));
    }

    public List<String> asFileContent() {
        final List<String> fileContent = new ArrayList<>();

        fileContent.add(BeatmapParser.EVENTS_HEADER_NAME);
        fileContent.addAll(eventData.stream().map(eventData -> eventData.data).collect(Collectors.toList()));
        fileContent.add("");

        return fileContent;
    }
}