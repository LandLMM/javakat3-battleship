package pl.sdacademy.javakato3.battleship.ui;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.sdacademy.javakato3.battleship.model.Ship;
import pl.sdacademy.javakato3.battleship.model.ShipType;

import java.awt.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleUITest {

    @Mock private JavaConsoleDelegate consoleDelegate;
    @InjectMocks private ConsoleUI ui;

    @Test
    public void shouldDisplayTextMessageSentToTheUser() {
        // given
        String messageToTheUser = "Bardzo sie starales lecz z gry wyleciales";
        // when
        ui.notifyUser(messageToTheUser);
        // then
        verify(consoleDelegate).printToConsole(eq(messageToTheUser));
    }

    @Test
    public void shouldReadShotThatIsUppercaseAndSingleNumber() {
        when(consoleDelegate.readFromConsole()).thenReturn("F2");

        Point userShot = ui.askUserForShot();

        assertEquals(5, (int) userShot.getX());
        assertEquals(1, (int) userShot.getY());
    }

    @Test
    public void shouldReadShotThatIsLowercaseAndSingleNumber() {
        when(consoleDelegate.readFromConsole()).thenReturn("d2");

        Point userShot = ui.askUserForShot();

        assertEquals(3, (int) userShot.getX());
        assertEquals(1, (int) userShot.getY());
    }

    @Test
    public void shouldReadShotThatIsLowercaseAndDoubleNumber() {
        when(consoleDelegate.readFromConsole()).thenReturn("d10");

        Point userShot = ui.askUserForShot();

        assertEquals(3, (int) userShot.getX());
        assertEquals(9, (int) userShot.getY());
    }

    @Test
    public void shouldReadHorizontalBattleshipFromUser() {
        when(consoleDelegate.readFromConsole()).thenReturn("c4", "Y");

        Ship ship = ui.askUserForNewShip(ShipType.BATTLESHIP);

        assertEquals(ShipType.BATTLESHIP, ship.getType());
        assertEquals(Boolean.TRUE, ship.isHorizontal());
        assertEquals(2, (int) ship.getPosition().getX());
        assertEquals(3, (int) ship.getPosition().getY());
    }

    @Test
    public void shouldReadVerticalDestroyerFromUser() {
        when(consoleDelegate.readFromConsole()).thenReturn("c4", "n");

        Ship ship = ui.askUserForNewShip(ShipType.DESTROYER);

        assertEquals(ShipType.DESTROYER, ship.getType());
        assertEquals(Boolean.FALSE, ship.isHorizontal());
        assertEquals(2, (int) ship.getPosition().getX());
        assertEquals(3, (int) ship.getPosition().getY());
    }





}