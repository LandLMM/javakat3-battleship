package pl.sdacademy.javakato3.battleship.player;

import pl.sdacademy.javakato3.battleship.model.Ship;
import pl.sdacademy.javakato3.battleship.model.ShipType;
import pl.sdacademy.javakato3.battleship.ui.UserInterface;

import java.awt.*;

public class DefaultPlayer implements Player {

    private UserInterface ui;

    public DefaultPlayer(UserInterface ui) {
        this.ui = ui;
    }

    @Override
    public Ship getNextShip(ShipType type) {
        return ui.askUserForNewShip(type);
    }


    @Override
    public Point getNextShot() {
        return ui.askUserForShoot();
    }

    @Override
    public void sendMessage(String message) {
        ui.notifyUser(message);
    }
}
