package pl.sdacademy.javakato3.battleship.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JavaConsoleDelegate {
    public void printToConsole(String message) {
        System.out.println(message);
    }

    public String readFromConsole() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            return "";
        }
    }
}
