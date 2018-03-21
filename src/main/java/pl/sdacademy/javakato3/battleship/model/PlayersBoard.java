package pl.sdacademy.javakato3.battleship.model;

import pl.sdacademy.javakato3.battleship.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayersBoard {

    private BoardField[][] seaMap;
    private List<Ship> ships;

    public PlayersBoard() {
        this.seaMap = new BoardField[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                seaMap[x][y] = BoardField.WATER;
            }
        }
        ships = new ArrayList<>();
    }

    public BoardField getSeaMapElement(Point point) {
        return getSeaMapElement(((int) point.getX()), ((int) point.getY()));
    }

    public BoardField getSeaMapElement(int x, int y) {
        if (isInBounds(x) && isInBounds(y)) {
            return seaMap[x][y];
        }
        return BoardField.NONE;
    }

    public void setSeaMapElement(Point point, BoardField newFieldValue) {
        setSeaMapElement(((int) point.getX()), ((int) point.getY()), newFieldValue);
    }

    public void setSeaMapElement(int x, int y, BoardField newFieldValue) {
        if (isInBounds(x) && isInBounds(y)) {
            seaMap[x][y] = newFieldValue;
        }
    }

    private boolean isInBounds(int dimension) {
        return dimension < 10 && dimension >= 0;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }






    /*
		seaMap - tablica tablic BoardField (10x10)
		ships - lista statów
		BoardField getSeaMapElement(int x, int y)
		BoardField getSeaMapElement(Point p)
		void setSeaMapElement(int x, int y, BoardField type)
		void setSeaMapElement(Point p, BoardField type)
		void addShip(Ship s)
		LIst<Ship> getShips()
     */
}
