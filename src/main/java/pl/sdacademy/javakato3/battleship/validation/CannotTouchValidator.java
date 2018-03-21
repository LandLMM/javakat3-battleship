package pl.sdacademy.javakato3.battleship.validation;

import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import pl.sdacademy.javakato3.battleship.model.BoardField;
import pl.sdacademy.javakato3.battleship.model.PlayersBoard;
import pl.sdacademy.javakato3.battleship.model.Ship;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CannotTouchValidator implements Validator {


    @Override
    public boolean isValid(Ship ship, PlayersBoard board) {

        if (board.getShips().isEmpty()) {

            return true;
        }

        int shipX = (int) ship.getPosition().getX();
        int shipEndX = ship.isHorizontal() ? shipX + ship.getType().getSize() : shipX;
        int shipY = (int) ship.getPosition().getY();
        int shipEndY = ship.isHorizontal() ? shipX + ship.getType().getSize() : shipX;

        List<BoardField> boardFragment = new ArrayList<>();

        for (int y = shipY - 1; y <= shipY + 1; y++) {
            for (int x = shipX - 1; x <= shipX + 1; x++) {
                BoardField element = board.getSeaMapElement(x, y);
                boardFragment.add(element);
            }
        }
        return !boardFragment.contains(BoardField.SHIP);
    }
}