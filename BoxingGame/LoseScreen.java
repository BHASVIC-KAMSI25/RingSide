
import greenfoot.*;

public class LoseScreen extends World
{
    private BackButton backButton;

    public LoseScreen(
        String playerName,
        String opponentName,
        int playerRoundsWon,
        int opponentRoundsWon,
        String resultType
    )
    {
        super(800, 450, 1);

        setBackground(
            new GreenfootImage("lose_screen.png")
        );

        /*
         * Opponent's name.
         */
        showText(
            opponentName,
            400,
            350
        );

        /*
         * Round score.
         */
        showText(
            playerName + " won " +
            playerRoundsWon + " round" +
            (playerRoundsWon == 1 ? "" : "s"),
            400,
            375
        );

        showText(
            opponentName + " won " +
            opponentRoundsWon + " round" +
            (opponentRoundsWon == 1 ? "" : "s"),
            400,
            395
        );

        /*
         * How the player lost.
         */
        if(resultType.equals("KNOCKOUT"))
        {
            showText(
                opponentName + " wins by knockout!",
                400,
                415
            );
        }
        else if(resultType.equals("DECISION"))
        {
            showText(
                opponentName + " wins by decision!",
                400,
                415
            );
        }
        else
        {
            showText(
                "DRAW",
                400,
                415
            );
        }

        /*
         * Back to menu button.
         */
        backButton = new BackButton();

        addObject(
            backButton,
            400,
            440
        );
    }
}