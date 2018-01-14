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
        boolean validationSucceeded = true;
        for (Validator subValidator : subValidators) {
            validationSucceeded &= subValidator.isValid(ship, board);
        }
        return validationSucceeded;
    }
}
