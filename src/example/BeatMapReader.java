package example;

import osu.beatmap.BeatMap;
import osu.beatmap.hit_objects.circle.HitCircleData;
import osu.beatmap.serialization.ParsedHitObjects;
import osu.beatmap.serialization.BeatMapParser;
import util.file.IOFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class BeatMapReader {

    public static void main(String[] args) {
        // create the file object
        final String root = IOFile.getRootFileNameFromClass("src", BeatMapReader.class);
        final String osuBeatMapFileName = "IRyS Ch. hololive-EN - Caesura of Despair (Plads) [Insane].osu";
        final File beatMapFile = new File(root + osuBeatMapFileName);

        // get the data object from the beatmap file
        Optional<BeatMap> map = BeatMapParser.decode(beatMapFile);

        // do stuff with it!
        map.ifPresent(beatMap -> {
            final ParsedHitObjects beatMapData = beatMap.hitObjects;
            final List<HitCircleData> circleDataList = beatMapData.hitCircleData;

            circleDataList.stream()
                    .filter(hitCircleData -> hitCircleData.position.x > 120)
                    .findFirst()
                    .ifPresent(hitCircleData -> System.out.println(hitCircleData.position.x));
        });
    }
}
