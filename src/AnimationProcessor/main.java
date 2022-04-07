package AnimationProcessor;

/**
 * The main class.
 *
 * @author Sebastian Kure
 * @author Jordan Vo
 * @author Azim Hamza
 * @author Faiz Khan
 * @version 1.0
 * @since 2022-04-04
 */
public class main {

    /**
     * Defines a new {@link AnimationPlayer#AnimationPlayer Animation Player}, {@link AnimationPlayer#loadAnimationFromFile loads}
     * {@link Shape} and {@link Effect} data from the input file, then {@link AnimationPlayer#run plays the
     * animation}.
     */
    public static void main(String[] args) {
        // Creates the animation player. Runs its constructor.
        AnimationPlayer player = new AnimationPlayer();
        // Loads the information from the input file into the ShapeList.
        player.loadAnimationFromFile("SampleInputt.txt");
        // Runs the animation assuming all information is stored correctly.
        player.run();
    }
}
