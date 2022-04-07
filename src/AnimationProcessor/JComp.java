package AnimationProcessor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 * A subclass of the {@link javax.swing.JComponent} class, modified to allow
 * custom drawing to {@link AnimationPlayer#Canvas itself}.
 */
public class JComp extends JComponent {

    /**
     * The default {@link java.awt.color Color} used when an object being drawn
     * to the screen does not have a custom {@link Shape#color Color}.
     */
    static protected final Color Default = Color.black;

    /**
     * A custom version of the JComponents
     * {@link javax.swing.JComponent#paint paint} method.<p>
     * Overwriting this method has allowed for the JComponent to be
     * {@link java.awt.Graphics modified} and redrawn by calling
     * {@link java.awt.Component#repaint repaint}.
     *
     * @param g The {@link java.awt.Graphics Graphics} object provided by
     * {@link javax.swing swing}. After modification, the contents of the object
     * are copied to the JComponent.
     */
    public void paint(Graphics g) {
        // Cycles through each shape.
        for (Shape CurrShape : AnimationPlayer.ShapeList) {
            // Draws the shape onto the graphics object.
            drawShape((Graphics2D) g, CurrShape);
        }
        // Draws the current fps on the graphics object.
        g.drawString("Fps: " + AnimationPlayer.Fps, 0, AnimationPlayer.Canvas.getHeight() - 15);
        // Draws the current frame of the animation on the graphics object.
        g.drawString("Frame: " + AnimationPlayer.CurrFrame, 0, AnimationPlayer.Canvas.getHeight());
    }

    /**
     * Draws a {@link Shape} to the {@link java.awt.Graphics graphics} object
     * provided by {@link JComp#paint paint}.
     * <p>
     * The graphics object is cast to a {@link java.awt.Graphics2D graphics2D}
     * object to allow a
     * {@linkplain java.awt.Graphics2D#setStroke wider array of graphical operations}
     * to be performed.
     *
     * @param g The {@link java.awt.Graphics2D graphics} object provided by
     * {@link JComp#paint paint}.
     *
     * @param currShape The {@link Shape} to be drawn to g.
     */
    private void drawShape(Graphics2D g, Shape currShape) {
        // Checks if the current shape should be drawn on the screen.
        if (currShape.visible == true) {
            // Sets the drawing color to that of the shape.
            g.setColor(currShape.color);
            // Checks the shapes type.
            switch (currShape.type.toLowerCase()) {
                case "circle":
                    // The input file provides the coordinates for the center of the circle.
                    // The drawing methods take the coordinates of the shape from the top left.
                    // To account for this, the x and y coordinates of all drawn circles are subtracted by the circles radius.

                    // Fills the circle on the graphics object.
                    g.fillOval(currShape.x - currShape.r, currShape.y - currShape.r, currShape.r * 2, currShape.r * 2);

                    // Sets the drawing color to that of the shapes border.
                    g.setColor(currShape.borderColor);
                    // Sets the thickness of the drawn line to that of the shapes border.
                    g.setStroke(new BasicStroke(currShape.border));
                    // Draws the border of the circle on the graphics object ontop of the filled circle.
                    g.drawOval(currShape.x - currShape.r, currShape.y - currShape.r, currShape.r * 2, currShape.r * 2);
                    break;
                case "rectangle":
                case "rect":
                    // Fills the rectangle on the graphics object.
                    g.fillRect(currShape.x, currShape.y, currShape.length, currShape.width);

                    // Sets the drawing color to that of the shapes border.
                    g.setColor(currShape.borderColor);
                    // Sets the thickness of the drawn line to that of the shapes border.
                    g.setStroke(new BasicStroke(currShape.border));
                    // Draws the border of the rectangle on the graphics object ontop of the filled rectangle.
                    g.drawRect(currShape.x, currShape.y, currShape.length, currShape.width);
                    break;
                case "line":
                    // Sets the drawing color to that of the shapes border.
                    g.setColor(currShape.borderColor);
                    // Sets the thickness of the drawn line to that of the shapes border.
                    g.setStroke(new BasicStroke(currShape.border));
                    // To draw the border of the line, a thicker line is drawn underneath with the thickness of the border.
                    g.drawLine(currShape.x, currShape.y, currShape.endX, currShape.endY);

                    // Sets the drawing color to that of the shape.
                    g.setColor(currShape.color);
                    // Sets the thickness of the drawn line to 1 as that is the assumed default thickness.
                    g.setStroke(new BasicStroke(1));
                    // Draws the line ontop of the border.
                    g.drawLine(currShape.x, currShape.y, currShape.endX, currShape.endY);
                    break;
            }
        }

    }
}
