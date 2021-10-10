package osu.beatmap.serialization;

import osu.beatmap.events.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParsedEvents {

    public List<BackgroundData> backgroundData;
    public List<VideoData> videoData;
    public List<BreakData> breakData;
    public List<SpriteData> spriteData;
    public List<AnimationData> animationData;

    public ParsedEvents(final List<String> timingPointsSection) {
        this.backgroundData = new ArrayList<>();
        this.videoData = new ArrayList<>();
        this.breakData = new ArrayList<>();
        this.spriteData = new ArrayList<>();
        this.animationData = new ArrayList<>();

        finalizeParsing(timingPointsSection);
    }

    private void finalizeParsing(final List<String> eventsSection) {
        eventsSection.forEach(line ->
        {
            if(line.contains("//")) {
                return;
            }
            final String[] splitData = line.split(",");
            switch (splitData[0]) {
                case "0", "Background" -> backgroundData.add(new BackgroundData(line));
                case "1", "Video" -> videoData.add(new VideoData(line));
                case "2", "Break" -> breakData.add(new BreakData(line));
                case "Sprite" -> spriteData.add(new SpriteData(line));
                case "Animation" -> animationData.add(new AnimationData(line));
            }
        });
    }

    public List<String> asFileContent() {
        final List<String> fileContent = new ArrayList<>();

        final List<CommonEventData> eventList = new ArrayList<>();
        eventList.addAll(backgroundData);
        eventList.addAll(videoData);
        eventList.addAll(breakData);
        eventList.addAll(spriteData);
        eventList.addAll(animationData);

        fileContent.add(BeatmapParser.EVENTS_HEADER_NAME);
        fileContent.addAll(eventList.stream().map(eventData -> {
            String result = "";

            result += eventData.eventType.getValue() + ",";

            if(eventData.eventType == EventType.BACKGROUND) {
                final BackgroundData backgroundData = (BackgroundData) eventData;
                result += backgroundData.startTime + ",";
                result += backgroundData.fileName + ",";
                result += backgroundData.offset.x + ",";
                result += backgroundData.offset.y + ",";
            }
            else if(eventData.eventType == EventType.VIDEO) {
                final VideoData videoData = (VideoData) eventData;
                result += videoData.startTime + ",";
                result += videoData.fileName + ",";
                result += videoData.offset.x + ",";
                result += videoData.offset.y + ",";
            }
            else if(eventData.eventType == EventType.BREAK) {
                final BreakData breakData = (BreakData) eventData;
                result += breakData.startTime + ",";
                result += breakData.endTime + ",";
            }
            else if(eventData.eventType == EventType.SPRITE) {
                final SpriteData spriteData = (SpriteData) eventData;
            }
            else if(eventData.eventType == EventType.ANIMATION) {
                final AnimationData animationData = (AnimationData) eventData;
            }

            return result;
        }).collect(Collectors.toList()));
        fileContent.add("");

        return fileContent;
    }
}