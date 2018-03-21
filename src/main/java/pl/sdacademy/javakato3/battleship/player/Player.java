package pl.sdacademy.javakato3.battleship.player;

import pl.sdacademy.javakato3.battleship.model.Ship;
import pl.sdacademy.javakato3.battleship.model.ShipType;

import java.awt.*;

public interface Player {

    Ship getNextShip(ShipType type);

    Point getNextShot();

    void sendMessage(String s);
}
