import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HippodromeTest {
    private List<Horse> horses;
    private Hippodrome hippodrome;

    @Test
    void shouldThrowIllegalArgumentExceptionWhenListIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenListIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(List.of());
        });
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorses() {
        horses = List.of(
                new Horse("Thunder", 2.85),
                new Horse("Spirit", 2.43),
                new Horse("Shadow", 2.71),
                new Horse("Storm", 2.92),
                new Horse("Blaze", 2.64),
                new Horse("Midnight", 2.38),
                new Horse("Starlight", 2.15),
                new Horse("Silver", 2.57),
                new Horse("Diamond", 2.29),
                new Horse("Apollo", 2.88),
                new Horse("Athena", 2.41),
                new Horse("Ranger", 2.66),
                new Horse("Dakota", 2.19),
                new Horse("Cherokee", 2.53),
                new Horse("Maverick", 2.77),
                new Horse("Luna", 2.34),
                new Horse("Comet", 2.95),
                new Horse("Phoenix", 2.82),
                new Horse("Sapphire", 2.26),
                new Horse("Jasper", 2.48),
                new Horse("Willow", 2.12),
                new Horse("River", 2.39),
                new Horse("Autumn", 2.05),
                new Horse("Sierra", 2.61),
                new Horse("Noble", 2.74),
                new Horse("Eclipse", 2.91),
                new Horse("Whisper", 2.22),
                new Horse("Harmony", 2.46),
                new Horse("Valor", 2.84),
                new Horse("Destiny", 2.69)
        );

        hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void move() {
        horses = IntStream.range(0, 50).mapToObj(i -> mock(Horse.class)).collect(Collectors.toList());
        hippodrome = new Hippodrome(horses);

        hippodrome.move();

        for (Horse horse : horses) {
            verify(horse, times(1)).move();
        }
    }

    @Test
    void getWinner() {
        Horse horse1 = mock(Horse.class);
        Horse horse2 = mock(Horse.class);
        Horse horse3 = mock(Horse.class);

        when(horse1.getDistance()).thenReturn(123.5);
        when(horse2.getDistance()).thenReturn(128.7);
        when(horse3.getDistance()).thenReturn(121.1);

        hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));

        Horse winner = hippodrome.getWinner();

        assertSame(horse2, winner);
    }
}