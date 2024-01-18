package main;

import java.util.*;

/** Class that generates all possible names from combinations
 * from a predefined prefixes and sufixes.
 * @author david.f@opendeusto.es*/
public class NameGenerator {

    // Generated names
    private ArrayList<String> names;
    private String pattern = "";

    // Possible name prefixes, suffixes and second names
    private static final String[] PREFIXES = {
            "El", "Shadow", "Fire", "Ice", "Water", "Cristal",
            "Magic", "Mystic", "Dark", "Light", "Silver", "Golden",
            "Star", "Moon", "Sun", "Storm", "Wind", "Mist", "Thunder",
            "Earth", "Ocean", "Blood", "Night", "Day", "Ancient", "Arcane",
            "Rune", "Sky", "Flame", "Frost", "Frozen", "Iron", "Steel", "Crimson",
            "Ember", "Blaze", "Echo", "Hollow", "Undeath", "Astral", "Void",
            "Cosmic", "Holly", "Cursed", "Devil", "Serpent", "Dragon", "Tiger",
            "Wolf", "Phoenix", "Griffin", "Archer", "Elder", "Abyss", "Elvis"
    };

    private static final String[] SUFFIXES = {
            "blade", "heart", "storm", "wing", "ember", "song", "whisper",
            "fang", "blaze", "spirit", "arrow", "flame", "peak", "frost",
            "sun", "moon", "burst", "light", "shade", "spark", "nova", "knight",
            "surge", "ripple", "star", "shadow", "bane", "shard", "thorn",
            "talon", "claw", "venom", "essence", "jewel", "mark", "rune",
            "hymn", "gleam", "echo", "storm", "harmony", "dream", "talisman",
            "whisper", "watcher", "aura", "cascade", "surge", "vortex", "burst", "tek"
    };

    private static final String[] SECOND_NAMES = {
            " of Anorlondo", " the Brave", " the Great", " the Bold", " the Wise",
            " the Magnificent", " the Enchanter", " the Explorer", " the Fearless",
            " the Noble", " the Wanderer", " the Silent", " the Quiet", " the Shadow",
            " the Dark", " the Eternal", " the Watcher", " the Seeker", " the Mage",
            " the Arcane", " the Fierce", " the Reckless", " the Just", " the Police",
            " the Cursed", " the Devout", "the Ancient", " the Dreamer", " the Harbinger",
            " the Radiant", " the Sentient", " the Automata", " the Exiled", " the herald",
            " the Undying", " the Eternal", " the Forgotten", " the Fallen", " the Vanguard",
            " the Beast", " the Immortal", " the Wolf", " of the Abyss", " of the Stars",
            " of the Frost", " of the Sunfire", " of the Moonlight", " of the Storms",
            " of the Shadow", " of the Crimson", " of the Dark Moon", " of Onyx", " of Murcia",
            " of Bilbao", " of Mordor", " of the Underground", " of the Thunder", " of the Eldritch",
            " of the Valleys", " of the Citadel", " of the Zenith", " of the Twilight",
            " of the Horizon", " of the Sanctuary", " of the Tundra", " of the Flames",
            " of the Caverns", " of the Flatlands", " of the Swamps", " of the Highlands",
            " of the Mountains", " of the Crest", " of the Evergreen", " of the Infinite"
    };

    /** Creates a GameGenerator, and generates all the possible names.*/
    public NameGenerator() {
        pattern = "";
        names = generateNames(PREFIXES, SUFFIXES, SECOND_NAMES, "", 3);
    }

    /** Generates all the possible names formed by combining
     * a set of prefixes suffixes and second names, recursively.
     * If you specify a patter that isn't "", it will only generate
     * names that contain that pattern specifies by setPatter()
     * method. Also generates all the possible combinations without
     * second name.
     * @param prefixes Array of possible prefixes.
     * @param suffixes Array of possible suffixes.
     * @param secondNames Array of possible second names.
     * @param n Set to 3 to generate name composed of elements from the three array.
     * @return ArrayList of all the possible name combinations*/
    private ArrayList<String> generateNames(
            String[] prefixes,
            String[] suffixes,
            String[] secondNames,
            String name,
            int n) {

        ArrayList<String> names = new ArrayList<>();

        if(n == 0) {
            // Only includes names that follow the specified pattern
            if(name.contains(pattern)) {
                names.add(name);
            }
            return names;
        }

        String[] nextPart = switch(n) {
            case 3 -> prefixes;
            case 2 -> suffixes;
            case 1 -> secondNames;
            default -> throw new IllegalStateException("Unexpected value: " + n + " > 3");
        };

        for(String next : nextPart) {
            // Also generates all the possible names without second name
            if(n == 1) {
                if(name.contains(pattern)) {
                    names.add(name);
                }
            }
            names.addAll(generateNames(prefixes, suffixes, secondNames, name + next, n - 1));
        }
        return names;
    }

    /** Returns a list of all the generated possible names.
     * @return All random names.*/
    protected ArrayList<String> getNames() {
        return names;
    }

    /** Returns a random name from all the possible names.
     * @return Random name.*/
    public String getRandomName() {
        if(!names.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(names.size());
            return names.get(randomIndex);
        }
        return "";
    }

    /** Generates all the possible names that contain
     * the specified patter. Use getRandomName() method
     * after setting the pattern to get a name with that
     * pattern.
     * @param pattern Pattern that generated names must contain.*/
    public void setPattern(String pattern) {
        this.pattern = pattern;
        names = generateNames(PREFIXES, SUFFIXES, SECOND_NAMES, pattern, 3);
    }
}
