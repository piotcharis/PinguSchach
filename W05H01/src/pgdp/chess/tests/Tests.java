package pgdp.chess.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import pgdp.chess.*;

public class Tests {

    // NICHT TEIL DER AUFGABE
    @Test
    public void testGameBoardConstructorAndGetSize() {

        // Überprüft ob der laut Aufgabestellung maximal großer gameBoard richtig
        // erstellt wird und alle Felder mit ' ' initialisiert
        GameBoard gameBoard = new GameBoard(100, 100);
        assertEquals(100, gameBoard.getSizeX());
        assertEquals(100, gameBoard.getSizeY());
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                assertEquals(' ', gameBoard.get(i, j));
            }
        }

        // Überprüft ob der laut Aufgabestellung minimal kleiner gameBoard richtig
        // erstellt wird und alle Felder mit ' ' initialisiert
        gameBoard = new GameBoard(1, 1);
        assertEquals(1, gameBoard.getSizeX());
        assertEquals(1, gameBoard.getSizeY());
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                assertEquals(' ', gameBoard.get(i, j));
            }
        }

        // Normalfall
        gameBoard = new GameBoard(8, 16);
        assertEquals(8, gameBoard.getSizeX());
        assertEquals(16, gameBoard.getSizeY());
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 16; j++) {
                assertEquals(' ', gameBoard.get(i, j));
            }
        }

        // Default gameBoard
        gameBoard = GameBoard.newDefault();
        assertEquals(8, gameBoard.getSizeX());
        assertEquals(16, gameBoard.getSizeY());

    }

    // NICHT TEIL DER AUFGABE
    @Test
    public void testEvaluateWinner() {

        // Alle mögliche Fälle:

        // Default gameBoard
        GameBoard gameBoard = GameBoard.newDefault();

        // Pinguine gewinnen
        gameBoard.set(3, 15, ' ');
        assertEquals(1, gameBoard.evaluateWinner());

        // Feinde gewinnen
        gameBoard.set(3, 15, 'A');
        gameBoard.set(4, 0, ' ');
        assertEquals(2, gameBoard.evaluateWinner());

        // Beide gewinnen
        gameBoard.set(3, 15, ' ');
        assertEquals(-1, gameBoard.evaluateWinner());

        // Keiner gewinnt
        gameBoard.set(3, 15, 'A');
        gameBoard.set(4, 0, 'C');
        assertEquals(0, gameBoard.evaluateWinner());
    }

    // NICHT TEIL DER AUFGABE
    @Test
    public void testSet() {

        GameBoard gameBoard = GameBoard.newDefault();

        // Normalfall
        assertTrue(gameBoard.set(0, 5, 'Z'));
        assertEquals('Z', gameBoard.get(0, 5));

        // Edge Case, dass der Methode ein ungültiger char gegeben wird. Methode soll false zurückgeben
        assertFalse(gameBoard.set(0, 5, '\0'));

        // Edge Case, dass x außerhalb des Boards liegt. Methode soll false zurückgeben.
        assertFalse(gameBoard.set(100, 5, 'Z'));

        // Edge Case, dass y außerhalb des Boards liegt.
        assertFalse(gameBoard.set(0, -16, 'Z'));
    }

    // NICHT TEIL DER AUFGABE
    @Test
    public void testGetTeam() {

        PinguChess chess = new PinguChess();

        // Normalfall
        assertEquals(0, chess.getTeam('C'));
        assertEquals(1, chess.getTeam('A'));

        // Edge Case, char ist kein gültiger piece auf dem Board. Methode soll -1 zurückgeben.
        assertEquals(-1, chess.getTeam('J'));

        // Edge Case, es wird ein space als char gegeben. Methode soll wieder -1 zurückgeben.
        assertEquals(-1, chess.getTeam(' '));

        // Edge Case, es wird ein kleingeschriebener char eingegeben.
        assertEquals(-1, chess.getTeam('a'));

    }

    // 1. Test für die Aufgabe
    @Test
    public void testCheckPath() {

        PinguChess chess = new PinguChess();

        // Normalfall
        assertTrue(chess.checkPath(0, 0, 0, 3));

        // Edge Case: Zielkoordinaten außerhalb des Boards. Methode soll false zurückgeben.
        assertFalse(chess.checkPath(0, 0, 0, 19));

        // Edge Case: Zielkoordinaten sind von eine Figur im gleichen Team besetzt.
        assertFalse(chess.checkPath(0, 0, 1, 0));

        // Edge Case: Path ist von eine andere Figur geblockt.
        assertFalse(chess.checkPath(3, 0, 3, 2));
    }

    // 2. Test für die Aufgabe
    @Test
    public void testCheckJump() {

        PinguChess chess = new PinguChess();

        // Normalfall
        assertTrue(chess.checkJump(0, 0, 0, 3));

        // Edge Case: Zielkoordinaten außerhalb des Boards. Methode soll false zurückgeben.
        assertFalse(chess.checkJump(0, 0, 0, 19));

        // Edge Case: Zielkoordinaten sind von eine Figur im gleichen Team besetzt.
        assertFalse(chess.checkJump(0, 0, 1, 0));

        // Edge Case: Path ist von eine andere Figur geblockt, Methode soll true zurückgeben,
        // weil die Figur drüber springt.
        assertTrue(chess.checkJump(3, 0, 3, 2));
    }

    // 3. Test für die Aufgabe
    @Test
    public void testCheckMove() {

        PinguChess chess = new PinguChess();

        // Normalfall
        assertTrue(chess.checkMove(0, 0, 0, 3));

        // Edge Case: Zielkoordinaten außerhalb des Boards
        assertFalse(chess.checkMove(0, 0, 0, 333));

        // Edge Case: Ungültige Bewegung für die ausgewählte Figur.
        assertFalse(chess.checkMove(2, 0, 2, 10));

        // Edge Case: Kaiserpenguin mit mind. 2 Pinguine in den benachbarten Feldern soll unbegrenzt bewegen können.
        chess.getGameBoard().set(3, 1, ' '); // Entfernen der Figur vor dem Kaiserpenguin, damit das Path offen ist.
        assertTrue(chess.checkMove(3, 0, 3, 14));
    }

    @Test
    public void testGet() {
        GameBoard gameBoard = GameBoard.newDefault();

        assertEquals('Z', gameBoard.get(2, 1));
        assertEquals('Z', gameBoard.get(3, 1));
        assertEquals('Z', gameBoard.get(4, 1));
        assertEquals('Z', gameBoard.get(5, 1));
        assertEquals('Z', gameBoard.get(7, 0));
    }
}
