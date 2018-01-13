package pl.sdacademy.javakato3.battleship.model;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayersBoardTest {

    private PlayersBoard board;

    @Before
    public void init() {
        board = new PlayersBoard();
    }

    @Test
    public void shouldReturnWaterAfterInit() {
        BoardField field = board.getSeaMapElement(4, 7);

        assertEquals(BoardField.WATER, field);
    }

    @Test
    public void shouldReturnNoneIfXElementIsTooLow() {
        BoardField field = board.getSeaMapElement(-1, 7);

        assertEquals(BoardField.NONE, field);
    }

    @Test
    public void shouldReturnNoneIfXElementIsTooHigh() {
        BoardField field = board.getSeaMapElement(10, 7);

        assertEquals(BoardField.NONE, field);
    }

    @Test
    public void shouldReturnNoneIfYElementIsTooLow() {
        BoardField field = board.getSeaMapElement(5, -1);

        assertEquals(BoardField.NONE, field);
    }

    @Test
    public void shouldReturnNoneIfYElementIsTooHigh() {
        BoardField field = board.getSeaMapElement(5, 10);

        assertEquals(BoardField.NONE, field);
    }

    @Test
    public void shouldReturnWaterForPointObject() {
        Point point = mock(Point.class);
        when(point.getX()).thenReturn(5d);
        when(point.getY()).thenReturn(7d);

        BoardField field = board.getSeaMapElement(point);

        assertEquals(BoardField.WATER, field);
    }

    @Test
    public void shouldChangeBoardFieldElement() {
        board.setSeaMapElement(3, 8, BoardField.MISS);

        BoardField field = board.getSeaMapElement(3, 8);

        assertEquals(BoardField.MISS, field);
    }

    @Test
    public void shouldNotThrowExceptionWhenElementChangeIsOutOfBounds() {
        board.setSeaMapElement(-1, 10, BoardField.MISS);

        BoardField field = board.getSeaMapElement(-1, 10);

        assertEquals(BoardField.NONE, field);
    }

    @Test
    public void shouldChangeBoardFieldElementForPoint() {
        // given
        Point point = mock(Point.class);
        when(point.getX()).thenReturn(3d);
        when(point.getY()).thenReturn(8d);

        // when
        board.setSeaMapElement(point, BoardField.MISS);

        // then
        BoardField field = board.getSeaMapElement(point);
        assertEquals(BoardField.MISS, field);
    }

    @Test
    public void shouldInitializeShipList() {
        List<Ship> ships = board.getShips();

        assertNotNull(ships);
        assertTrue(ships.isEmpty());
    }

    @Test
    public void shouldAddShipToShipList() {
        Ship ship = mock(Ship.class);

        board.addShip(ship);

        List<Ship> ships = board.getShips();
        assertNotNull(ships);
        assertEquals(1, ships.size());
    }











}