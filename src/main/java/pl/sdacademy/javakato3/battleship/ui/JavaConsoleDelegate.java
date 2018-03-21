package pl.sdacademy.javakato3.battleship.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class JavaConsoleDelegate {
    public void printToConsole(String message) {
        System.out.println(message);
    }

    public String readFromConsole() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            return "";
        }
    }
}
