package osu.beatmap.serialization;

import java.util.ArrayList;
import java.util.List;

public class ParsedMetadata {

    public String title;
    public String titleUnicode;
    public String artist;
    public String artistUnicode;
    public String creator;
    public String version;
    public String source;
    public String tags;
    public String beatmapId;
    public String beatmapSetId;

    public ParsedMetadata(final List<String> difficultyData) {
        finalizeParsing(difficultyData);
    }

    private void finalizeParsing(final List<String> fileData) {
        this.title =         extractValueOf(fileData, "Title");
        this.titleUnicode =  extractValueOf(fileData, "TitleUnicode");
        this.artist =        extractValueOf(fileData, "Artist");
        this.artistUnicode = extractValueOf(fileData, "ArtistUnicode");
        this.creator =       extractValueOf(fileData, "Creator");
        this.version =       extractValueOf(fileData, "Version");
        this.source =        extractValueOf(fileData, "Source");
        this.tags =          extractValueOf(fileData, "Tags");
        this.beatmapId =     extractValueOf(fileData, "BeatmapID");
        this.beatmapSetId =  extractValueOf(fileData, "BeatmapSetID");
    }

    private String extractValueOf(final List<String> fileData, final String designator) {
        return fileData.stream()
                .map(s -> s.split(": |:"))
                .filter(s -> s[0].equals(designator))
                .map(strings -> strings.length == 2 ? strings[1] : "")
                .findFirst().get();
    }

    public List<String> asFileContent() {
        final List<String> fileContent = new ArrayList<>();

        fileContent.add(BeatmapParser.METADATA_HEADER_NAME);
        fileContent.add("Title:" + title);
        fileContent.add("TitleUnicode:" + titleUnicode);
        fileContent.add("Artist:" + artist);
        fileContent.add("ArtistUnicode:" + artistUnicode);
        fileContent.add("Creator:" + creator);
        fileContent.add("Version:" + version);
        fileContent.add("Source:" + source);
        fileContent.add("Tags:" + tags);
        fileContent.add("BeatmapID:" + beatmapId);
        fileContent.add("BeatmapSetID:" + beatmapSetId);
        fileContent.add("");

        return fileContent;
    }
}
