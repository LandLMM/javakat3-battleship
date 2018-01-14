package pl.sdacademy.javakato3.battleship.validation;

import org.junit.Test;
import pl.sdacademy.javakato3.battleship.model.PlayersBoard;
import pl.sdacademy.javakato3.battleship.model.Ship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AndValidatorTest {

    @Test
    public void shouldValidateWhenNoSubValidators() {
        Ship shipToValidate = mock(Ship.class);
        PlayersBoard boardToValidate = mock(PlayersBoard.class);
        AndValidator validator = new AndValidator();

        boolean result = validator.isValid(shipToValidate, boardToValidate);

        assertTrue(result);
    }

    @Test
    public void shouldValidateWhenAllSubValidatorsValidated() {
        // given
        Ship shipToValidate = mock(Ship.class);
        PlayersBoard boardToValidate = mock(PlayersBoard.class);

        Validator subValidator1 = mock(Validator.class);
        when(subValidator1.isValid(shipToValidate, boardToValidate)).thenReturn(true);

        Validator subValidator2 = mock(Validator.class);
        when(subValidator2.isValid(shipToValidate, boardToValidate)).thenReturn(true);

        Validator validator = new AndValidator(subValidator1, subValidator2);

        // when
        boolean result = validator.isValid(shipToValidate, boardToValidate);

        // then
        assertTrue(result);
    }

    @Test
    public void shouldNotValidateWhenAtLeastOneSubValidatorFail() {
        // given
        Ship shipToValidate = mock(Ship.class);
        PlayersBoard boardToValidate = mock(PlayersBoard.class);

        Validator subValidator1 = mock(Validator.class);
        when(subValidator1.isValid(shipToValidate, boardToValidate)).thenReturn(true);

        Validator subValidator2 = mock(Validator.class);
        when(subValidator2.isValid(shipToValidate, boardToValidate)).thenReturn(true);

        Validator subValidator3 = mock(Validator.class);
        when(subValidator3.isValid(shipToValidate, boardToValidate)).thenReturn(false);

        Validator validator = new AndValidator(subValidator1, subValidator2, subValidator3);

        // when
        boolean result = validator.isValid(shipToValidate, boardToValidate);

        // then
        assertFalse(result);
    }

    @Test
    public void shouldNotValidateWhenAllSubValidatorsFail() {
        // given
        Ship shipToValidate = mock(Ship.class);
        PlayersBoard boardToValidate = mock(PlayersBoard.class);

        Validator subValidator1 = mock(Validator.class);
        when(subValidator1.isValid(shipToValidate, boardToValidate)).thenReturn(false);

        Validator subValidator2 = mock(Validator.class);
        when(subValidator2.isValid(shipToValidate, boardToValidate)).thenReturn(false);

        Validator validator = new AndValidator(subValidator1, subValidator2);

        // when
        boolean result = validator.isValid(shipToValidate, boardToValidate);

        // then
        assertFalse(result);
    }

}