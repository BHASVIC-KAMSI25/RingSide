import greenfoot.*;

public class NameScreen extends World
{
    private String playerName = "";

    public NameScreen()
    {
        super(800, 450, 1);
        setBackground(
            new GreenfootImage("bg.png")
        );

        showText(
            "ENTER YOUR NAME",
            400,
            130
        );

        showText(
            "PRESS ENTER WHEN DONE",
            400,
            300
        );

        updateNameDisplay();
    }

    public void act()
    {
        String key = Greenfoot.getKey();

        if(key != null)
        {
            if(key.equals("enter"))
            {
                if(playerName.length() > 0)
                {
                    Greenfoot.setWorld(
                        new FightIntroScreen(
                            playerName,
                            "VANDAL SAVAGE"
                        )
                    );
                }
            }
            else if(key.equals("backspace"))
            {
                if(playerName.length() > 0)
                {
                    playerName =
                        playerName.substring(
                            0,
                            playerName.length() - 1
                        );
                }
            }
            else if(key.length() == 1)
            {
                if(playerName.length() < 12)
                {
                    playerName +=
                        key.toUpperCase();
                }
            }

            updateNameDisplay();
        }
    }

    private void updateNameDisplay()
    {
        showText(
            playerName + "_",
            400,
            220
        );
    }
}