package pl.sdacademy.javakato3.battleship.model;

public class GameBoard {
    private PlayersBoard humanPlayerBoard;
    private PlayersBoard otherPlayerBoard;

    public GameBoard(PlayersBoard humanPlayerBoard, PlayersBoard otherPlayerBoard) {
        this.humanPlayerBoard = humanPlayerBoard;
        this.otherPlayerBoard = otherPlayerBoard;
    }

    public PlayersBoard getHumanPlayerBoard() {
        return humanPlayerBoard;
    }

    public PlayersBoard getOtherPlayerBoard() {
        return otherPlayerBoard;
    }
}
