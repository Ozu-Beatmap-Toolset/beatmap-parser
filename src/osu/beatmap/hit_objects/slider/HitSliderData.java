package osu.beatmap.hit_objects.slider;

import osu.beatmap.Beatmap;
import osu.beatmap.hit_objects.CommonHitObjectData;
import osu.beatmap.hit_objects.HitObjectType;
import osu.beatmap.hit_objects.SoundSet;
import osu.beatmap.operations.SliderVelocityFinder;
import osu.beatmap.serialization.ParsedDifficulty;
import osu.beatmap.serialization.ParsedTimingPoints;
import util.math.vector.Vector2Int;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HitSliderData extends CommonHitObjectData {

    public Vector2Int position;
    public boolean isNewCombo;
    public int amountOfComboColorsToSkip;
    public SliderType curveType;
    public List<Vector2Int> controlPoints;
    public int slides;
    public double length;
    public List<Integer> edgeSounds;
    public List<SoundSet> edgeSets;

    public HitSliderData(final String data) {
        finalizeParsing(data);
    }

    private void finalizeParsing(final String data) {
        String[] splitData = data.split(",");
        this.position =      CommonHitObjectData.parsePosition(splitData);
        this.time =          CommonHitObjectData.parseTime(splitData);
        this.hitObjectType = HitObjectType.SLIDER;
        this.isNewCombo =    (Integer.parseInt(splitData[3]) & 4) != 0;
        this.amountOfComboColorsToSkip = (Integer.parseInt(splitData[3]) >> 4) & 7;
        this.hitSound =      CommonHitObjectData.parseHitSound(splitData);
        this.curveType =                    this.parseCurveType(splitData);
        this.controlPoints =                this.parseControlPoints(splitData);
        this.slides =                    Integer.parseInt(splitData[6]);
        this.length =                     Double.parseDouble(splitData[7]);
        this.edgeSounds =                   this.parseEdgeSounds(splitData);
        this.edgeSets =                     this.parseEdgeSets(splitData);

        String[] stringSplitHitSample;
        try {
            stringSplitHitSample = splitData[10].split(":");
        }
        catch(final RuntimeException exception) {
            stringSplitHitSample = new String[]{"0", "0", "0", "0", ""};
        }
        this.customAdditionSoundFileName = CommonHitObjectData.parseHitSampleCustomSoundFile(stringSplitHitSample);
        this.hitSample = CommonHitObjectData.parseHitSampleInts(stringSplitHitSample);
    }

    public double getEndTime(final ParsedTimingPoints timingPoints, final ParsedDifficulty difficulty) {
        final double sliderVelocity = SliderVelocityFinder.findSliderVelocityAt(timingPoints, difficulty, time);
        return time + length*slides/sliderVelocity;
    }

    public void setEndTime(final ParsedTimingPoints timingPoints, final ParsedDifficulty difficulty, final double endTime) {
        final double sliderVelocity = SliderVelocityFinder.findSliderVelocityAt(timingPoints, difficulty, time);
        length = (endTime - time) * sliderVelocity / slides;
    }

    private List<SoundSet> parseEdgeSets(final String[] splitData) {
        try {
            return Arrays.stream(splitData[9].split("\\|"))
                    .map(s -> {
                        final String[] stringTuple = s.split(":");
                        final int normalSet = Integer.parseInt(stringTuple[0]);
                        final int additionSet = Integer.parseInt(stringTuple[1]);
                        return new SoundSet(normalSet, additionSet);
                    })
                    .collect(Collectors.toList());
        }
        catch(final RuntimeException exception) {
            return List.of(new SoundSet(0, 0), new SoundSet(0, 0));
        }
    }

    private List<Integer> parseEdgeSounds(final String[] splitData) {
        try {
            return Arrays.stream(splitData[8].split("\\|"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
        catch(final RuntimeException exception) {
            return List.of(0, 0);
        }
    }

    private List<Vector2Int> parseControlPoints(final String[] splitData) {
        final List<Vector2Int> controlPoints = new ArrayList<>();

        final String[] controlPointsStrWithLetter = splitData[5].split("\\|");
        final String[] controlPointsStr = Arrays.copyOfRange(controlPointsStrWithLetter, 1, controlPointsStrWithLetter.length);
        Arrays.stream(controlPointsStr)
                .forEach(controlPointStr -> {
                    try {
                        controlPoints.add(parseControlPoint(controlPointStr));
                    }
                    catch(final SliderControlPointException exception) {
                        exception.printStackTrace();
                    }
                });

        return controlPoints;
    }

    private SliderType parseCurveType(final String[] splitData) {
        try {
            return getCurveTypeFromLetter(splitData[5].charAt(0));
        }
        catch(final SliderTypeException exception) {
            exception.printStackTrace();
            return SliderType.UNDEFINED;
        }
    }

    private Vector2Int parseControlPoint(final String controlPointsStr) throws SliderControlPointException {
        final String[] coordinatesStr = controlPointsStr.split(":");

        if(coordinatesStr.length != 2) {
            throw new SliderControlPointException(coordinatesStr.length);
        }

        final int xCoordinate = Integer.parseInt(coordinatesStr[0]);
        final int yCoordinate = Integer.parseInt(coordinatesStr[1]);

        return new Vector2Int(xCoordinate, yCoordinate);
    }

    private SliderType getCurveTypeFromLetter(final char letter) throws SliderTypeException {
        Optional<SliderType> sliderTypeOpt = Optional.empty();

        switch(letter) {
            case 'B' -> sliderTypeOpt = Optional.of(SliderType.BEZIER);
            case 'L' -> sliderTypeOpt = Optional.of(SliderType.LINEAR);
            case 'P' -> sliderTypeOpt = Optional.of(SliderType.CIRCULAR);
        }

        if(sliderTypeOpt.isPresent()) {
            return sliderTypeOpt.get();
        }

        throw new SliderTypeException(letter);
    }
}
