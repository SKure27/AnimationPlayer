package AnimationProcessor;

import java.awt.Color;

/**
 * Can be used to represent the Effects defined in the assignment.
 */
public class Effect {

    /**
     * The frame on which the effect should occur.
     */
    protected int start = 0;
    /**
     * The effect type ("Show", "Hide", "Jump", "ChangeColor").
     */
    protected String type = "";

    /**
     * The x-coordinate to jump to.
     */
    protected int x = 0;
    /**
     * The y-coordinate to jump to.
     */
    protected int y = 0;

    /**
     * The color to change to.
     */
    protected Color newColor = JComp.Default;
}
