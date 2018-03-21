package pl.sdacademy.javakato3.battleship.validation;

import pl.sdacademy.javakato3.battleship.model.PlayersBoard;
import pl.sdacademy.javakato3.battleship.model.Ship;

public class StartOnAMapValidator implements Validator {


    @Override
    public boolean isValid(Ship ship, PlayersBoard board) {
        int shipX = (int) ship.getPosition().getX();
        int shipY = (int) ship.getPosition().getY();

        return shipX >= 0 && shipX < 10 && shipY > 0 && shipY < 10;


    }
}
