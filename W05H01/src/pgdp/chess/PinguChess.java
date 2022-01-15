package pgdp.chess;

import static pgdp.PinguLib.*;

public class PinguChess {

    private GameBoard gameBoard;
    private boolean pinguPlaying = true;

    // Konstruktor
    public PinguChess() {
        gameBoard = GameBoard.newDefault();
    }

    // Getter und Setter für den gameBoard
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    // Hilft bei der Bestimmung wer beim Spielen dran ist
    public boolean isPinguPlaying() {
        return pinguPlaying;
    }

    public void setPinguPlaying(boolean playing) {
        pinguPlaying = playing;
    }

    // Getter für das Team
    public int getTeam(char piece) {

        // Pinguine
        if (piece == 'Z' || piece == 'F' || piece == 'K' || piece == 'C') {
            return 0;

            // Feinde
        } else if (piece == 'S' || piece == 'O' || piece == 'E' || piece == 'A') {
            return 1;

            // Keines von beiden
        } else {
            return -1;
        }
    }

    // Richtung der Koordinatendifferenz
    public int getDirection(int dX, int dY) {
        // Richtung nach unten
        if (dX == 0 && dY > 0) {
            return 4;

            // unten links
        } else if (dX < 0 && dY == (-1) * dX) {
            return 5;

            // links
        } else if (dX < 0 && dY == 0) {
            return 6;

            // oben links
        } else if (dX < 0 && dY == dX) {
            return 7;

            // oben
        } else if (dX == 0 && dY < 0) {
            return 0;

            // oben rechts
        } else if (dX == (-1) * dY && dY < 0) {
            return 1;

            // rechts
        } else if (dX > 0 && dY == 0) {
            return 2;

            // unten rechts
        } else if (dX == dY && dY > 0) {
            return 3;

            // ungültige Richtung
        } else {
            return -1;
        }
    }

    // Abstand
    public int getDistance(int dX, int dY) {

        // Keine Bewegung
        if (dX == 0 && dY == 0) {
            return 0;
        } else {
            // Anhand der Richtung wird entsprechend die Distanz zurückgegeben
            switch (getDirection(dX, dY)) {
                case 4:
                case 3:
                case 5:
                    return dY;
                case 6:
                case 7:
                    return dX * (-1);
                case 0:
                    return dY * (-1);
                case 1:
                case 2:
                    return dX;
                default:
                    return -1;
            }
        }
    }

    // Nachbarn im gleichen Team
    public int getFriendlyNeighbours(int x, int y) {

        // Uberprüft ob die Koordinaten außerhalb des Boards sind
        if (x < 0 || x >= getGameBoard().getSizeX() || y < 0 || y >= getGameBoard().getSizeY()) {
            return -1;
        } else {
            int teammates = 0;

            // Zuerst Ermittlung des Teams und dann alle herumliegenden Felder durchsuchen nach Figuren
            // vom gleichen Team.
            switch (getTeam(getGameBoard().get(x, y))) {
                case 0:
                    if (getTeam(getGameBoard().get(x, y + 1)) == 0) {
                        teammates += 1;
                    }
                    if (getTeam(getGameBoard().get(x + 1, y + 1)) == 0) {
                        teammates += 1;
                    }
                    if (getTeam(getGameBoard().get(x + 1, y - 1)) == 0) {
                        teammates += 1;
                    }
                    if (getTeam(getGameBoard().get(x, y - 1)) == 0) {
                        teammates += 1;
                    }
                    if (getTeam(getGameBoard().get(x - 1, y - 1)) == 0) {
                        teammates += 1;
                    }
                    if (getTeam(getGameBoard().get(x - 1, y)) == 0) {
                        teammates += 1;
                    }
                    if (getTeam(getGameBoard().get(x - 1, y + 1)) == 0) {
                        teammates += 1;
                    }
                    break;

                case 1:

                    if (getTeam(getGameBoard().get(x + 1, y - 1)) == 1) {
                        teammates += 1;
                    }
                    if (getTeam(getGameBoard().get(x, y - 1)) == 1) {
                        teammates += 1;
                    }
                    if (getTeam(getGameBoard().get(x - 1, y - 1)) == 1) {
                        teammates += 1;
                    }

                    if (y < getGameBoard().getSizeY() - 1) {
                        if (getTeam(getGameBoard().get(x, y + 1)) == 1) {
                            teammates += 1;
                        }
                        if (getTeam(getGameBoard().get(x + 1, y + 1)) == 1) {
                            teammates += 1;
                        }
                        if (getTeam(getGameBoard().get(x - 1, y + 1)) == 1) {
                            teammates += 1;
                        }
                    }

                    if (getTeam(getGameBoard().get(x - 1, y)) == 1) {
                        teammates += 1;
                    }
                    break;
            }

            return teammates;

        }
    }

    // Überprüfung des Paths
    public boolean checkPath(int fromX, int fromY, int toX, int toY) {
        int dX = toX - fromX;
        int dY = toY - fromY;

        boolean result = false;

        // Überprüfung ob die Bewegung gültig ist
        if (getDistance(dX, dY) == 0 || getDistance(dX, dY) == -1 || dX > getGameBoard().getSizeX()
                || dY > getGameBoard().getSizeY() || getDirection(dX, dY) == -1) {
            return false;
        } else {
            // Anhand der Richtung wird jedes Feld gecheckt, ob es besetzt ist und ob das
            // Zielfeld vom gleichen Team besetzt ist.
            switch (getDirection(dX, dY)) {
                case 4:
                    if (getTeam(getGameBoard().get(fromX, fromY + getDistance(dX, dY))) == getTeam(
                            getGameBoard().get(fromX, fromY))) {
                        result = false;
                        break;
                    } else {
                        result = true;
                        for (int i = 1; i < getDistance(dX, dY); i++) {
                            if (getGameBoard().get(fromX, fromY + i) == ' ') {
                                result = true;
                            } else {
                                result = false;
                                break;
                            }
                        }
                    }
                    break;
                case 5:
                    if (getTeam(getGameBoard().get(fromX - getDistance(dX, dY), fromY + getDistance(dX, dY))) ==
                            getTeam(
                                    getGameBoard().get(fromX, fromY))) {
                        result = false;
                        break;
                    } else {
                        result = true;
                        for (int i = 1; i < getDistance(dX, dY); i++) {
                            if (getGameBoard().get(fromX - i, fromY + i) == ' ') {
                                result = true;
                            } else {
                                result = false;
                                break;
                            }
                        }
                    }
                    break;
                case 6:
                    if (getTeam(getGameBoard().get(fromX - getDistance(dX, dY), fromY)) == getTeam(
                            getGameBoard().get(fromX, fromY))) {
                        result = false;
                        break;
                    } else {
                        result = true;
                        for (int i = 1; i < getDistance(dX, dY); i++) {

                            if (getGameBoard().get(fromX - i, fromY) == ' ') {
                                result = true;
                            } else {
                                result = false;
                                break;
                            }
                        }
                    }
                    break;
                case 7:
                    if (getTeam(getGameBoard().get(fromX - getDistance(dX, dY), fromY - getDistance(dX, dY))) ==
                            getTeam(
                                    getGameBoard().get(fromX, fromY))) {
                        result = false;
                        break;
                    } else {
                        result = true;
                        for (int i = 1; i < getDistance(dX, dY); i++) {

                            if (getGameBoard().get(fromX - i, fromY - i) == ' ') {
                                result = true;
                            } else {
                                result = false;
                                break;
                            }
                        }
                    }
                    break;
                case 0:
                    if (getTeam(getGameBoard().get(fromX, fromY - getDistance(dX, dY))) == getTeam(
                            getGameBoard().get(fromX, fromY))) {
                        result = false;
                        break;
                    } else {
                        result = true;
                        for (int i = 1; i < getDistance(dX, dY); i++) {

                            if (getGameBoard().get(fromX, fromY - i) == ' ') {
                                result = true;
                            } else {
                                result = false;
                                break;
                            }
                        }
                    }
                    break;
                case 1:
                    if (getTeam(getGameBoard().get(fromX + getDistance(dX, dY), fromY - getDistance(dX, dY))) ==
                            getTeam(
                                    getGameBoard().get(fromX, fromY))) {
                        result = false;
                        break;
                    } else {
                        result = true;
                        for (int i = 1; i < getDistance(dX, dY); i++) {

                            if (getGameBoard().get(fromX + i, fromY - i) == ' ') {
                                result = true;
                            } else {
                                result = false;
                                break;
                            }
                        }
                    }
                    break;
                case 2:
                    if (getTeam(getGameBoard().get(fromX + getDistance(dX, dY), fromY)) == getTeam(
                            getGameBoard().get(fromX, fromY))) {
                        result = false;
                        break;
                    } else {
                        result = true;
                        for (int i = 1; i < getDistance(dX, dY); i++) {

                            if (getGameBoard().get(fromX + i, fromY) == ' ') {
                                result = true;
                            } else {
                                result = false;
                                break;
                            }
                        }
                    }
                    break;
                case 3:
                    if (getTeam(getGameBoard().get(fromX + getDistance(dX, dY), fromY + getDistance(dX, dY))) ==
                            getTeam(
                                    getGameBoard().get(fromX, fromY))) {
                        result = false;
                        break;
                    } else {
                        result = true;
                        for (int i = 1; i < getDistance(dX, dY); i++) {

                            if (getGameBoard().get(fromX + i, fromY + i) == ' ') {
                                result = true;
                            } else {
                                result = false;
                                break;
                            }
                        }
                    }
                    break;
            }
        }
        return result;
    }

    // Ähnlich mit checkPath, aber hier wird nur der Zielfeld überprüft
    public boolean checkJump(int fromX, int fromY, int toX, int toY) {

        int dX = toX - fromX;
        int dY = toY - fromY;

        int team1 = getTeam(getGameBoard().get(toX, toY));
        int team2 = getTeam(getGameBoard().get(fromX, fromY));
        char ziel = getGameBoard().get(toX, toY);

        // gültige Bewegung?
        if ((getDistance(dX, dY) == 0 || getDistance(dX, dY) == -1 || getDirection(dX, dY) == -1
                || dX > getGameBoard().getSizeX() || dY > getGameBoard().getSizeY())) {
            return false;
        } else {
            if (ziel == ' ' || team1 != team2) {
                return true;
            } else {
                return false;
            }
        }
    }

    // Überprüft die Bewegung anhand der Figur
    public boolean checkMove(int fromX, int fromY, int toX, int toY) {

        int dX = toX - fromX;
        int dY = toY - fromY;
        boolean result = false;

        // Pinguine
        if (getTeam(getGameBoard().get(fromX, fromY)) == 0) {
            switch (getGameBoard().get(fromX, fromY)) {
                // Zügelpinguine
                case 'Z':
                    // Für positive Y == max. 3 Felder
                    if (getDirection(dX, dY) == 4) {
                        if (getDistance(dX, dY) <= 3) {
                            if (checkPath(fromX, fromY, toX, toY) == true) {
                                result = true;
                            }
                        }
                        // Sonst max. 1 Feld
                    } else {
                        if (getDistance(dX, dY) == 1) {
                            if (checkPath(fromX, fromY, toX, toY) == true) {
                                result = true;
                            }
                        }
                    }
                    break;
                // Felsenpinguine
                case 'F':
                    // max. 2 Felder
                    if (getDistance(dX, dY) <= 2) {
                        if (checkJump(fromX, fromY, toX, toY) == true) {
                            result = true;
                        }
                    }
                    break;
                // Kaiserpinguin
                case 'K':
                    // unbegrenzt weit wenn es mind. 2 benachbarte Pinguine gibt
                    if (getFriendlyNeighbours(fromX, fromY) >= 2) {
                        if (checkPath(fromX, fromY, toX, toY) == true) {
                            result = true;
                        }
                        // sonst max. 5 Felder
                    } else {
                        if (getDistance(dX, dY) <= 5) {
                            if (checkPath(fromX, fromY, toX, toY) == true) {
                                result = true;
                            }
                        }
                    }
                    break;
                // Pinguin Küken
                case 'C':
                    // max. 1 Feld
                    if (getDistance(dX, dY) == 1) {
                        if (checkPath(fromX, fromY, toX, toY) == true) {
                            result = true;
                        }
                    }
                    break;
            }
            // Feinde
        } else if (getTeam(getGameBoard().get(fromX, fromY)) == 1) {
            switch (getGameBoard().get(fromX, fromY)) {
                // Seeleoparden == Zügelpinguine mit negativen Y
                case 'S':
                    if (getDirection(dX, dY) == 0) {
                        if (getDistance(dX, dY) <= 3) {
                            if (checkPath(fromX, fromY, toX, toY) == true) {
                                result = true;
                            }
                        }
                    } else {
                        if (getDistance(dX, dY) == 1) {
                            if (checkPath(fromX, fromY, toX, toY) == true) {
                                result = true;
                            }
                        }
                    }
                    break;
                // Orcas == Felsenpinguine
                case 'O':
                    if (getDistance(dX, dY) <= 2) {
                        if (checkJump(fromX, fromY, toX, toY) == true) {
                            result = true;
                        }
                    }
                    break;
                // Eisbär == Kaiserpinguin
                case 'E':
                    if (getFriendlyNeighbours(fromX, fromY) >= 2) {
                        if (checkPath(fromX, fromY, toX, toY) == true) {
                            result = true;
                        }
                    } else {
                        if (getDistance(dX, dY) <= 5) {
                            if (checkPath(fromX, fromY, toX, toY) == true) {
                                result = true;
                            }
                        }
                    }
                    break;
                // Seelöwen == Küken
                case 'A':
                    if (getDistance(dX, dY) == 1) {
                        if (checkPath(fromX, fromY, toX, toY) == true) {
                            result = true;
                        }
                    }
                    break;
            }
        }

        return result;
    }

    // Spiel
    public void runTurn() {

        while (true) {
            // Ausgeben des Boards auf die Konsole
            writeGameBoard(getGameBoard());

            // Pinguine spielen
            if (isPinguPlaying() == true) {
                System.out.println("Die Pinguine sind am Zug");
                // Eingabe der Koordinaten mit Korrektur für die interne Verwendung
                int x = readInt("Bitte X Koordinate der Figur eingeben") - 1;
                int y = readInt("Bitte Y Koordinate der Figur eingeben") - 1;

                // Überprüfung ob die gegebenen Koordinaten im Team der Pinguine gehören
                if (getTeam(getGameBoard().get(x, y)) == 0) {
                    System.out.println("Wohin soll die Figur gehen?");
                    // Eingabe der Koordinaten mit Korrektur für die interne Verwendung
                    int toX = readInt("Bitte X Koordinate des Zielfeldes eingeben") - 1;
                    int toY = readInt("Bitte Y Koordinate des Zielfeldes eingeben") - 1;

                    // Überprüfung der Bewegung und dann Ausführung falls sie gültig ist
                    if (checkMove(x, y, toX, toY) == true) {
                        getGameBoard().set(toX, toY, getGameBoard().get(x, y));
                        getGameBoard().set(x, y, ' ');
                        // Jetzt sollen die Feinde spielen
                        setPinguPlaying(false);
                        break;
                    }
                }
            } else {
                System.out.println("Die Feinde der Pinguine sind am Zug");
                // Eingabe der Koordinaten mit Korrektur für die interne Verwendung
                int x = readInt("Bitte X Koordinate der Figur eingeben") - 1;
                int y = readInt("Bitte Y Koordinate der Figur eingeben") - 1;

                // Überprüfung ob die Figur in den gegebenen Koordinaten den Feinden gehört
                if (getTeam(getGameBoard().get(x, y)) == 1) {
                    System.out.println("Wohin soll die Figur gehen?");
                    // Eingabe der Koordinaten mit Korrektur für die interne Verwendung
                    int toX = readInt("Bitte X Koordinate des Zielfeldes eingeben") - 1;
                    int toY = readInt("Bitte Y Koordinate des Zielfeldes eingeben") - 1;

                    // Überprüfung der Bewegung und dann Ausführung falls sie gültig ist
                    if (checkMove(x, y, toX, toY) == true) {
                        getGameBoard().set(toX, toY, getGameBoard().get(x, y));
                        getGameBoard().set(x, y, ' ');
                        // Jetzt sollen wieder die Pinguine spielen
                        setPinguPlaying(true);
                        break;
                    }
                }
            }
        }
    }

    // Spiel
//    public void runTurn() {
//
//        // Ausgeben des Boards auf die Konsole
//        writeGameBoard(getGameBoard());
//
//        if (isPinguPlaying() == true) {
//            System.out.println("Die Pinguine sind am Zug");
//        } else {
//            System.out.println("Die Feinde der Pinguine sind am Zug");
//        }
//
//        while (true) {
//
//
//            // Pinguine spielen
//            if (isPinguPlaying() == true) {
//                // Eingabe der Koordinaten mit Korrektur für die interne Verwendung
//                int x = readInt("Bitte X Koordinate der Figur eingeben") - 1;
//                int y = readInt("Bitte Y Koordinate der Figur eingeben") - 1;
//
//                // Überprüfung ob die gegebenen Koordinaten im Team der Pinguine gehören
//                if (getTeam(getGameBoard().get(x, y)) == 0) {
//                    System.out.println("Wohin soll die Figur gehen?");
//                    // Eingabe der Koordinaten mit Korrektur für die interne Verwendung
//                    int toX = readInt("Bitte X Koordinate des Zielfeldes eingeben") - 1;
//                    int toY = readInt("Bitte Y Koordinate des Zielfeldes eingeben") - 1;
//
//                    // Überprüfung der Bewegung und dann Ausführung falls sie gültig ist
//                    if (checkMove(x, y, toX, toY) == true) {
//                        getGameBoard().set(toX, toY, getGameBoard().get(x, y));
//                        getGameBoard().set(x, y, ' ');
//                        // Jetzt sollen die Feinde spielen
//                        setPinguPlaying(false);
//                        break;
//                    }
//                }
//            } else {
//                // Eingabe der Koordinaten mit Korrektur für die interne Verwendung
//                int x = readInt("Bitte X Koordinate der Figur eingeben") - 1;
//                int y = readInt("Bitte Y Koordinate der Figur eingeben") - 1;
//
//                // Überprüfung ob die Figur in den gegebenen Koordinaten den Feinden gehört
//                if (getTeam(getGameBoard().get(x, y)) == 1) {
//                    System.out.println("Wohin soll die Figur gehen?");
//                    // Eingabe der Koordinaten mit Korrektur für die interne Verwendung
//                    int toX = readInt("Bitte X Koordinate des Zielfeldes eingeben") - 1;
//                    int toY = readInt("Bitte Y Koordinate des Zielfeldes eingeben") - 1;
//
//                    // Überprüfung der Bewegung und dann Ausführung falls sie gültig ist
//                    if (checkMove(x, y, toX, toY) == true) {
//                        getGameBoard().set(toX, toY, getGameBoard().get(x, y));
//                        getGameBoard().set(x, y, ' ');
//                        // Jetzt sollen wieder die Pinguine spielen
//                        setPinguPlaying(true);
//                        break;
//                    }
//                }
//            }
//        }
//    }

    // Spiel
    public void run() {

        while (true) {
            // Check für Gewinner
            if (getGameBoard().evaluateWinner() == 1) {
                System.out.println("Die Pinguine haben gewonnen!");
                break;
            } else if (getGameBoard().evaluateWinner() == 2) {
                System.out.println("Die Feinde der Pinguine haben gewonnen!");
                break;
            } else if (getGameBoard().evaluateWinner() == -1) {
                System.out.println("Beide haben gewonnen?");
                break;
            }
            // Spielen
            runTurn();
        }
    }

    public static void main(String[] args) {
        PinguChess chess = new PinguChess();
        chess.run();
    }
}
