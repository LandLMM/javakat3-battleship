package pl.sdacademy.javakato3.battleship.validation;

import pl.sdacademy.javakato3.battleship.model.PlayersBoard;
import pl.sdacademy.javakato3.battleship.model.Ship;

public interface Validator {
    boolean isValid(Ship ship, PlayersBoard board);
}
