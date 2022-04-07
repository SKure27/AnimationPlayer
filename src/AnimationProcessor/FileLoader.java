package AnimationProcessor;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A collection of methods pertaining to the parsing of information from the
 * input file.
 */
public class FileLoader {

    /**
     * Runs a recursive loop that ensures the file path for the input file is
     * valid.<p>
     * The loop will repeatedly prompt the user for a valid file path. This
     * continues until either a valid file path is entered, or the user chooses
     * to terminate the program via an input to the terminal.
     *
     * @param Filepath Location of the input file.
     * @return A Scanner object reading from the input file.
     */
    static protected Scanner readFile(String Filepath) {
        // Defines a scanner object that reads from the terminal so the user can enter a new filepath.
        Scanner scan = new Scanner(System.in);
        try {
            // Attempts to create a scanner reading from the provided filepath.
            return new Scanner(new File(Filepath));
        } catch (FileNotFoundException e) {
            System.out.print("The File was not found.\nEnter the correct filepath or 'n' to exit the program: ");
            // Reads a new filepath from the user.
            Filepath = scan.nextLine();
            // If the user enters 'n' to exit the program.
            if (Filepath.equalsIgnoreCase("n")) {
                // Exits the program.
                System.exit(0);
            }
            // Runs the method recursively until a valid filepath is found.
            return readFile(Filepath);
        }
    }

    /**
     * Reads the next section of numerical data from the input file.
     *
     * @param scan The {@link java.util.Scanner} object reading from the input
     * file.
     * @return The integer read from the input file.
     */
    static protected int readNum(Scanner scan) {
        scan.useDelimiter("\n");
        // Error Checking
        // Removes any non-integers from read information.
        return Integer.parseInt(scan.next().replaceAll("\\D", ""));
    }

    /**
     * Advances the Scanner to the next section of data.
     *
     * @param scan The {@link java.util.Scanner} object reading from the input
     * file.
     */
    static protected void skipToNum(Scanner scan) {
        scan.skip("\\D*+");
    }

    /**
     * Checks if the the Scanner has read all the information for the current
     * shape.
     * <p>
     * Also checks if the end of the file has been reached.
     *
     * @param scan The {@link java.util.Scanner} object reading from the input
     * file.
     *
     * @return If either of the conditions mentioned are true.
     */
    static protected boolean endOfShape(Scanner scan) {
        scan.useDelimiter("\\S");
        // Checks if the following line is the end of the file.
        if (scan.hasNext("[.|\\s]\\z")) {
            return true;
        }
        // Checks if a blank space is located on the next line.
        return scan.hasNext("\n\r*+\n");
    }

    /**
     * Checks if the Scanner has reached the end of the file.
     *
     * @param scan The {@link java.util.Scanner} object reading from the input
     * file.
     *
     * @return If the end of the file has been reached.
     */
    static protected boolean endOfFile(Scanner scan) {
        // Checks if the following line is the end of the file.
        return scan.hasNext("[.|\\s]\\z");
    }

    /**
     * Reads the next shape/effect type ("Circle", "Rect", "Show", "Jump", etc).
     *
     * @param scan The {@link java.util.Scanner} object reading from the input
     * file.
     *
     * @return The shape/effect type.
     */
    static protected String readNextType(Scanner scan) {
        scan.skip("[^a-zA-Z]*+");
        scan.useDelimiter("\n");
        // Error Checking
        // Removes any whitespace characters to avoid unintended behaviour when checking types.
        return scan.next().replaceAll("\\s", "");
    }

    /**
     * Checks if the information on the following line of the text file is in a
     * format that corresponds to readable data ("label": "data").
     *
     * @param scan The {@link java.util.Scanner} object reading from the input
     * file.
     *
     * @return If the following line of the text file follows the correct
     * format.
     */
    static protected boolean nextInfoExists(Scanner scan) {
        scan.useDelimiter("\n");
        // Checks if the format of the next line is correct.
        return scan.hasNext("\\D++[\\d[, ]*+]++\\s*+");
    }

    /**
     * Reads the type of data the following information represents ("length",
     * "width", "color", etc).
     *
     * @param scan The {@link java.util.Scanner} object reading from the input
     * file.
     *
     * @return The type of information the following data represents.
     */
    static protected String readinfoType(Scanner scan) {
        scan.useDelimiter("[^a-zA-Z]");
        // Reads the type of the next piece of informtion.
        return scan.next();
    }

    /**
     * Reads the next section of data as a color (r,g,b).
     *
     * @param scan The {@link java.util.Scanner} object reading from the input
     * file.
     *
     * @return A {@link java.awt.Color} object corresponding to the values from
     * the input file.
     */
    static protected Color readColor(Scanner scan) {
        scan.useDelimiter(",|\n");
        // Error Checking
        // Removes any non-integer values from the data read.
        return new Color(Integer.parseInt(scan.next().replaceAll("\\D", "")), Integer.parseInt(scan.next().replaceAll("\\D", "")), readNum(scan));
    }

    /**
     * Stores the data being read from the input file in the corresponding
     * {@link Shape}.
     *
     * @param CurrShape The current {@link Shape} being defined by the input
     * file.
     *
     * @param scan The {@link java.util.Scanner} object reading from the input
     * file.
     */
    static protected void defineShape(Shape CurrShape, Scanner scan) {
        // Checks what type of data is contained on the following line.
        // Defines the shape using the information read from the file.
        switch (FileLoader.readinfoType(scan).toLowerCase()) {
            case "x":
                FileLoader.skipToNum(scan);
                CurrShape.x = FileLoader.readNum(scan);
                break;
            case "y":
                FileLoader.skipToNum(scan);
                CurrShape.y = FileLoader.readNum(scan);
                break;
            case "border":
                FileLoader.skipToNum(scan);
                CurrShape.border = FileLoader.readNum(scan);
                break;
            case "color":
                FileLoader.skipToNum(scan);
                CurrShape.color = FileLoader.readColor(scan);
                break;
            case "bordercolor":
                FileLoader.skipToNum(scan);
                CurrShape.borderColor = FileLoader.readColor(scan);
                break;
            case "r":
                FileLoader.skipToNum(scan);
                CurrShape.r = FileLoader.readNum(scan);
                break;
            case "length":
                FileLoader.skipToNum(scan);
                CurrShape.length = FileLoader.readNum(scan);
                break;
            case "width":
                FileLoader.skipToNum(scan);
                CurrShape.width = FileLoader.readNum(scan);
                break;
            case "endx":
                FileLoader.skipToNum(scan);
                CurrShape.endX = FileLoader.readNum(scan);
                break;
            case "endy":
                FileLoader.skipToNum(scan);
                CurrShape.endY = FileLoader.readNum(scan);
                break;
        }
    }

    /**
     * Stores the data being read from the input file in the corresponding
     * {@link Effect}.
     *
     * @param CurrEffect The current {@link Effect} being defined by the input
     * file.
     *
     * @param scan The {@link java.util.Scanner} object reading from the input
     * file.
     */
    static protected void defineEffect(Effect CurrEffect, Scanner scan) {
        // Checks what type of data is contained on the following line.
        // Defines the effect using the information read from the file.
        switch (FileLoader.readinfoType(scan).toLowerCase()) {
            case "start":
                FileLoader.skipToNum(scan);
                CurrEffect.start = FileLoader.readNum(scan);
                break;
            case "x":
                FileLoader.skipToNum(scan);
                CurrEffect.x = FileLoader.readNum(scan);
                break;
            case "y":
                FileLoader.skipToNum(scan);
                CurrEffect.y = FileLoader.readNum(scan);
                break;
            case "color":
                FileLoader.skipToNum(scan);
                CurrEffect.newColor = FileLoader.readColor(scan);
                break;
        }
    }
}
