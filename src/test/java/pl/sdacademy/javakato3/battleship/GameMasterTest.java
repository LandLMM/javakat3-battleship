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

    @Mock
    private Player humanPlayer;

    @Mock
    private Player computerPlayer;

    @Mock
    private GameBoard gameBoard;

    @Mock
    private PlayersBoard humanPlayerBoard;

    @Mock
    private PlayersBoard computerPlayerBoard;

    @Mock
    private Validator shipPositionValidator;
    private List<ShipType> shipTypesToBePlaced;
    private GameMaster gameMaster;

    //Builder Design
    @Before
    public void init() {
        shipTypesToBePlaced = new ArrayList<>();
        gameMaster = new GameMaster(humanPlayer,
                computerPlayer,
                gameBoard,
                shipPositionValidator,
                shipTypesToBePlaced);
        when(gameBoard.getHumanPlayerBoard()).thenReturn(humanPlayerBoard);
        when(gameBoard.getOtherPlayerBoard()).thenReturn(computerPlayerBoard);

    }

    @Test
    public void shouldAskHumanPlayerForShip() {
        shipTypesToBePlaced.add(ShipType.BATTLESHIP);
        when(shipPositionValidator.isValid(any(), any())).thenReturn(true); //no matter what parameters we provide, they will always be true any(),any()
        gameMaster.placeShips();
        verify(humanPlayer, times(1)).getNextShip(eq(ShipType.BATTLESHIP));
    }

    @Test
    public void shouldAskComputerPlayerForShip() {
        shipTypesToBePlaced.add(ShipType.SUBMARINE);
        when(shipPositionValidator.isValid(any(), any())).thenReturn(true);
        gameMaster.placeShips();
        verify(computerPlayer, times(1)).getNextShip(ShipType.SUBMARINE);
    }

    @Test
    public void shouldAskPlayerForShipUntilCorrectPostionIsProvided() {
        shipTypesToBePlaced.add(ShipType.DESTROYER);
        when(shipPositionValidator.isValid(any(), any())).thenReturn(false, false, true, false, false, true);
        gameMaster.placeShips();
        verify(humanPlayer, times(3)).getNextShip(eq(ShipType.DESTROYER));
        verify(computerPlayer, times(3)).getNextShip(eq(ShipType.DESTROYER));
    }

    @Test
    public void shouldPlaceCorrectlyPositionedHumanPlayersShipOnTheirBoard() {
        shipTypesToBePlaced.add(ShipType.BATTLESHIP);
        Ship playerShip = mock(Ship.class);
        when(humanPlayer.getNextShip(any())).thenReturn(playerShip);
        when(shipPositionValidator.isValid(any(), any())).thenReturn(true);
        gameMaster.placeShips();
        verify(humanPlayerBoard, times(1)).addShip(eq(playerShip));

    }

    @Test
    public void shouldPlaceCorrectlyPositionedComputerPlayersShipOnTheirBoard() {
        shipTypesToBePlaced.add(ShipType.SUBMARINE);
        Ship computerShip = mock(Ship.class);
        when(computerPlayer.getNextShip(any())).thenReturn(computerShip);
        when(shipPositionValidator.isValid(any(), any())).thenReturn(true);
        gameMaster.placeShips();
        verify(computerPlayerBoard, times(1)).addShip(eq(computerShip));
    }

    @Test
    public void shouldSetSeaMapElementsForHumanPlayersShip() {
        shipTypesToBePlaced.add(ShipType.CRUISER);
        //user ship definition
        Ship shipToBePlaced = mock(Ship.class);
        when(shipToBePlaced.getPosition()).thenReturn(new Point(2, 1));
        when(shipToBePlaced.isHorizontal()).thenReturn(true);
        when(shipToBePlaced.getType()).thenReturn(ShipType.CRUISER);
        when(humanPlayer.getNextShip(eq(ShipType.CRUISER))).thenReturn(shipToBePlaced);
        //validation will always succeed
        when(shipPositionValidator.isValid(any(), any())).thenReturn(true);

        gameMaster.placeShips();

        verify(humanPlayerBoard).setSeaMapElement(eq(2), eq(1), eq(BoardField.SHIP));
        verify(humanPlayerBoard).setSeaMapElement(eq(3), eq(1), eq(BoardField.SHIP));
        verify(humanPlayerBoard).setSeaMapElement(eq(4), eq(1), eq(BoardField.SHIP));
    }
/*

    @Test
    public void shouldSetSeaMapElementsForComputerPlayersShip() {
        fail();
    }

    @Test
    public void shouldAskPlayerForEveryShipTypeDefined() {
        fail();
    }
*/

    @Test
    public void shouldAskFirstPlayerForHisShot() {
        gameMaster.setCurrentPlayer(humanPlayer);

        gameMaster.startTurn();

        verify(humanPlayer).getNextShot();
    }

    @Test
    public void shouldUpdateSeaMapWithHitOnShipHit() {

        gameMaster.setCurrentPlayer(humanPlayer);
        Point playersShot = new Point(2, 4);
        when(humanPlayer.getNextShot()).thenReturn(playersShot);
        when(computerPlayerBoard.getSeaMapElement(eq(playersShot))).thenReturn(BoardField.SHIP);

        gameMaster.startTurn();

        verify(computerPlayerBoard).setSeaMapElement(eq(playersShot), eq(BoardField.SHIP_HIT));

    }

    @Test
    public void shouldUpdateShipStatusAfterHit() {


    }

    @Test
    public void shouldUpdateSeaMapWithMissWhenMissed() {
        gameMaster.setCurrentPlayer(computerPlayer);
        Point playersShot = new Point(1, 5);
        when(computerPlayer.getNextShot()).thenReturn(playersShot);
        when(humanPlayerBoard.getSeaMapElement(eq(playersShot))).thenReturn(BoardField.SHIP);

        gameMaster.startTurn();

        verify(humanPlayerBoard).setSeaMapElement(eq(playersShot), eq(BoardField.SHIP_HIT));
    }

    @Test
    public void shouldNotChangeCurrentUserAfterHit() {
        gameMaster.setCurrentPlayer(humanPlayer);
        Point playersShot = new Point(3, 6);
        when(humanPlayer.getNextShot()).thenReturn(playersShot);
        when(computerPlayerBoard.getSeaMapElement(eq(playersShot))).thenReturn(BoardField.SHIP);

        gameMaster.startTurn();


        Player nextTurnPlayer = gameMaster.getCurrentPlayer();
        assertEquals(humanPlayer, nextTurnPlayer);
    }

    @Test
    public void shouldChangeCurrentUserAfterMissedShot() {
        gameMaster.setCurrentPlayer(computerPlayer);
        Point playersShot = new Point(3, 6);
        when(computerPlayer.getNextShot()).thenReturn(playersShot);
        when(computerPlayerBoard.getSeaMapElement(eq(playersShot))).thenReturn(BoardField.WATER);

        gameMaster.startTurn();


        Player nextTurnPlayer = gameMaster.getCurrentPlayer();
        assertEquals(computerPlayer, nextTurnPlayer);

    }

    @Test
    public void shouldReturnEmptyOptionalWhenBothPlayersHaveShips() {
        when(computerPlayerBoard.getSeaMapElement(anyInt(), anyInt())).thenReturn(BoardField.SHIP);
        when(humanPlayerBoard.getSeaMapElement(anyInt(), anyInt())).thenReturn(BoardField.SHIP);


        Optional<Player> winner = gameMaster.getWinner();

        assertEquals(Optional.empty(), winner);

    }

    @Test
    public void shouldReturnHumanPlayerWhenComputerHasNoShips() {
        gameMaster = spy(gameMaster);

        when(computerPlayerBoard.getSeaMapElement(anyInt(), anyInt())).thenReturn(BoardField.WATER);
        when(humanPlayerBoard.getSeaMapElement(anyInt(), anyInt())).thenReturn(BoardField.SHIP);
        when(gameMaster.isGameStarted()).thenReturn(true);

        Optional<Player> winner = gameMaster.getWinner();

        assertEquals(humanPlayer, winner.get());
    }

    @Test
    public void shouldReturnComputerPlayerWhenHumanHasNoShips() {
        gameMaster = spy(gameMaster);

        when(computerPlayerBoard.getSeaMapElement(anyInt(), anyInt())).thenReturn(BoardField.WATER);
        when(humanPlayerBoard.getSeaMapElement(anyInt(), anyInt())).thenReturn(BoardField.SHIP);
        when(gameMaster.isGameStarted()).thenReturn(true);

        Optional<Player> winner = gameMaster.getWinner();

        assertEquals(humanPlayer, winner.get());

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