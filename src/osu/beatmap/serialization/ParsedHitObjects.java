package osu.beatmap.serialization;

import osu.beatmap.hit_objects.CommonHitObjectData;
import osu.beatmap.hit_objects.HitObjectType;
import osu.beatmap.hit_objects.HitObjectTypeException;
import osu.beatmap.hit_objects.circle.HitCircleData;
import osu.beatmap.hit_objects.slider.HitSliderData;
import osu.beatmap.hit_objects.slider.SliderType;
import osu.beatmap.hit_objects.spinner.HitSpinnerData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ParsedHitObjects {

    public List<HitCircleData> hitCircleData;
    public List<HitSliderData> hitSliderData;
    public List<HitSpinnerData> hitSpinnerData;

    public ParsedHitObjects(final List<String> hitObjectData) {
        this.hitCircleData = new ArrayList<>();
        this.hitSliderData = new ArrayList<>();
        this.hitSpinnerData = new ArrayList<>();

        finalizeParsing(hitObjectData);
    }

    private void finalizeParsing(final List<String> hitObjectData) {
        hitObjectData.forEach(line -> {
            try {
                final HitObjectType hitObjectType = findTypeOfHitObject(line);
                switch(hitObjectType) {
                    case CIRCLE -> hitCircleData.add(new HitCircleData(line));
                    case SLIDER -> hitSliderData.add(new HitSliderData(line));
                    case SPINNER -> hitSpinnerData.add(new HitSpinnerData(line));
                    default -> {}
                }
            }
            catch(final HitObjectTypeException exception) {
                exception.printStackTrace();
            }
        });
    }

    private HitObjectType findTypeOfHitObject(final String line) throws HitObjectTypeException {
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

    public List<String> asFileContent() {
        final List<String> fileContent = new ArrayList<>();
        final List<CommonHitObjectData> commonHitObjectData = new ArrayList<>();
        commonHitObjectData.addAll(hitCircleData);
        commonHitObjectData.addAll(hitSliderData);
        commonHitObjectData.addAll(hitSpinnerData);

        final List<CommonHitObjectData> sortedHitObjects = commonHitObjectData.stream()
                .sorted(Comparator.comparingInt(hitObject -> hitObject.time))
                .collect(Collectors.toList());

        final Function<CommonHitObjectData, String> hitObjectParser = hitObject -> {
            String conversion = "";
            if(hitObject.hitObjectType == HitObjectType.CIRCLE) {
                final HitCircleData hitCircle = (HitCircleData) hitObject;
                conversion += hitCircle.position.x + ",";
                conversion += hitCircle.position.y + ",";
                conversion += hitObject.time + ",";
                conversion += (1 | (hitCircle.isNewCombo ? 4 : 0) | hitCircle.amountOfComboColorsToSkip << 4) + ",";
                conversion += hitCircle.hitSound + ",";
                conversion += hitCircle.hitSample.stream().map(Object::toString).collect(Collectors.joining(":")) + ":" + hitCircle.customAdditionSoundFileName;
            }
            else if(hitObject.hitObjectType == HitObjectType.SLIDER) {
                final HitSliderData hitSlider = (HitSliderData) hitObject;
                conversion += hitSlider.position.x + ",";
                conversion += hitSlider.position.y + ",";
                conversion += hitObject.time + ",";
                conversion += (2 | (hitSlider.isNewCombo ? 4 : 0) | hitSlider.amountOfComboColorsToSkip << 4) + ",";
                conversion += hitSlider.hitSound + ",";
                conversion += (hitSlider.curveType == SliderType.LINEAR ? "L" : (hitSlider.curveType == SliderType.BEZIER ? "B" : "P")) + "|";
                conversion += hitSlider.controlPoints.stream().map(vector2 -> vector2.x + ":" + vector2.y).collect(Collectors.joining("|")) + ",";
                conversion += hitSlider.slides + ",";
                conversion += hitSlider.length + ",";
                conversion += hitSlider.edgeSounds.stream().map(i -> i + "").collect(Collectors.joining("|")) + ",";
                conversion += hitSlider.edgeSets.stream().map(soundSet -> soundSet.normalSet + ":" + soundSet.additionSet).collect(Collectors.joining("|")) + ",";
                conversion += hitSlider.hitSample.stream().map(Object::toString).collect(Collectors.joining(":")) + ":" + hitSlider.customAdditionSoundFileName;
            }
            else {
                final HitSpinnerData hitSpinner = (HitSpinnerData) hitObject;
                conversion += HitSpinnerData.POSITION.x + ",";
                conversion += HitSpinnerData.POSITION.y + ",";
                conversion += hitSpinner.time + ",";
                conversion += 12 + ",";  // type + combo I guess
                conversion += hitSpinner.hitSound + ",";
                conversion += hitSpinner.endTime + ",";
                conversion += hitSpinner.hitSample.stream().map(Object::toString).collect(Collectors.joining(":")) + ":" + hitSpinner.customAdditionSoundFileName;
            }

            return conversion;
        };

        final List<String> convertedHitObjects = sortedHitObjects.stream().map(hitObjectParser).collect(Collectors.toList());

        fileContent.add(BeatmapParser.HIT_OBJECTS_HEADER_NAME);
        fileContent.addAll(convertedHitObjects);
        fileContent.add("");

        return fileContent;
    }
}
