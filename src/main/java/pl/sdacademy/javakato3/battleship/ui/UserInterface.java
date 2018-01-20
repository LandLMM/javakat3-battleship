package pl.sdacademy.javakato3.battleship.ui;

import pl.sdacademy.javakato3.battleship.model.GameBoard;
import pl.sdacademy.javakato3.battleship.model.Ship;
import pl.sdacademy.javakato3.battleship.model.ShipType;

import java.awt.*;

public interface UserInterface {
    void printMaps(GameBoard gameBoard);
    void notifyUser(String message);
    Point askUserForShot();
    Ship askUserForNewShip(ShipType type);
}
