package pl.sdacademy.javakato3.battleship.validation;

import com.sun.org.apache.xpath.internal.operations.String;
import org.junit.Before;
import org.junit.Test;
import pl.sdacademy.javakato3.battleship.model.BoardField;
import pl.sdacademy.javakato3.battleship.model.PlayersBoard;
import pl.sdacademy.javakato3.battleship.model.Ship;
import pl.sdacademy.javakato3.battleship.model.ShipType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CannotTouchValidatorTest {

    private CannotTouchValidator validator;

    @Before
    public void init() {
        validator = new CannotTouchValidator();
    }

    @Test
    public void shouldValidateWhenNoOtherShips() {
        Ship shipToValidate = mock(Ship.class);
        PlayersBoard boardToValidate = mock(PlayersBoard.class);
        when(boardToValidate.getSeaMapElement(anyInt(), anyInt())).thenReturn(BoardField.WATER);
        when(boardToValidate.getShips()).thenReturn(new ArrayList<>());

        boolean isValid = validator.isValid(shipToValidate, boardToValidate);

        assertTrue(isValid);
    }

    @Test
    public void shouldValidateProperlyPlacedShip() {
        // given
        Ship shipToValidate = mock(Ship.class);
        when(shipToValidate.getPosition()).thenReturn(new Point(1, 3));
        when(shipToValidate.getType()).thenReturn(ShipType.DESTROYER);
        when(shipToValidate.isHorizontal()).thenReturn(Boolean.TRUE);

        Ship existingShip = mock(Ship.class);
        when(existingShip.getPosition()).thenReturn(new Point(0, 1));
        when(existingShip.getType()).thenReturn(ShipType.DESTROYER);
        when(existingShip.isHorizontal()).thenReturn(Boolean.TRUE);

        PlayersBoard boardToValidate = mock(PlayersBoard.class);
        when(boardToValidate.getSeaMapElement(0,1)).thenReturn(BoardField.SHIP);
        when(boardToValidate.getSeaMapElement(1,1)).thenReturn(BoardField.SHIP);
        when(boardToValidate.getShips()).thenReturn(new ArrayList<Ship>() {{add(existingShip);}});

        boolean result = validator.isValid(shipToValidate, boardToValidate);

        assertTrue(result);
    }

    @Test
    public void shouldNotValidateWhenShipsAreTouchingBySides() {
        // given
        Ship shipToValidate = mock(Ship.class);
        when(shipToValidate.getPosition()).thenReturn(new Point(1, 2));
        when(shipToValidate.getType()).thenReturn(ShipType.DESTROYER);
        when(shipToValidate.isHorizontal()).thenReturn(Boolean.FALSE);

        Ship existingShip = mock(Ship.class);
        when(existingShip.getPosition()).thenReturn(new Point(1, 1));
        when(existingShip.getType()).thenReturn(ShipType.DESTROYER);
        when(existingShip.isHorizontal()).thenReturn(Boolean.TRUE);

        PlayersBoard boardToValidate = mock(PlayersBoard.class);
        when(boardToValidate.getSeaMapElement(1,1)).thenReturn(BoardField.SHIP);
        when(boardToValidate.getSeaMapElement(2,1)).thenReturn(BoardField.SHIP);
        when(boardToValidate.getShips()).thenReturn(new ArrayList<Ship>() {{add(existingShip);}});

        boolean result = validator.isValid(shipToValidate, boardToValidate);

        assertFalse(result);
    }

    @Test
    public void shouldNotValidateWhenShipsAreTouchingByCorners() {
        // given
        Ship shipToValidate = mock(Ship.class);
        when(shipToValidate.getPosition()).thenReturn(new Point(3, 0));
        when(shipToValidate.getType()).thenReturn(ShipType.DESTROYER);
        when(shipToValidate.isHorizontal()).thenReturn(Boolean.TRUE);

        Ship existingShip = mock(Ship.class);
        when(existingShip.getPosition()).thenReturn(new Point(1, 1));
        when(existingShip.getType()).thenReturn(ShipType.DESTROYER);
        when(existingShip.isHorizontal()).thenReturn(Boolean.TRUE);

        PlayersBoard boardToValidate = mock(PlayersBoard.class);
        when(boardToValidate.getSeaMapElement(1,1)).thenReturn(BoardField.SHIP);
        when(boardToValidate.getSeaMapElement(2,1)).thenReturn(BoardField.SHIP);
        when(boardToValidate.getShips()).thenReturn(new ArrayList<Ship>() {{add(existingShip);}});

        boolean result = validator.isValid(shipToValidate, boardToValidate);

        assertFalse(result);
    }








}