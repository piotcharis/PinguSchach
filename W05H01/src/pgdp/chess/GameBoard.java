package pgdp.chess;

import java.lang.reflect.Array;
import java.util.Arrays;

public class GameBoard {

    static char[][] gameBoard;
    static int sizeX;
    static int sizeY;

    public GameBoard(int sizeX, int sizeY) {

        GameBoard.sizeX = sizeX;
        GameBoard.sizeY = sizeY;

        // Erstellen eines Arrays von chars mit den gegebenen Längen
        gameBoard = new char[sizeX][sizeY];

        // Initialisierung aller Felder im Board mit ' '
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j] = ' ';
            }
        }
    }

    public static GameBoard newDefault() {

        // Default Board ist 8x16
        sizeX = 8;
        sizeY = 16;

        // Erstellen des Boards mit den Default Werten mit Hilfe des Konstruktors
        GameBoard defaultBoard = new GameBoard(8, 16);

        // Initialisierung der Fälder mit ' '
//        for (int i = 0; i < gameBoard.length; i++) {
//            for (int j = 0; j < gameBoard[i].length; j++) {
//                gameBoard[i][j] = ' ';
//            }
//        }

        // Einsetzen der default Figuren
        defaultBoard.set(0, 0, 'Z');
        defaultBoard.set(1, 0, 'F');
        defaultBoard.set(2, 0, 'F');
        defaultBoard.set(3, 0, 'K');
        defaultBoard.set(4, 0, 'C');
        defaultBoard.set(5, 0, 'F');
        defaultBoard.set(6, 0, 'F');
        defaultBoard.set(7, 0, 'Z');
        defaultBoard.set(2, 1, 'Z');
        defaultBoard.set(3, 1, 'Z');
        defaultBoard.set(4, 1, 'Z');
        defaultBoard.set(5, 1, 'Z');

        defaultBoard.set(0, 15, 'S');
        defaultBoard.set(1, 15, 'O');
        defaultBoard.set(2, 15, 'O');
        defaultBoard.set(3, 15, 'A');
        defaultBoard.set(4, 15, 'E');
        defaultBoard.set(5, 15, 'O');
        defaultBoard.set(6, 15, 'O');
        defaultBoard.set(7, 15, 'S');
        defaultBoard.set(2, 14, 'S');
        defaultBoard.set(3, 14, 'S');
        defaultBoard.set(4, 14, 'S');
        defaultBoard.set(5, 14, 'S');

        return defaultBoard;
    }

    public char get(int x, int y) {

        // Uberprüft ob die x- und y-Werte gültig sind, wenn nicht wird '\0' zurückgegeben
        if (x < 0 || x >= gameBoard.length || y < 0 || y >= gameBoard[x].length) {
            return '\0';
        } else {
            return gameBoard[x][y];
        }
    }

    public boolean set(int x, int y, char piece) {

        // Uberprüft ob die x- und y-Werte gültig sind und ob das piece nicht \0 ist
        if (x < 0 || x >= gameBoard.length || y < 0 || y >= gameBoard[x].length || piece == '\0') {
            return false;
        } else {
            // Einsetzen des piece in den gegebenen Koordinaten
            gameBoard[x][y] = piece;
            return true;
        }
    }

    // Getters für die Gräße in x und y Richtung
    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int evaluateWinner() {

        boolean thereIsC = false;
        boolean thereIsA = false;

        // Jedes Feld des Boards wird durchgesucht nach C und A
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {

                if (get(i, j) == 'C') {
                    thereIsC = true;
                }

                if (get(i, j) == 'A') {
                    thereIsA = true;
                }
            }
        }

        // Keine A == Pinguine gewinnen
        if (thereIsC == true && thereIsA == false) {
            return 1;

            // Keine C == Feinde gewinnen
        } else if (thereIsC == false && thereIsA == true) {
            return 2;

            // Keine C und keine A beide gewinnen
        } else if (thereIsC == false && thereIsA == false) {
            return -1;

            // Beide da == keiner hat gewonnen
        } else
            return 0;
    }
}
