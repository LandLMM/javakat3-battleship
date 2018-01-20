package pl.sdacademy.javakato3.battleship;

import pl.sdacademy.javakato3.battleship.model.*;
import pl.sdacademy.javakato3.battleship.player.Player;
import pl.sdacademy.javakato3.battleship.validation.Validator;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class GameMaster {
    private final Player humanPlayer;
    private final Player computerPlayer;
    private final GameBoard gameBoard;
    private final Validator shipPositionValidator;
    private final List<ShipType> shipTypesToBePlaced;
    private Player currentPlayer;
    private boolean gameStarted;

    public GameMaster(Player humanPlayer, Player computerPlayer, GameBoard gameBoard, Validator shipPositionValidator, List<ShipType> shipTypesToBePlaced) {
        this.humanPlayer = humanPlayer;
        this.computerPlayer = computerPlayer;
        this.gameBoard = gameBoard;
        this.shipPositionValidator = shipPositionValidator;
        this.shipTypesToBePlaced = shipTypesToBePlaced;
    }

    public void placeShips() {
        for (ShipType shipType : shipTypesToBePlaced) {
            boolean isHumanPlayersShipProperlyPlaced = false;
            while(!isHumanPlayersShipProperlyPlaced) {
                Ship humanPlayersShip = humanPlayer.getNextShip(shipType);
                isHumanPlayersShipProperlyPlaced = shipPositionValidator.isValid(humanPlayersShip, gameBoard.getHumanPlayerBoard());
                if (isHumanPlayersShipProperlyPlaced) {
                    updatePlayersBoardWithShip(humanPlayersShip, gameBoard.getHumanPlayerBoard());
                }
            }
            boolean isComputerPlayersShipProperlyPlaced = false;
            while (!isComputerPlayersShipProperlyPlaced) {
                Ship computerPlayersShip = computerPlayer.getNextShip(shipType);
                isComputerPlayersShipProperlyPlaced = shipPositionValidator.isValid(computerPlayersShip, gameBoard.getOtherPlayerBoard());
                if (isComputerPlayersShipProperlyPlaced) {
                    updatePlayersBoardWithShip(computerPlayersShip, gameBoard.getOtherPlayerBoard());
                }
            }
            gameBoard.updateObservers();
        }
        gameStarted = true;
    }

    private void updatePlayersBoardWithShip(Ship playersShip, PlayersBoard playersBoard) {
        playersBoard.addShip(playersShip);
        Optional<Ship> optionalShip = Optional.ofNullable(playersShip);
        int shipX = optionalShip
                .map(Ship::getPosition)
                .map(Point::getX)
                .map(Double::intValue)
                .orElse(Integer.MIN_VALUE);
        int shipY = optionalShip
                .map(Ship::getPosition)
                .map(Point::getY)
                .map(Double::intValue)
                .orElse(Integer.MIN_VALUE);
        int mastTotalNumber = optionalShip
                .map(Ship::getType)
                .map(ShipType::getSize)
                .orElse(0);
        for (int mastNumber = 0; mastNumber < mastTotalNumber; mastNumber++) {
            if (playersShip.isHorizontal()) {
                playersBoard.setSeaMapElement(shipX + mastNumber, shipY, BoardField.SHIP);
            } else {
                playersBoard.setSeaMapElement(shipX, shipY + mastNumber, BoardField.SHIP);
            }
        }
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void startTurn() {
        Point shot = currentPlayer.getNextShot();
        PlayersBoard opponentsBoard = getOpponentsBoard();
        BoardField currentSeaElement = Optional.ofNullable(opponentsBoard.getSeaMapElement(shot))
                .orElse(BoardField.NONE);
        switch (currentSeaElement) {
            case WATER:
                opponentsBoard.setSeaMapElement(shot, BoardField.MISS);
                if (currentPlayer == humanPlayer) {
                    currentPlayer = computerPlayer;
                } else {
                    currentPlayer = humanPlayer;
                }
                break;
            case SHIP:
                opponentsBoard.setSeaMapElement(shot, BoardField.SHIP_HIT);
                break;
        }
        gameBoard.updateObservers();
    }

    private PlayersBoard getOpponentsBoard() {
        if (currentPlayer == humanPlayer) {
            return gameBoard.getOtherPlayerBoard();
        } else {
            return gameBoard.getHumanPlayerBoard();
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Optional<Player> getWinner() {
        if (!isGameStarted()) {
            return Optional.empty();
        }
        if (!thereAreShipsLeftOnBoard(gameBoard.getOtherPlayerBoard())) {
            return Optional.of(humanPlayer);
        }
        if (!thereAreShipsLeftOnBoard(gameBoard.getHumanPlayerBoard())) {
            return Optional.of(computerPlayer);
        }
        return Optional.empty();
    }

    private boolean thereAreShipsLeftOnBoard(PlayersBoard board) {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (board.getSeaMapElement(x, y) == BoardField.SHIP) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }
}
