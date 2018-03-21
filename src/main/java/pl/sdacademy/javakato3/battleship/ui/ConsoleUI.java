package pl.sdacademy.javakato3.battleship.ui;

import pl.sdacademy.javakato3.battleship.model.*;

import java.awt.*;
import java.io.Console;

public class ConsoleUI implements UserInterface {

    private JavaConsoleDelegate consoleDelegate = new JavaConsoleDelegate();


    @Override
    public void printMaps(GameBoard gameBoard) {
        consoleDelegate.printToConsole("     YOU         OPPONENT");
        consoleDelegate.printToConsole("");
        consoleDelegate.printToConsole("  ABCDEFGHIJ    ABCDEFGHIJ");
        for (int y = 0; y < 10; y++) {
            String humanLine = getLine(y, gameBoard.getHumanPlayerBoard());
            String otherLine = getLine(y, gameBoard.getOtherPlayerBoard()).replace("O", " ");
            consoleDelegate.printToConsole(humanLine + "  " + otherLine);
        }
    }

    private String getLine(int y, PlayersBoard board) {
        StringBuilder builder = new StringBuilder();
        if (y + 1 < 10) {
            builder.append(" ");
        }
        builder.append(y + 1);
        for (int x = 0; x < 10; x++) {
            Point address = new Point(x, y);
            BoardField mapElement = board.getSeaMapElement(address);
            builder.append(decodeElement(mapElement));
        }
        return builder.toString();
    }

    private String decodeElement(BoardField mapElement) {
        switch (mapElement) {
            case SHIP:
                return "O";
            case SHIP_HIT:
                return "X";
            case MISS:
                return "*";
            default:
                return " ";
        }
    }

    @Override
    public void notifyUser(String message) {
        consoleDelegate.printToConsole(message);

    }

    @Override
    public Point askUserForShoot() {
        return getPointFromUser();
    }

    @Override
    public Ship askUserForNewShip(ShipType type) {
        consoleDelegate.printToConsole("Provide the location of " + type + " ship:");
        Point shipLocation = getPointFromUser();
        consoleDelegate.printToConsole("Is ship horizontal (Y/N): ");
        String shipOrientation = consoleDelegate.readFromConsole();
        boolean isShipHorizontal = shipOrientation.toUpperCase().equals("Y");
        return new Ship(type, isShipHorizontal, shipLocation);
    }

    private Point getPointFromUser() {
        String userInput = consoleDelegate.readFromConsole();
        if (userInput.length() < 2 || userInput.length() > 3) {
            return new Point(-1, -1);

        }
        userInput = userInput.toUpperCase();
        int x = userInput.charAt(0) - 'A';
        String userYSelection = userInput.substring(1);
        int y;
        try {
            y = Integer.parseInt(userYSelection) - 1;
        } catch (NumberFormatException e) {
            y = -1;
        }
        return new Point(x, y);
    }
}
