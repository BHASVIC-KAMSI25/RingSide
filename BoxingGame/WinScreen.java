
import greenfoot.*;

public class WinScreen extends World
{
    private BackButton backButton;

    public WinScreen(
        String playerName,
        String opponentName,
        int playerRoundsWon,
        int opponentRoundsWon,
        String resultType
    )
    {
        super(800, 450, 1);

        setBackground(
            new GreenfootImage("win_screen.png")
        );

        /*
         * Player's name.
         */
        showText(
            playerName,
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
         * How the player won.
         */
        if(resultType.equals("KNOCKOUT"))
        {
            showText(
                playerName + " wins by knockout!",
                400,
                415
            );
        }
        else if(resultType.equals("DECISION"))
        {
            showText(
                playerName + " wins by decision!",
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
