package pl.sdacademy.javakato3.battleship.model;

public enum ShipType {
    BATTLESHIP(4),
    CRUISER(3),
    DESTROYER(2),
    SUBMARINE(1);

    private final Integer size;

    ShipType(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }

}
