package pl.sdacademy.javakato3.battleship.validation;

import pl.sdacademy.javakato3.battleship.model.PlayersBoard;
import pl.sdacademy.javakato3.battleship.model.Ship;

public class ShipOutOfBoundsValidator implements Validator {
    @Override
    public boolean isValid(Ship ship, PlayersBoard board) {
        int shipX = (int) ship.getPosition().getX();
        int shipEndX = ship.isHorizontal() ? shipX + ship.getType().getSize() : shipX;
        int shipY = (int) ship.getPosition().getY();
        int shipEndY = ship.isHorizontal() ? shipY : shipY + ship.getType().getSize();

        return shipEndX >=0 && shipEndX < 10 && shipEndY >=0 && shipEndY < 10;
    }
}
