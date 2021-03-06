package osu_file_parser;

import org.junit.jupiter.api.Test;
import osu.beatmap.Beatmap;
import osu.beatmap.hit_objects.HitObjectType;
import osu.beatmap.serialization.ParsedHitObjects;
import osu.beatmap.hit_objects.SoundSet;
import osu.beatmap.hit_objects.circle.HitCircleData;
import osu.beatmap.hit_objects.slider.HitSliderData;
import osu.beatmap.hit_objects.slider.SliderType;
import osu.beatmap.hit_objects.spinner.HitSpinnerData;
import osu.beatmap.serialization.ParsedTimingPoints;
import osu.beatmap.serialization.BeatmapParser;
import osu.beatmap.timing_points.RedLineData;
import util.file.IOFile;
import util.math.vector.Vector2Int;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class SimpleFileLoading {

    private static final String ROOT = IOFile.getRootFileNameFromClass("test", SimpleFileLoading.class);
    private static final String VALID_OSU_FILE_NAME = "IRyS Ch. hololive-EN - Caesura of Despair (Plads) [Insane].osu";
    private static final String INVALID_OSU_FILE_NAME = "invalid header osu file.osu";
    private static final String VALID_OSU_FILE_PATH = ROOT + VALID_OSU_FILE_NAME;
    private static final String INVALID_OSU_FILE_PATH = ROOT + INVALID_OSU_FILE_NAME;
    private static final List<String> VALID_OSU_FILE_CONTENT = IOFile.getFileContent(ROOT + VALID_OSU_FILE_NAME);

    @Test
    public void loadingValidOsuFileReturnsOptionalOfOsuMap() {
        Optional<Beatmap> filledOptional = BeatmapParser.decode(new File(VALID_OSU_FILE_PATH));

        assert filledOptional.isPresent();
    }

    @Test
    public void loadingInvalidOsuFileReturnsEmptyOptional() {
        Optional<Beatmap> emptyOptional = BeatmapParser.decode(new File(INVALID_OSU_FILE_PATH));

        assert emptyOptional.isEmpty();
    }

    @Test
    public void loadingCircleAtSpecificLineInOsuFileLoadsDataCorrectly() {
        final HitCircleData hitCircleData = new HitCircleData(VALID_OSU_FILE_CONTENT.get(96));

        assert hitCircleData.position.x == 297;
        assert hitCircleData.position.y == 109;
        assert hitCircleData.time == 4690;
        assert hitCircleData.hitObjectType == HitObjectType.CIRCLE;
        assert hitCircleData.hitSound == 0;
        assert hitCircleData.hitSample.get(0) == 1;
        assert hitCircleData.hitSample.get(1) == 0;
        assert hitCircleData.hitSample.get(2) == 0;
        assert hitCircleData.hitSample.get(3) == 0;
        assert hitCircleData.customAdditionSoundFileName.equals("");
    }

    @Test
    public void loadingSliderAtSpecificLineInOsuFileLoadsDataCorrectly() {
        final HitSliderData hitSliderData = new HitSliderData(VALID_OSU_FILE_CONTENT.get(94));

        assert hitSliderData.position.x == 240;
        assert hitSliderData.position.y == 138;
        assert hitSliderData.time == 3483;
        assert hitSliderData.hitObjectType == HitObjectType.SLIDER;
        assert hitSliderData.hitSound == 0;
        assert hitSliderData.curveType == SliderType.BEZIER;
        assert hitSliderData.controlPoints.get(0).equals(new Vector2Int(211, 136));
        assert hitSliderData.controlPoints.get(1).equals(new Vector2Int(168, 148));
        assert hitSliderData.controlPoints.get(2).equals(new Vector2Int(193, 158));
        assert hitSliderData.controlPoints.get(3).equals(new Vector2Int(154, 170));
        assert hitSliderData.controlPoints.get(4).equals(new Vector2Int(120, 167));
        assert Math.abs(hitSliderData.length - 112.499992847443) < 0.0001;
        assert hitSliderData.edgeSounds.get(0) == 0;
        assert hitSliderData.edgeSounds.get(1) == 0;
        assert hitSliderData.edgeSets.get(0).equals(new SoundSet(1, 0));
        assert hitSliderData.edgeSets.get(1).equals(new SoundSet(1, 0));
        assert hitSliderData.hitSample.get(0) == 1;
        assert hitSliderData.hitSample.get(1) == 0;
        assert hitSliderData.hitSample.get(2) == 0;
        assert hitSliderData.hitSample.get(3) == 0;
        assert hitSliderData.customAdditionSoundFileName.equals("");
    }

    @Test
    public void loadingSpinnerAtSpecificLineInOsuFileLoadsDataCorrectly() {
        final HitSpinnerData hitSpinnerData = new HitSpinnerData(VALID_OSU_FILE_CONTENT.get(117));

        assert hitSpinnerData.time == 12621;
        assert hitSpinnerData.hitObjectType == HitObjectType.SPINNER;
        assert hitSpinnerData.hitSound == 0;
        assert hitSpinnerData.endTime == 16242;
        assert hitSpinnerData.hitSample.get(0) == 1;
        assert hitSpinnerData.hitSample.get(1) == 0;
        assert hitSpinnerData.hitSample.get(2) == 0;
        assert hitSpinnerData.hitSample.get(3) == 0;
        assert hitSpinnerData.customAdditionSoundFileName.equals("");
    }

    @Test
    public void loadingHitObjectsOfValidFileLoadsTheRightAmountOfHitObjects() {
        final ParsedHitObjects parsedHitObjects = BeatmapParser.decode(new File(VALID_OSU_FILE_PATH)).get().hitObjects;

        assert parsedHitObjects.hitCircleData.size() == 283;
        assert parsedHitObjects.hitSliderData.size() == 116;
        assert parsedHitObjects.hitSpinnerData.size() == 2;
    }

    @Test
    public void loadingRedLineAtSpecifiedLineInOsuFileLoadsDataCorrectly() {
        final RedLineData redLineData = new RedLineData(VALID_OSU_FILE_CONTENT.get(51));

        assert redLineData.time == 2104;
        assert Math.abs(redLineData.beatLength - 344.827586206897) < 0.0001;
        assert redLineData.meter == 4;
        assert redLineData.sampleSet == 1;
        assert redLineData.sampleIndex == 0;
        assert redLineData.volume == 100;
        assert redLineData.effects == 0;
    }

    @Test
    public void loadingTimingPointsOfValidInOsuFileLoadsTheRightAmountOfLines() {
        final ParsedTimingPoints parsedTimingPoints = BeatmapParser.decode(new File(VALID_OSU_FILE_PATH)).get().timingPoints;

        assert parsedTimingPoints.greenLineData.size() == 33;
        assert parsedTimingPoints.redLineData.size() == 1;
    }
}
