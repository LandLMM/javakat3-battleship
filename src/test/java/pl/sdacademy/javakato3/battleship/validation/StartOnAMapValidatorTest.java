package pl.sdacademy.javakato3.battleship.validation;

import org.junit.Before;
import org.junit.Test;
import pl.sdacademy.javakato3.battleship.model.PlayersBoard;
import pl.sdacademy.javakato3.battleship.model.Ship;

import java.awt.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StartOnAMapValidatorTest {

    private StartOnAMapValidator validator;

    @Before
    public void init() {

        validator = new StartOnAMapValidator();
    }


    @Test
    public void shouldNotValidateWhenXIsLessThan0() {
        Ship shipToValidate = mock(Ship.class);
        when(shipToValidate.getPosition()).thenReturn(new Point(-1, 5));
        PlayersBoard boardToValidate = mock(PlayersBoard.class);

        boolean result = validator.isValid(shipToValidate, boardToValidate);

        assertFalse(result);
    }

    @Test
    public void shouldNotValidateWhenXIsGreaterThan9() {
        Ship shipToValidate = mock(Ship.class);
        when(shipToValidate.getPosition()).thenReturn(new Point(10, 5));
        PlayersBoard boardToValidate = mock(PlayersBoard.class);

        boolean result = validator.isValid(shipToValidate, boardToValidate);

        assertFalse(result);
    }

    @Test
    public void shouldNotValidateWhenYIsGreaterThan9() {
        Ship shipToValidate = mock(Ship.class);
        when(shipToValidate.getPosition()).thenReturn(new Point(5, 10));
        PlayersBoard boardToValidate = mock(PlayersBoard.class);

        boolean result = validator.isValid(shipToValidate, boardToValidate);

        assertFalse(result);
    }

    @Test
    public void shouldNotValidateProperlyPlacedShip() {
        Ship shipToValidate = mock(Ship.class);
        when(shipToValidate.getPosition()).thenReturn(new Point(4, 7));
        PlayersBoard boardToValidate = mock(PlayersBoard.class);

        boolean result = validator.isValid(shipToValidate, boardToValidate);

        assertTrue(result);
    }

}