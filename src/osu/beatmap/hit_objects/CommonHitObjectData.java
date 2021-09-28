package osu.beatmap.hit_objects;

import util.math.vector.Vector2Int;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommonHitObjectData {

    public int time;
    public int hitObjectType;
    public int hitSound;
    public List<Integer> hitSample;
    public String customAdditionSoundFileName;

    public CommonHitObjectData() {}

    public static List<Integer> parseHitSampleInts(String[] data) {
        List<Integer> hitSample;

        hitSample = Arrays.stream(Arrays.copyOfRange(data, 0, 4))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        if(hitSample.size() != 4) {
            hitSample = List.of(0, 0, 0, 0);
        }

        return hitSample;
    }

    public static String parseHitSampleCustomSoundFile(String[] data) {
        if(data.length < 5) {
            return "";
        }
        return data[4];
    }

    public static Vector2Int parsePosition(String[] splitData) {
        final int xPosition = Integer.parseInt(splitData[0]);
        final int yPosition = Integer.parseInt(splitData[1]);
        return new Vector2Int(xPosition, yPosition);
    }

    public static int parseTime(String[] splitData) {
        return Integer.parseInt(splitData[2]);
    }

    public static int parseHitObjectType(String[] splitData) {
        return Integer.parseInt(splitData[3]);
    }

    public static int parseHitSound(String[] splitData) {
        return Integer.parseInt(splitData[4]);
    }

}
