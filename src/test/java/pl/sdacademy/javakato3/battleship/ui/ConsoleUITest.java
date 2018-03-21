package pl.sdacademy.javakato3.battleship.ui;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.sdacademy.javakato3.battleship.model.Ship;
import pl.sdacademy.javakato3.battleship.model.ShipType;

import java.awt.*;
import java.io.Console;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class ConsoleUITest {

    @Mock
    private JavaConsoleDelegate consoleDelegate;
    @InjectMocks
    private ConsoleUI ui;

    @Test
    public void shouldDisplayTextMessageSentToUser() {
        //given
        String messageToTheUser = "Bardzo sie starales lecz z gry wyleciales";
        //when
        ui.notifyUser(messageToTheUser);
        //then
        verify(consoleDelegate).printToConsole(messageToTheUser);
    }

    @Test
    public void shouldReadShotThatIsUpperCaseAndSingleNumber() {
        when(consoleDelegate.readFromConsole()).thenReturn("F2");

        Point userShot = ui.askUserForShoot();

        assertEquals(5, (int) userShot.getX());
        assertEquals(1, (int) userShot.getY());

    }

    @Test
    public void shouldReadShotThatIsLowerCaseAndSingleNumber() {
        when(consoleDelegate.readFromConsole()).thenReturn("d2");

        Point userShot = ui.askUserForShoot();

        assertEquals(3, (int) userShot.getX());
        assertEquals(1, (int) userShot.getY());

    }

    @Test
    public void shouldReadShotThatIsLowerCaseAndDoubleNumber() {
        when(consoleDelegate.readFromConsole()).thenReturn("d10");

        Point userShot = ui.askUserForShoot();

        assertEquals(3, (int) userShot.getX());
        assertEquals(9, (int) userShot.getY());

    }

    @Test
    public void shouldReadHorizontalBattleshipFromUser() {
        when(consoleDelegate.readFromConsole()).thenReturn("C4", "Y");

        Ship ship = ui.askUserForNewShip(ShipType.BATTLESHIP);

        assertEquals(ShipType.BATTLESHIP, ship.getType());
        assertEquals(Boolean.TRUE, ship.isHorizontal());
        assertEquals(2, (int) ship.getPosition().getX());
        assertEquals(3, (int) ship.getPosition().getY());

    }


    @Test
    public void shoulReadVerticalDestoyerFromUser() {
        when(consoleDelegate.readFromConsole()).thenReturn("C4", "N");

        Ship ship = ui.askUserForNewShip(ShipType.DESTROYER);

        assertEquals(ShipType.DESTROYER, ship.getType());
        assertEquals(Boolean.FALSE, ship.isHorizontal());
        assertEquals(2, (int) ship.getPosition().getX());
        assertEquals(3, (int) ship.getPosition().getY());

    }

}