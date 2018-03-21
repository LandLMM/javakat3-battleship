package pl.sdacademy.javakato3.battleship;

import java.util.Optional;

public class GameMasterThread extends Thread {

    private GameMaster gameMaster;

    public GameMasterThread(GameMaster gameMaster) {
        this.gameMaster = gameMaster;
    }


    @Override
    public void run() {
        gameMaster.placeShips();
        while (gameMaster.getWinner().equals(Optional.empty())) {
            gameMaster.startTurn();

        }

        gameMaster.getWinner().get().sendMessage("You won!");
    }
}
