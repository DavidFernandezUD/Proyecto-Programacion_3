package main;

import main.entities.Enemy;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/** NameGenerator Test.
 * @author david.f@opendeusto.es*/
public class NameGeneratorTest {

    private NameGenerator nameGenerator;
    private ArrayList<String> names;

    @Before
    public void setUp() {
        nameGenerator = new NameGenerator();
        names = nameGenerator.getNames();
    }

    @Test
    public void generateNamesTest() {

        assertEquals(415140, names.size()); // Correct number of possible names with and without second name

        // Compound Names
        assertTrue(names.contains("Elvistek of Bilbao"));
        assertTrue(names.contains("Hollowknight of the Abyss"));

        // Simple Names
        assertTrue(names.contains("Wolfclaw"));
        assertTrue(names.contains("Abysswatcher"));

    }

    @Test
    public void getRandomNameTest() {

        nameGenerator.setPattern("Elvis");
        for(int i = 0; i < 100000; i++) {
            assertTrue(nameGenerator.getRandomName().contains("Elvis"));
        }
        nameGenerator.setPattern("");

        // Checks if at least 99% of the 100 random name lists don't contain repeated names
        int n_repeated = 0;
        for(int i = 0; i < 100; i++) {
            boolean repeated = false;
            ArrayList<String> generatedNames = new ArrayList<>();
            for(int j = 0; j < 100; j++) {
                String name = nameGenerator.getRandomName();
                repeated = repeated || generatedNames.contains(name);
                generatedNames.add(name);
            }
            n_repeated += (repeated) ? 1 : 0;
        }

        assertTrue(n_repeated <= 99);
    }

}
