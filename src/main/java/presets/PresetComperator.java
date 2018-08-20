package presets;

import java.util.Comparator;

public class PresetComperator implements Comparator<Preset> {
    @Override
    public int compare(Preset o1, Preset o2) {
        return o1.toString().compareToIgnoreCase(o2.toString());
    }
}
