package AnimationProcessor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * This class contains all methods concerning the logic used to process
 * animation data. It also contains the definitions for the swing components
 * used to draw to the screen.
 * <p>
 * Methods related to painting on the screen are located in {@link JComp}.
 */
public class AnimationPlayer {

    /**
     * The {@link javax.swing.JComponent} used to draw onto the window.
     */
    static protected JComp Canvas = new JComp();

    /**
     * Stores the information of each {@link Shape} read from the input file.
     */
    static protected ArrayList<Shape> ShapeList = new ArrayList();

    /**
     * The frame rate of the Animation.
     */
    static protected int Fps = 10;
    /**
     * The current frame of the Animation.
     */
    static protected int CurrFrame = 0;
    /**
     * The maximum number of frames the Animation should run for.
     */
    static protected int Frames = 1000;

    /**
     * The next frame an event will occur.
     */
    private int NextEvent;

    /**
     * Sets up the swing window by defining a JFrame for the program to use.<p>
     * The JFrames Size and Name are defined. The JFrame is also made visible to
     * the user. This method also adds the JComponent to the JFrame.
     */
    AnimationPlayer() {
        // A JFrame object which represents the window the user sees.
        JFrame window = new JFrame("Animation Player");
        // Tells swing that pressing the x button on the window should close the window.
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Sets the windows size.
        window.setSize(400, 300);
        // Sets the spawn location of the window to the center of the screen on a 1920x1080 display.
        window.setLocation(760, 390);
        // Adds the JComponent to the JFrame.
        window.add(Canvas);
        // Makes the window visible to the user.
        window.setVisible(true);
    }

    /**
     * Parses the information found in the input file and stores it in the
     * {@link AnimationPlayer#ShapeList}.
     * <p>
     * After running this method, the program should assume that all data from
     * the input file is stored properly in the
     * {@link AnimationPlayer#ShapeList}.
     *
     * @param Filepath Location of the input file.
     */
    protected void loadAnimationFromFile(String Filepath) {
        // Defines a scanner that reads from the input file.
        Scanner c = FileLoader.readFile(Filepath);

        // Reads the max number of frames from the input file.
        FileLoader.skipToNum(c);
        Frames = FileLoader.readNum(c);

        // Reads the fps from the input file.
        FileLoader.skipToNum(c);
        Fps = FileLoader.readNum(c);

        // Reads the number of shapes defined in the input file.
        FileLoader.skipToNum(c);
        int ShapeNum = FileLoader.readNum(c);

        // Reads each line of the file using the methods in the FileLoader class.
        // Organizes the information into shape object which are then stored in the ShapeList
        // defShapeNum keeps track of the number of defined shapes (index of ShapeList). 
        // effectNum keeps track of the number of defined effects (index of EffectList).
        for (int defShapeNum = 0, effectNum = 0; defShapeNum < ShapeNum; defShapeNum++) {
            // Adds an empty shape object to the ShapeList.
            ShapeList.add(new Shape());
            // Reads the shapes type from the file.
            ShapeList.get(defShapeNum).type = FileLoader.readNextType(c);
            c.skip("[^a-zA-Z]*+");

            // Defines each shape object as well as their respective EffectLists.
            // The process is divided into two phases. The shape defintion phase, and the effect definition phase.
            // readEffect keeps track of the current phase of the process. The program is initially in the shape definition phase.
            // shapeDefined keeps track of whether or not the current shape has finished being defined.
            // effectDefined keeps track of whether or not the current effect has finished being defined.
            for (boolean shapeDefined = false, effectDefined = false, readEffects = false; shapeDefined == false;) {
                // Checks if the next line of the file contains shape or effect data.
                // During the effect definition phase, this also checks whether or not the current effect has finished being defined.
                if (FileLoader.nextInfoExists(c) == true || effectDefined == true) {
                    // Checks if it is currently the shape definition phase.
                    if (readEffects == false) {
                        // Stores the data on the following line in the current shape.
                        FileLoader.defineShape(ShapeList.get(defShapeNum), c);
                    } else {
                        // Checks if the current effect has finished being defined.
                        if (effectDefined) {
                            // Error Checking
                            // Only skips the effect label if it is actually in the file.
                            if (c.hasNext("(?i-:effect)\r*+")) {
                                c.skip("[^a-zA-Z]*+");
                            }
                            // Adds an empty effect to the EffectList.
                            ShapeList.get(defShapeNum).EffectList.add(new Effect());
                            // Reads the effects type from the file.
                            ShapeList.get(defShapeNum).EffectList.get(effectNum).type = FileLoader.readNextType(c);
                            c.skip("[^a-zA-Z]*+");

                            // The new effect has not finished being defined.
                            effectDefined = false;
                        } else {
                            // Stores the data on the following line in the current effect.
                            FileLoader.defineEffect(ShapeList.get(defShapeNum).EffectList.get(effectNum), c);
                        }

                    }
                } // Checks if either the end of the shape or file has been reached.
                else if (FileLoader.endOfShape(c)) {
                    // Checks if the scanner has reached the end of the file.
                    if (FileLoader.endOfFile(c)) {
                        // Error Checking
                        // In the event that the input file indicated 
                        // more shape data was in the file then there actually was.
                        // End the file reading process with the current information gathered.
                        defShapeNum = ShapeNum;
                    } // Error Checking
                    // In the event that the input file indicated 
                    // less shape data was in the file then there actually was.
                    else if (defShapeNum + 1 == ShapeNum) {
                        // Increase the maximum number of shapes.
                        ShapeNum++;
                    }
                    // The end of the shape has been reached.
                    shapeDefined = true;
                    // Resets the effect counter for the next shape.
                    effectNum = 0;
                } // If the end of the shape or file has not been reached, the scanner must have reached effect information. 
                else if (readEffects == false) {
                    // Enters the effect definition phase.
                    readEffects = true;
                    // A new effect will only be created when the previous effect has been finished being defined.
                    // Thus setting effectDefined to true allows an initial effect to be created.
                    effectDefined = true;
                } // If the next line does not contain effect data, then the current effect must have finished being defined.
                else {
                    // Indicates the effect is finished being defined.
                    effectDefined = true;
                    // Increments the defined effect counter.
                    effectNum++;
                }
            }
        }
        // Closes the scanner.
        c.close();
    }

    /**
     * Controls the frame rate of the animation and applies effects to shapes on
     * their corresponding frames.
     * <p>
     * This loop manipulates {@link AnimationPlayer#ShapeList} data while
     * {@link JComp} draws shapes to the screen.
     */
    protected void run() {
        // Determines the next frame an event will occur.
        findNextEvent();

        // The interval between each frame in seconds.
        final float Period = 1.0f / (float) Fps;

        // A timer object is used to run logic each frame.
        // This is done to avoid disrupting the swing subsystems.
        // The actionlistener is defined on the fly rather then in a seperate file to prevent unnecessary overcomplication of the code.
        // Time intervals are accepted in milliseconds thus the Period of the frame is multiplied by 1000.
        Timer MainLoop = new Timer((int) (1000 * Period), new ActionListener() {
            // The rountine that should run each frame.
            public void actionPerformed(ActionEvent evt) {
                // Checks if the an effect should occur on this frame.
                if (CurrFrame == NextEvent) {
                    // Applies active effects to their respective shapes.
                    ViewEffects();
                    // Determines the next frame and event will occur.
                    findNextEvent();
                }
                // Increments the frame counter.
                CurrFrame++;
            }
        });

        // The main loop which will run until the animation reaches the maximum frame.
        while (CurrFrame < Frames) {
            // Runs the code defined in the timer.
            MainLoop.start();
            // Sends a request to swing to repaint the JComponent.
            Canvas.repaint();
        }
    }

    /**
     * Applies effects to their corresponding shape. This method is only called
     * on the frames which the program knows an effect will occur.
     */
    private void ViewEffects() {
        // Cycles through each shape.
        for (Shape CurrShape : ShapeList) {
            // Cycles through each effect.
            for (int y = 0; y < CurrShape.EffectList.size(); y++) {
                // Checks if the current effect occurs on this frame.
                if (CurrShape.EffectList.get(y).start == CurrFrame) {
                    // Checks the effect type.
                    switch (CurrShape.EffectList.get(y).type.toLowerCase()) {
                        case "hide":
                            // Prevents the shape from being drawn.
                            CurrShape.visible = false;
                            break;
                        case "show":
                            // Allows the shape to be drawn.
                            CurrShape.visible = true;
                            break;
                        case "jump":
                            // Checks if the current shape if a line.
                            if (CurrShape.type.equalsIgnoreCase("Line")) {
                                // Calculates the difference between the x-coordinates at the start and end of the line.
                                CurrShape.endX -= CurrShape.x;
                                // Applies that difference to the new x-coordinate to obtain the corresponding end x-coordinate.
                                CurrShape.endX += CurrShape.EffectList.get(y).x;
                                // Calculates the difference between the y-coordinates at the start and end of the line.
                                CurrShape.endY -= CurrShape.y;
                                // Applies that difference to the new y-coordinate to obtain the corresponding end y-coordinate.
                                CurrShape.endY += CurrShape.EffectList.get(y).y;
                            }
                            // Moves the shape to the new set of coordinates.
                            CurrShape.x = CurrShape.EffectList.get(y).x;
                            CurrShape.y = CurrShape.EffectList.get(y).y;
                            break;
                        case "changecolor":
                            // Changes the shapes color.
                            CurrShape.color = CurrShape.EffectList.get(y).newColor;
                            break;
                    }
                }
            }
        }
    }

    /**
     * Determines the next frame at which an event will occur. The result is
     * stored in {@link AnimationPlayer#NextEvent}.
     * <p>
     * This allows the program to save resources by only querying the effects of
     * each shape when one of the effects is guaranteed to occur.
     */
    private void findNextEvent() {
        // Sets NextEvent to the maximum number of frames to allow progressive comparison to the start frames of each effect.
        NextEvent = Frames;
        // Cycles through each shape.
        for (Shape CurrShape : ShapeList) {
            // Cycles through each effect.
            for (int y = 0; y < CurrShape.EffectList.size(); y++) {
                // Repeatedly compares the start frame of each event to determine which effect will run next.
                // Finds the lowest start frame that occurs after the current frame.
                if (CurrShape.EffectList.get(y).start < NextEvent && CurrShape.EffectList.get(y).start > CurrFrame) {
                    // Redefines NextEvent so it can be compared again.
                    NextEvent = CurrShape.EffectList.get(y).start;
                }
            }
        }
    }
}
