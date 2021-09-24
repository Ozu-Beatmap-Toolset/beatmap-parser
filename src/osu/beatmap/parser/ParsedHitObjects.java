package osu.beatmap.parser;

import osu.beatmap.hit_objects.HitObjectType;
import osu.beatmap.hit_objects.HitObjectTypeException;
import osu.beatmap.hit_objects.circle.HitCircleData;
import osu.beatmap.hit_objects.slider.HitSliderData;
import osu.beatmap.hit_objects.spinner.HitSpinnerData;

import java.util.ArrayList;
import java.util.List;

public class ParsedHitObjects {

    public List<HitCircleData> hitCircleData;
    public List<HitSliderData> hitSliderData;
    public List<HitSpinnerData> hitSpinnerData;

    public ParsedHitObjects(List<String> hitObjectData) {
        this.hitCircleData = new ArrayList<>();
        this.hitSliderData = new ArrayList<>();
        this.hitSpinnerData = new ArrayList<>();

        finalizeParsing(hitObjectData);
    }

    private void finalizeParsing(List<String> hitObjectData) {
        hitObjectData.forEach(line -> {
            try {
                final int lineNumber = hitObjectData.indexOf(line);
                final HitObjectType hitObjectType = findTypeOfHitObject(line, lineNumber);
                switch(hitObjectType) {
                    case CIRCLE -> hitCircleData.add(new HitCircleData(line));
                    case SLIDER -> hitSliderData.add(new HitSliderData(line));
                    case SPINNER -> hitSpinnerData.add(new HitSpinnerData(line));
                    default -> {}
                }
            }
            catch (HitObjectTypeException exception) {
                exception.printStackTrace();
            }
        });
    }

    private HitObjectType findTypeOfHitObject(String line, int lineNumber) throws HitObjectTypeException {
        final String[] data = line.split(",");
        final int type = Integer.parseInt(data[3]);
        if((type & 0b1) == 0b1) {
            return HitObjectType.CIRCLE;
        }
        else if((type & 0b10) == 0b10) {
            return HitObjectType.SLIDER;
        }
        else if((type & 0b1000) == 0b1000) {
            return HitObjectType.SPINNER;
        }

        throw new HitObjectTypeException(type);
    }

}
