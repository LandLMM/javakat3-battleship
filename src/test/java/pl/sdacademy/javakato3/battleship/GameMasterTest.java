package pl.sdacademy.javakato3.battleship;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.sdacademy.javakato3.battleship.model.*;
import pl.sdacademy.javakato3.battleship.player.Player;
import pl.sdacademy.javakato3.battleship.validation.Validator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameMasterTest {

    @Mock private Player humanPlayer;
    @Mock private Player computerPlayer;
    @Mock private GameBoard gameBoard;
    @Mock private Validator shipPositionValidator;
    @Mock private PlayersBoard humanPlayersBoard;
    @Mock private PlayersBoard computerPlayersBoard;
    private List<ShipType> shipTypesToBePlaced;
    private GameMaster gameMaster;

    @Before
    public void init() {
        shipTypesToBePlaced = new ArrayList<>();
        gameMaster = new GameMaster(
                humanPlayer,
                computerPlayer,
                gameBoard,
                shipPositionValidator,
                shipTypesToBePlaced);
        when(gameBoard.getHumanPlayerBoard()).thenReturn(humanPlayersBoard);
        when(gameBoard.getOtherPlayerBoard()).thenReturn(computerPlayersBoard);
    }

    @Test
    public void shouldAskHumanPlayerForShip() {
        shipTypesToBePlaced.add(ShipType.BATTLESHIP);
        when(shipPositionValidator.isValid(any(), any())).thenReturn(true);

        gameMaster.placeShips();

        verify(humanPlayer, times(1)).getNextShip(eq(ShipType.BATTLESHIP));
    }

    @Test
    public void shouldAskComputerPlayerForShip() {
        shipTypesToBePlaced.add(ShipType.SUBMARINE);
        when(shipPositionValidator.isValid(any(), any())).thenReturn(true);

        gameMaster.placeShips();

        verify(computerPlayer, times(1)).getNextShip(eq(ShipType.SUBMARINE));
    }

    @Test
    public void shouldAskPlayerForShipUntilCorrectPositionIsProvided() {
        shipTypesToBePlaced.add(ShipType.DESTROYER);
        when(shipPositionValidator.isValid(any(), any())).thenReturn(false, false, true, false, false, true);

        gameMaster.placeShips();

        verify(humanPlayer, times(3)).getNextShip(eq(ShipType.DESTROYER));
        verify(computerPlayer, times(3)).getNextShip(eq(ShipType.DESTROYER));
    }

    @Test
    public void shouldPlaceCorrectlyPositionedHumanPlayersShipOnTheirsBoard() {
        shipTypesToBePlaced.add(ShipType.BATTLESHIP);
        Ship playersShip = mock(Ship.class);
        when(humanPlayer.getNextShip(any())).thenReturn(playersShip);
        when(shipPositionValidator.isValid(any(), any())).thenReturn(true);

        gameMaster.placeShips();

        verify(humanPlayersBoard, times(1)).addShip(eq(playersShip));
    }

    @Test
    public void shouldPlaceCorrectlyPositionedComputerPlayersShipOnTheirsBoard() {
        shipTypesToBePlaced.add(ShipType.BATTLESHIP);
        Ship playersShip = mock(Ship.class);
        when(computerPlayer.getNextShip(any())).thenReturn(playersShip);
        when(shipPositionValidator.isValid(any(), any())).thenReturn(true);

        gameMaster.placeShips();

        verify(computerPlayersBoard, times(1)).addShip(eq(playersShip));
    }

    @Test
    public void shouldSetSeaMapElementsForHumanPlayersShip() {
        shipTypesToBePlaced.add(ShipType.CRUISER);
        // user ship definition
        Ship shipToBePlaced = mock(Ship.class);
        when(shipToBePlaced.getPosition()).thenReturn(new Point(2, 1));
        when(shipToBePlaced.isHorizontal()).thenReturn(true);
        when(shipToBePlaced.getType()).thenReturn(ShipType.CRUISER);
        when(humanPlayer.getNextShip(eq(ShipType.CRUISER))).thenReturn(shipToBePlaced);
        // validation will always succeed
        when(shipPositionValidator.isValid(any(), any())).thenReturn(true);

        gameMaster.placeShips();

        verify(humanPlayersBoard).setSeaMapElement(eq(2), eq(1), eq(BoardField.SHIP));
        verify(humanPlayersBoard).setSeaMapElement(eq(3), eq(1), eq(BoardField.SHIP));
        verify(humanPlayersBoard).setSeaMapElement(eq(4), eq(1), eq(BoardField.SHIP));
    }

    @Test
    public void shouldSetSeaMapElementsForComputerPlayersShip() {
//        fail();
    }

    @Test
    public void shouldAskPlayerForEveryShipTypeDefined() {
//        fail();
    }

    @Test
    public void shouldAskFirstPlayerForTheirShot() {
        gameMaster.setCurrentPlayer(humanPlayer);

        gameMaster.startTurn();

        verify(humanPlayer).getNextShot();
    }

    @Test
    public void shouldUpdateSeaMapWithShipHitOnShipHit() {
        gameMaster.setCurrentPlayer(humanPlayer);
        Point playersShot = new Point(2, 4);
        when(humanPlayer.getNextShot()).thenReturn(playersShot);
        when(computerPlayersBoard.getSeaMapElement(eq(playersShot))).thenReturn(BoardField.SHIP);

        gameMaster.startTurn();

        verify(computerPlayersBoard).setSeaMapElement(eq(playersShot), eq(BoardField.SHIP_HIT));
    }

    @Test
    public void shouldUpdateShipStatusAfterHit() {

    }

    @Test
    public void shouldUpdateSeaMapWithMissWhenMissed() {
        gameMaster.setCurrentPlayer(computerPlayer);
        Point playersShot = new Point(2, 4);
        when(computerPlayer.getNextShot()).thenReturn(playersShot);
        when(humanPlayersBoard.getSeaMapElement(eq(playersShot))).thenReturn(BoardField.WATER);

        gameMaster.startTurn();

        verify(humanPlayersBoard).setSeaMapElement(eq(playersShot), eq(BoardField.MISS));
    }

    @Test
    public void shouldNotChangeCurrentUserAfterHit() {
        gameMaster.setCurrentPlayer(humanPlayer);
        Point playersShot = new Point(3, 6);
        when(humanPlayer.getNextShot()).thenReturn(playersShot);
        when(computerPlayersBoard.getSeaMapElement(eq(playersShot))).thenReturn(BoardField.SHIP);

        gameMaster.startTurn();

        Player nextTurnPlayer = gameMaster.getCurrentPlayer();
        assertEquals(humanPlayer, nextTurnPlayer);
    }

    @Test
    public void shouldChangeCurrentUserAfterMissedShot() {
        gameMaster.setCurrentPlayer(humanPlayer);
        Point playersShot = new Point(3, 6);
        when(humanPlayer.getNextShot()).thenReturn(playersShot);
        when(computerPlayersBoard.getSeaMapElement(eq(playersShot))).thenReturn(BoardField.WATER);

        gameMaster.startTurn();

        Player nextTurnPlayer = gameMaster.getCurrentPlayer();
        assertEquals(computerPlayer, nextTurnPlayer);
    }

    @Test
    public void shouldReturnEmptyOptionalWhenBothPlayersHaveShips() {
        when(computerPlayersBoard.getSeaMapElement(anyInt(), anyInt())).thenReturn(BoardField.SHIP);
        when(humanPlayersBoard.getSeaMapElement(anyInt(), anyInt())).thenReturn(BoardField.SHIP);

        Optional<Player> winner = gameMaster.getWinner();

        assertEquals(Optional.empty(), winner);
    }

    @Test
    public void shouldReturnHumanPlayerWhenComputerHasNoShips() {
        gameMaster = spy(gameMaster);
        when(computerPlayersBoard.getSeaMapElement(anyInt(), anyInt())).thenReturn(BoardField.WATER);
        when(humanPlayersBoard.getSeaMapElement(anyInt(), anyInt())).thenReturn(BoardField.SHIP);
        when(gameMaster.isGameStarted()).thenReturn(true);

        Optional<Player> winner = gameMaster.getWinner();

        assertEquals(humanPlayer, winner.get());
    }

    @Test
    public void shouldReturnComputerPlayerWhenHumanHasNoShips() {
        gameMaster = spy(gameMaster);
        when(computerPlayersBoard.getSeaMapElement(anyInt(), anyInt())).thenReturn(BoardField.SHIP);
        when(humanPlayersBoard.getSeaMapElement(anyInt(), anyInt())).thenReturn(BoardField.WATER);
        when(gameMaster.isGameStarted()).thenReturn(true);

        Optional<Player> winner = gameMaster.getWinner();

        assertEquals(computerPlayer, winner.get());
    }

    @Test
    public void shouldReturnEmptyOptionalWhenGameHasNotStarted() {
        //given
        //when
        Optional<Player> winner = gameMaster.getWinner();
        //then
        assertEquals(Optional.empty(), winner);
    }








}