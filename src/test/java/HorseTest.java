import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

class HorseTest {

    private static Horse horse;

    private static final class TestHorseData {
        static final String NAME = "Kelpie";
        static final double SPEED = 2.2;
        static final double DISTANCE = 10;

        static Horse validHorseWithThreeParameters() {
            return new Horse(NAME, SPEED, DISTANCE);
        }

        static Horse validHorseWithTwoParameters() {
            return new Horse(NAME, SPEED);
        }
    }

    @BeforeEach
    void setUp() {
        horse = TestHorseData.validHorseWithThreeParameters();
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNameIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 2.5);
        });
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest(name = "name = ''{0}''")
    @CsvSource(textBlock = """
            '',
            ' ',
            '   ',
            '\t',
            '\n',
            '\t\n'
            """)
    void shouldThrowIllegalArgumentExceptionWhenNameIsBlank(String invalidName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(invalidName, 2.5);
        });
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenSpeedIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Pinky", -3);
        });
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenDistanceIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Jessie", 2.1, -15);
        });
        assertEquals( "Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getName() {
        assertEquals(TestHorseData.NAME, horse.getName());
    }

    @Test
    void getSpeed() {
        assertEquals(TestHorseData.SPEED, horse.getSpeed());
    }

    @Test
    void getDistanceWithThreeParams() {
        assertEquals(TestHorseData.DISTANCE, horse.getDistance());
    }

    @Test
    void getDistanceWithTwoParams() {
        horse = new Horse(TestHorseData.NAME, TestHorseData.SPEED);
        assertEquals(0, horse.getDistance());
    }

    @ParameterizedTest(name = "randomVal={0} -> expectedDistance={1}")
    @CsvSource({
            "0.2, 10.44",
            "0.35, 10.77",
            "0.5, 11.1",
            "0.75, 11.65",
            "0.9, 11.98"
    })
    void move_updatesWalkDistanceCorrectly(double randomVal, double expectedVal) {
        try(MockedStatic<Horse> mocked = mockStatic(Horse.class)) {
            mocked.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomVal);

            horse.move();

            assertEquals(expectedVal, horse.getDistance(), 0.001);

            mocked.verify(() -> Horse.getRandomDouble(0.2, 0.9), times(1));
        }
    }
}