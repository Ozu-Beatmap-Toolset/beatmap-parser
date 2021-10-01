package osu.beatmap.hit_objects.circle;

import osu.beatmap.hit_objects.CommonHitObjectData;
import osu.beatmap.hit_objects.HitObjectType;
import util.math.vector.Vector2Int;

public class HitCircleData extends CommonHitObjectData {

    public Vector2Int position;
    public boolean isNewCombo;
    public int amountOfComboColorsToSkip;

    public HitCircleData(final String data) {
        finalizeParsing(data);
    }

    private void finalizeParsing(final String data) {
        String[] splitData = data.split(",");

        this.position =      CommonHitObjectData.parsePosition(splitData);
        this.time =          CommonHitObjectData.parseTime(splitData);
        this.hitObjectType = HitObjectType.CIRCLE;
        this.isNewCombo =    (Integer.parseInt(splitData[3]) & 4) != 0;
        this.amountOfComboColorsToSkip = (Integer.parseInt(splitData[3]) >> 4) & 7;
        this.hitSound =      CommonHitObjectData.parseHitSound(splitData);

        String[] stringSplitHitSample;
        try {
            stringSplitHitSample = splitData[5].split(":");
        }
        catch(RuntimeException exception) {
            stringSplitHitSample = new String[]{"0", "0", "0", "0", ""};
        }
        this.customAdditionSoundFileName = CommonHitObjectData.parseHitSampleCustomSoundFile(stringSplitHitSample);
        this.hitSample = CommonHitObjectData.parseHitSampleInts(stringSplitHitSample);
    }
}
