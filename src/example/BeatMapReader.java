package example;

import osu.beatmap.Beatmap;
import osu.beatmap.hit_objects.circle.HitCircleData;
import osu.beatmap.serialization.ParsedHitObjects;
import osu.beatmap.serialization.BeatmapParser;
import util.file.IOFile;
import util.math.vector.Vector2Int;

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
        Optional<Beatmap> map = BeatmapParser.decode(beatMapFile);

        // do stuff with it
        // here, we just peak the first circle that we find after 120 pixels on the x axis
        map.ifPresent(beatMap -> {
            final ParsedHitObjects beatMapData = beatMap.hitObjects;
            final List<HitCircleData> circleDataList = beatMapData.hitCircleData;

            circleDataList.stream()
                    .filter(hitCircleData -> hitCircleData.position.x > 120)
                    .findFirst()
                    .ifPresent(hitCircleData -> System.out.println("Some arbitrary position of a circle: " + hitCircleData.position.x));
        });

        // you can also modify the data the beatmap contains
        // here we just set the x position of the same circle we just peaked earlier to 0
        map.ifPresent(beatMap -> {
            final ParsedHitObjects beatMapData = beatMap.hitObjects;
            final List<HitCircleData> circleDataList = beatMapData.hitCircleData;

            circleDataList.stream()
                    .filter(hitCircleData -> hitCircleData.position.x > 120)
                    .findFirst()
                    .ifPresent(hitCircleData -> hitCircleData.position.x = 0);
        });

        // you can also export the beatmap and play it in the game
        map.ifPresent(beatmap -> {
            final File destination = new File("src\\example\\some filename.osu");
            BeatmapParser.encode(beatmap, destination);
        });
    }
}
