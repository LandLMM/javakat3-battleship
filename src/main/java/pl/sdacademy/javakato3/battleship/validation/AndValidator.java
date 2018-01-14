package pl.sdacademy.javakato3.battleship.validation;

import pl.sdacademy.javakato3.battleship.model.PlayersBoard;
import pl.sdacademy.javakato3.battleship.model.Ship;

public class AndValidator implements Validator {

    private Validator[] subValidators;

    public AndValidator(Validator... subValidators) {
        this.subValidators = subValidators;
    }

    @Override
    public boolean isValid(Ship ship, PlayersBoard board) {
        for (Validator subValidator : subValidators) {
            if (!subValidator.isValid(ship, board)) {
                return false;
            }
        }
        return true;
    }
}
