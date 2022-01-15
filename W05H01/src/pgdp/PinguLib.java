package pgdp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import pgdp.chess.GameBoard;

/**
 * This class provides convenience methods to make the barrier of entry into
 * programming easier.
 * <p>
 * <i>Diese Klasse stellt simple Methoden zur Verfügung um den Einstieg in die
 * Programmierung zu vereinfachen.</i>
 */
public class PinguLib {

    private static InputStream is = System.in;
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

    /**
     * Prints the {@link GameBoard} to the console and breaks the line.
     * <p>
     * <i>Gibt das {@link GameBoard} auf der Konsole aus und beginnt eine neue Teile</i>
     */
    public static void writeGameBoard(GameBoard board) {
        //This is horrible Spagetti Code you don't have to understand.
        //Nothing to see here pls go somewhere else
        //Abandon all hope ye who enter here
        if (board.getSizeX() <= 0 || board.getSizeY() <= 0) {
            throw new IllegalStateException("GameBoard has invalid dimensions");
        }
        var tableColomnSize = new int[board.getSizeX() + 1];
        tableColomnSize[0] = Integer.toString(board.getSizeY()).length();
        for (int i = 1; i <= board.getSizeX(); i++) {
            tableColomnSize[i] = Integer.toString(i).length();
        }
        //build horizontal table border
        var horizontalBorderBuilder = new StringBuilder();
        horizontalBorderBuilder.append('+');
        for (
                int i : tableColomnSize

        ) {
            appendNTimes(horizontalBorderBuilder, '-', i + 2).append('+');
        }
        var horizontalBorder = horizontalBorderBuilder.append(System.lineSeparator()).toString();
        System.out.print(horizontalBorder);
        //build top line
        var line = new StringBuilder(horizontalBorder.length() - 1);
        {
            appendNTimes(line.append('|'), ' ', tableColomnSize[0] + 2).append('|');
            IntStream.range(1, board.getSizeX() + 1).forEachOrdered(i -> {
                var n = Integer.toString(i);
                appendNTimes(line, ' ', tableColomnSize[i] - n.length() + 1).append(n).append(" |");
            });
            System.out.println(line);
        }
        System.out.print(horizontalBorder);
        //print board
        for (int y = 0; y < board.getSizeY(); y++) {
            line.setLength(0);
            line.append('|');
            var label = Integer.toString(y + 1);
            appendNTimes(line, ' ', tableColomnSize[0] - label.length() + 1).append(label).append(" |");
            for (int x = 0; x < board.getSizeX(); x++) {
                char figure = board.get(x, y);
                if (figure == '\0') throw new IllegalStateException("Game Board contains \\0 char");
                appendNTimes(line, ' ', tableColomnSize[x + 1]).append(figure).append(" |");
            }
            System.out.println(line);
            System.out.print(horizontalBorder);
        }
    }

    private static StringBuilder appendNTimes(StringBuilder builder, char c, int n) {
        for (int i = 0; i < n; i++) builder.append(c);
        return builder;
    }

    /**
     * Prints the <code>int</code> to the console and breaks the line.
     * <p>
     * <i>Gibt den <code>int</code>-Wert auf der Konsole aus und beginnt eine neue
     * Zeile.</i>
     */
    public static void write(int output) {
        write(String.valueOf(output));
    }

    /**
     * Prints the {@link String} to the console and breaks the line.
     * <p>
     * <i>Gibt den {@link String} auf der Konsole aus und beginnt eine neue
     * Zeile.</i>
     */
    public static void write(String output) {
        System.out.println(output);
    }

    /**
     * Prints the <code>double</code> to the console and breaks the line.
     * <p>
     * <i>Gibt den <code>double</code>-Wert auf der Konsole aus und beginnt eine
     * neue Zeile.</i>
     */
    public static void write(double output) {
        write(String.valueOf(output));
    }

    /**
     * Identical to {@link #write(int)}.
     * <p>
     * <i>Identisch zu {@link #write(int)}</i>
     */
    public static void writeLineConsole(int output) {
        writeLineConsole(String.valueOf(output));
    }

    /**
     * Identical to {@link #write(String)}.
     * <p>
     * <i>Identisch zu {@link #write(String)}</i>
     */
    public static void writeLineConsole(String output) {
        System.out.println(output);
    }

    /**
     * Identical to {@link #write(double)}.
     * <p>
     * <i>Identisch zu {@link #write(double)}</i>
     */
    public static void writeLineConsole(double output) {
        writeLineConsole(String.valueOf(output));
    }

    /**
     * Prints a line separator in the console.
     * <p>
     * <i>Gibt einen Zeilenumbruch auf der Konsole aus.</i>
     */
    public static void writeLineConsole() {
        writeLineConsole("");
    }

    /**
     * Prints the <code>int</code> to the console. Does not end with a line break.
     * <p>
     * <i>Gibt den <code>int</code>-Wert auf der Konsole aus. Endet nicht mit einem
     * Zeilenumbruch.</i>
     */
    public static void writeConsole(int output) {
        writeConsole(String.valueOf(output));
    }

    /**
     * Prints the {@link String} to the console. Does not end with a line break.
     * <p>
     * <i>Gibt den {@link String} auf der Konsole aus. Endet nicht mit einem
     * Zeilenumbruch.</i>
     */
    public static void writeConsole(String output) {
        System.out.print(output);
    }

    /**
     * Prints the <code>double</code> to the console. Does not end with a line
     * break.
     * <p>
     * <i>Gibt den <code>double</code>-Wert auf der Konsole aus. Endet nicht mit
     * einem Zeilenumbruch.</i>
     */
    public static void writeConsole(double output) {
        writeConsole(String.valueOf(output));
    }

    /**
     * Tries to read an <code>int</code> from the console, and retires if the input
     * was not a valid integer. It prompts the user by printing the given
     * <code>text</code> with a line break to the console.
     * <p>
     * <i>Versucht einen <code>int</code>-Wert von der Konsole einzulesen, und
     * wiederholt die Anfrage solange es nicht nicht um eine ganze Zahl handelt. Der
     * oder die Benutzerin wird durch die Ausgabe des übergebenen
     * <code>text</code>es zur Eingabe aufgefordert (mit Zeilenumbruch).</i>
     *
     * @see Integer#parseInt(String)
     */
    public static int readInt(String text) {
        Integer x = null;
        do {
            String s = readString(text);
            if (s == null) {
                throw new IllegalStateException("Es ist keine Eingabe (mehr) verfügbar.");
            }
            try {
                x = Integer.parseInt(s.trim());
            } catch (@SuppressWarnings("unused") NumberFormatException e) {
                // try again
            }
        } while (x == null);
        return x;
    }

    /**
     * Reads a {@link String} from the console, and prompts the user by printing the
     * given <code>text</code> with a line break to the console.
     * <p>
     *
     * <i>Liest einen {@link String} von der Konsole ein, und fordert den oder die
     * Benutzerin durch die Ausgabe des übergebenen <code>text</code>es zur Eingabe
     * auf (mit Zeilenumbruch).</i>
     *
     * @param text the text to display on the console before reading an input.
     * @return the input string or <code>null</code>, if no input is available
     * (should normally not happen)
     */
    public static String readString(String text) {
        // Exchange the reader in case System.in has changed.
        // This is necessary for testing, as for every test input, System.in is
        // changed.
        if (System.in != is) {
            is = System.in;
            bufferedReader = new BufferedReader(new InputStreamReader(is));
        }
        try {
            System.out.println(text);
            return bufferedReader.readLine();
        } catch (IOException e) {
            // We "hide" the exception in the method signature by rethrowing an
            // unchecked
            // exception
            throw new UncheckedIOException("Konnte Eingabe nicht lesen.", e);
        }
    }

}
