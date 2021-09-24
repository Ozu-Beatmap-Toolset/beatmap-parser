package osu.beatmap.hit_objects.circle;

import osu.beatmap.hit_objects.CommonHitObjectData;
import util.math.vector.Vector2Int;

public class HitCircleData extends CommonHitObjectData {

    public Vector2Int position;

    public HitCircleData(String data) {
        finalizeParsing(data);
    }

    private void finalizeParsing(String data) {
        String[] splitData = data.split(",");

        this.position =      CommonHitObjectData.parsePosition(splitData);
        this.time =          CommonHitObjectData.parseTime(splitData);
        this.hitObjectType = CommonHitObjectData.parseHitObjectType(splitData);
        this.hitSound =      CommonHitObjectData.parseHitSound(splitData);

        final String[] stringSplitHitSample = splitData[5].split(":");
        this.customAdditionSoundFileName = CommonHitObjectData.parseHitSampleCustomSoundFile(stringSplitHitSample);
        this.hitSample = CommonHitObjectData.parseHitSampleInts(stringSplitHitSample);
    }
}
