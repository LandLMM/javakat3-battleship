package pl.sdacademy.javakato3.battleship;

import pl.sdacademy.javakato3.battleship.model.GameBoard;
import pl.sdacademy.javakato3.battleship.model.PlayersBoard;
import pl.sdacademy.javakato3.battleship.model.ShipType;
import pl.sdacademy.javakato3.battleship.player.DefaultPlayer;
import pl.sdacademy.javakato3.battleship.player.Player;
import pl.sdacademy.javakato3.battleship.ui.ConsoleUI;
import pl.sdacademy.javakato3.battleship.ui.RandomComputerUI;
import pl.sdacademy.javakato3.battleship.validation.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConsoleUI humanUI = new ConsoleUI();
        Player humanPlayer = new DefaultPlayer(humanUI);

        RandomComputerUI randomComputerUI = new RandomComputerUI();
        Player computerPlayer = new DefaultPlayer(randomComputerUI);

        PlayersBoard humanPlayersBoard = new PlayersBoard();
        PlayersBoard computerPlayerBoard = new PlayersBoard();


        GameBoard gameBoard = new GameBoard(humanPlayersBoard, computerPlayerBoard);
        gameBoard.registerUI(humanUI);
        gameBoard.registerUI(randomComputerUI);

        List<ShipType> shipTypes = new ArrayList<>();
        shipTypes.add(ShipType.BATTLESHIP);
        shipTypes.add(ShipType.DESTROYER);

        Validator shipPositionValidator = new AndValidator(
                new CannotTouchValidator(),
                new ShipOutOfBoundValidator(),
                new StartOnAMapValidator()
        );


        GameMaster gameMaster = new GameMaster(
                humanPlayer,
                computerPlayer,
                gameBoard,
                shipPositionValidator,
                shipTypes
        );
        gameMaster.setCurrentPlayer(humanPlayer);

        new GameMasterThread(gameMaster).start();
    }

}
