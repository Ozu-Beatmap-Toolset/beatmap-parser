package osu.beatmap.serialization;

import java.util.*;

public class ParsedGeneral {

    public String audioFileName;
    public int audioLeadIn;
    public int previewTime;
    public int countDown;
    public String sampleSet;
    public double stackLeniency;
    public int mode;
    public int letterboxInBreaks;
    public int useSkinSprites;
    public String overlayPosition;
    public String skinPreference;
    public int epilepsyWarning;
    public int countdownOffset;
    public int specialStyle;
    public int widescreenStoryboard;
    public int samplesMatchPlaybackRate;

    public ParsedGeneral(final List<String> difficultyData) {
        finalizeParsing(difficultyData);
    }

    private void finalizeParsing(final List<String> fileData) {
        this.audioFileName =                             extractValueOf(fileData, "AudioFilename")           .orElse("");
        this.audioLeadIn =              Integer.parseInt(extractValueOf(fileData, "AudioLeadIn")             .orElse("0"));
        this.previewTime =              Integer.parseInt(extractValueOf(fileData, "PreviewTime")             .orElse("-1"));
        this.countDown =                Integer.parseInt(extractValueOf(fileData, "Countdown")               .orElse("1"));
        this.sampleSet =                                 extractValueOf(fileData, "SampleSet")               .orElse("Normal");
        this.stackLeniency =          Double.parseDouble(extractValueOf(fileData, "StackLeniency")           .orElse("0.7"));
        this.mode =                     Integer.parseInt(extractValueOf(fileData, "Mode")                    .orElse("0"));
        this.letterboxInBreaks =        Integer.parseInt(extractValueOf(fileData, "LetterboxInBreaks")       .orElse("0"));
        this.useSkinSprites =           Integer.parseInt(extractValueOf(fileData, "UseSkinSprites")          .orElse("0"));
        this.overlayPosition =                           extractValueOf(fileData, "OverlayPosition")         .orElse("NoChange");
        this.skinPreference =                            extractValueOf(fileData, "SkinPreference")          .orElse("");
        this.epilepsyWarning =          Integer.parseInt(extractValueOf(fileData, "EpilepsyWarning")         .orElse("0"));
        this.countdownOffset =          Integer.parseInt(extractValueOf(fileData, "CountdownOffset")         .orElse("0"));
        this.specialStyle =             Integer.parseInt(extractValueOf(fileData, "SpecialStyle")            .orElse("0"));
        this.widescreenStoryboard =     Integer.parseInt(extractValueOf(fileData, "WidescreenStoryboard")    .orElse("0"));
        this.samplesMatchPlaybackRate = Integer.parseInt(extractValueOf(fileData, "SamplesMatchPlaybackRate").orElse("0"));
    }

    private Optional<String> extractValueOf(final List<String> fileData, final String designator) {
        return fileData.stream()
                .map(s -> s.split(": |:"))
                .filter(s -> s[0].equals(designator))
                .map(strings -> strings.length == 2 ? strings[1] : "")
                .findFirst();
    }

    public List<String> asFileContent() {
        final List<String> fileContent = new ArrayList<>();

        fileContent.add(BeatmapParser.GENERAL_HEADER_NAME);
        fileContent.add("AudioFilename: " + audioFileName);
        fileContent.add("AudioLeadIn: " + audioLeadIn);
        fileContent.add("PreviewTime: " + previewTime);
        fileContent.add("Countdown: " + countDown);
        fileContent.add("SampleSet: " + sampleSet);
        fileContent.add("StackLeniency: " + stackLeniency);
        fileContent.add("Mode: " + mode);
        fileContent.add("LetterboxInBreaks: " + letterboxInBreaks);
        fileContent.add("UseSkinSprites: " + useSkinSprites);
        fileContent.add("OverlayPosition: " + overlayPosition);
        fileContent.add("SkinPreference: " + skinPreference);
        fileContent.add("EpilepsyWarning: " + epilepsyWarning);
        fileContent.add("CountdownOffset: " + countdownOffset);
        fileContent.add("SpecialStyle: " + specialStyle);
        fileContent.add("WidescreenStoryboard: " + widescreenStoryboard);
        fileContent.add("SamplesMatchPlaybackRate: " + samplesMatchPlaybackRate);
        fileContent.add("");

        return fileContent;
    }
}
