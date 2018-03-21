package pl.sdacademy.javakato3.battleship.ui;

import pl.sdacademy.javakato3.battleship.model.*;

import java.awt.*;
import java.util.Random;

public class RandomComputerUI implements UserInterface {

    private PlayersBoard opponentsBoard;

    @Override
    public void printMaps(GameBoard gameBoard) {
        opponentsBoard = gameBoard.getOtherPlayerBoard();
    }

    @Override
    public void notifyUser(String message) {

    }

    @Override
    public Point askUserForShoot() {

        Point point;
        do {
            Random randomGenerator = new Random();
            int x = randomGenerator.nextInt(10);
            int y = randomGenerator.nextInt(10);
            point = new Point(x, y);
        }
        while
                (opponentsBoard.getSeaMapElement(point) == BoardField.MISS ||
                opponentsBoard.getSeaMapElement(point) == BoardField.SHIP_HIT);
        {

        }
        return point;
    }

    @Override
    public Ship askUserForNewShip(ShipType shipType) {
        Random randomGenerator = new Random();
        int x = randomGenerator.nextInt(10);
        int y = randomGenerator.nextInt(10);
        Point shipLocation = new Point(x, y);
        boolean isHorizontal = randomGenerator.nextBoolean();

        return new Ship(shipType, isHorizontal, shipLocation);

    }
}
