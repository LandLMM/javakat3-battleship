package pl.sdacademy.javakato3.battleship.validation;

import org.junit.Before;
import org.junit.Test;
import pl.sdacademy.javakato3.battleship.model.PlayersBoard;
import pl.sdacademy.javakato3.battleship.model.Ship;
import pl.sdacademy.javakato3.battleship.model.ShipType;

import java.awt.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShipOutOfBoundsValidatorTest {

    private ShipOutOfBoundsValidator validator;

    @Before
    public void init() {
        validator = new ShipOutOfBoundsValidator();
    }

    @Test
    public void shouldNotValidateHorizontalShipIfOutOfBound() {
        Ship shipToValidate = mock(Ship.class);
        when(shipToValidate.getPosition()).thenReturn(new Point(8, 3));
        when(shipToValidate.isHorizontal()).thenReturn(true);
        when(shipToValidate.getType()).thenReturn(ShipType.BATTLESHIP);
        PlayersBoard boardToValidate = mock(PlayersBoard.class);

        boolean result = validator.isValid(shipToValidate, boardToValidate);

        assertFalse(result);
    }

    @Test
    public void shouldNotValidateVerticalShipIfOutOfBound() {
        Ship shipToValidate = mock(Ship.class);
        when(shipToValidate.getPosition()).thenReturn(new Point(5, 9));
        when(shipToValidate.isHorizontal()).thenReturn(false);
        when(shipToValidate.getType()).thenReturn(ShipType.CRUISER);
        PlayersBoard boardToValidate = mock(PlayersBoard.class);

        boolean result = validator.isValid(shipToValidate, boardToValidate);

        assertFalse(result);
    }

    @Test
    public void shouldValidateProperlyPlacedShip() {
        Ship shipToValidate = mock(Ship.class);
        when(shipToValidate.getPosition()).thenReturn(new Point(4, 3));
        when(shipToValidate.isHorizontal()).thenReturn(true);
        when(shipToValidate.getType()).thenReturn(ShipType.DESTROYER);
        PlayersBoard boardToValidate = mock(PlayersBoard.class);

        boolean result = validator.isValid(shipToValidate, boardToValidate);

        assertTrue(result);
    }

}