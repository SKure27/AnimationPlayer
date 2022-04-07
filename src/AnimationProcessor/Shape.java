package AnimationProcessor;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Can be used to represent the Shapes defined in the assignment.
 */
public class Shape {

    /**
     * The x-coordinate of the top-left corner of the shape.
     */
    protected int x = 0;
    /**
     * The y-coordinate of the top-left corner of the shape.
     */
    protected int y = 0;
    /**
     * The thickness of the border around the shape in pixels.
     */
    protected int border = 0;
    /**
     * Indicates whether or not the shape should be drawn on the screen.
     */
    protected boolean visible = false;
    /**
     * The shapes type ("Circle", "Rect", "Line").
     */
    protected String type = "";
    /**
     * The color of the shape.
     */
    protected Color color = JComp.Default;
    /**
     * The color of the shapes border.
     */
    protected Color borderColor = JComp.Default;
    /**
     * Stores the information of each {@link Effect} read from the input file.
     */
    protected ArrayList<Effect> EffectList = new ArrayList();

    /**
     * The radius of a circle.
     */
    protected int r = 0;

    /**
     * The length of a rectangle.
     */
    protected int length = 0;
    /**
     * The width of a rectangle.
     */
    protected int width = 0;

    /**
     * The x-coordinate of the endpoint of a line.
     */
    protected int endX = 0;
    /**
     * The y-coordinate of the endpoint of a line.
     */
    protected int endY = 0;

}
