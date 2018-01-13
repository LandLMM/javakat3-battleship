package pl.sdacademy.javakato3.battleship.model;

import java.awt.*;

public class Ship {
    private final ShipType type;
    private final boolean horizontal;
    private final Point position;
    private ShipState state;

    public Ship(ShipType type, boolean horizontal, Point position) {
        this.type = type;
        this.horizontal = horizontal;
        this.position = position;
        state = ShipState.OK;
    }

    public ShipType getType() {
        return type;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public Point getPosition() {
        return position;
    }

    public ShipState getState() {
        return state;
    }

    public void setState(ShipState state) {
        this.state = state;
    }
}
