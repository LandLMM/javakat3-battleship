package pl.sdacademy.javakato3.battleship.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class GameBoardTest {

    private GameBoard gameBoard;

    @Before
    public void init() {
        gameBoard = new GameBoard(mock(PlayersBoard.class), mock(PlayersBoard.class));
    }

    @Test
    public void shouldProvideHumanPlayersBoard() {
        PlayersBoard humanPlayerBoard = gameBoard.getHumanPlayerBoard();

        assertNotNull(humanPlayerBoard);
    }

    @Test
    public void shouldProvideOtherPlayersBoard() {
        PlayersBoard otherPlayerBoard = gameBoard.getOtherPlayerBoard();

        assertNotNull(otherPlayerBoard);
    }

    @Test
    public void shouldProvideDifferentPlayersBoard() {
        PlayersBoard humanPlayerBoard = gameBoard.getHumanPlayerBoard();
        PlayersBoard otherPlayerBoard = gameBoard.getOtherPlayerBoard();

        assertNotEquals(humanPlayerBoard, otherPlayerBoard);
    }

}
