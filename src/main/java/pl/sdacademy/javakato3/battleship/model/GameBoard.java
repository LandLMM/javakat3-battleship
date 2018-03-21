package pl.sdacademy.javakato3.battleship.model;

import pl.sdacademy.javakato3.battleship.ui.UserInterface;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private PlayersBoard humanPlayerBoard;
    private PlayersBoard otherPlayerBoard;
    private List<UserInterface> observers;

    public GameBoard(PlayersBoard humanPlayerBoard, PlayersBoard otherPlayerBoard) {
        this.humanPlayerBoard = humanPlayerBoard;
        this.otherPlayerBoard = otherPlayerBoard;
        observers = new ArrayList<>();
    }

    public void registerUI(UserInterface ui) {
        observers.add(ui);
    }

    public void unregisterUI(UserInterface ui) {
        observers.remove(ui);
    }

    public void updateObservers() {
        for (UserInterface observer : observers) {
            observer.printMaps(this);
        }
    }



    /*    public GameBoard() {
        this.humanPlayerBoard = new PlayersBoard();
        this.otherPlayerBoard = new PlayersBoard();
    }*/

    public PlayersBoard getHumanPlayerBoard() {
        return humanPlayerBoard;
    }

    public PlayersBoard getOtherPlayerBoard() {
        return otherPlayerBoard;
    }


}
