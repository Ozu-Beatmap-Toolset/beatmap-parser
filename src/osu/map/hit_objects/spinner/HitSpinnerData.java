package osu.map.hit_objects.spinner;

import osu.map.hit_objects.CommonHitObjectData;
import utils.math.vector.Vector2Int;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HitSpinnerData extends CommonHitObjectData {

    public static final Vector2Int POSITION = new Vector2Int(256, 192);

    public int endTime;

    public HitSpinnerData(String data) {
        finalizeParsing(data);
    }

    private void finalizeParsing(String data) {
        String[] splitData = data.split(",");
        this.time =          CommonHitObjectData.parseTime(splitData);
        this.hitObjectType = CommonHitObjectData.parseHitObjectType(splitData);
        this.hitSound =      CommonHitObjectData.parseHitSound(splitData);
        this.endTime =                   Integer.parseInt(splitData[5]);

        final String[] stringSplitHistSample = splitData[6].split(":");
        this.customAdditionSoundFileName = CommonHitObjectData.parseHitSampleCustomSoundFile(stringSplitHistSample);
        this.hitSample = CommonHitObjectData.parseHitSampleInts(stringSplitHistSample);
    }
}
