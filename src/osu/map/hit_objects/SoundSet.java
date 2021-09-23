package osu.map.hit_objects;

public class SoundSet {

    public int normalSet;
    public int additionSet;

    public SoundSet(int normalSet, int additionSet) {
        this.normalSet = normalSet;
        this.additionSet = additionSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoundSet that = (SoundSet) o;
        return normalSet == that.normalSet &&
                additionSet == that.additionSet;
    }
}
