import greenfoot.*;

public class FightIntroScreen extends World
{
    private String playerName;
    private String opponentName;

    private int timer = 0;

    
    private final int DISPLAY_TIME = 180;

    public FightIntroScreen(
        String playerName,
        String opponentName
    )
    {
        super(800, 450, 1);

        this.playerName = playerName;
        this.opponentName = opponentName;

    
        setBackground(
            new GreenfootImage("fight_intro.png")
        );

        // Player's name
        showText(
            playerName,
            200,
            70
        );

        // Opponent's name
        showText(
            opponentName,
            650,
            70
        );
    }

    public void act()
    {
        timer++;

        if(timer >= DISPLAY_TIME)
        {
            Greenfoot.setWorld(
                new BoxingWorld(
                    playerName,
                    opponentName
                )
            );
        }
    }
}