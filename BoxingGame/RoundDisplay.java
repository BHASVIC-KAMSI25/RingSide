import greenfoot.*;

public class RoundDisplay extends Actor
{
    public RoundDisplay()
    {
        showFightTimer(90, 1);
    }

    public void showFightTimer(int seconds, int round)
    {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;

        String time;

        if(remainingSeconds < 10)
        {
            time = minutes + ":0" + remainingSeconds;
        }
        else
        {
            time = minutes + ":" + remainingSeconds;
        }

        GreenfootImage image =
            new GreenfootImage(
                time,
                38,
                Color.RED,
                new Color(0, 0, 0, 0)
            );

        setImage(image);
    }

    public void showBeginning(int round, int seconds)
    {
        GreenfootImage image =
            new GreenfootImage(
                "ROUND " + round +
                " BEGINNING IN " +
                seconds,
                36,
                Color.RED,
                new Color(0, 0, 0, 0)
            );

        setImage(image);
    }

    public void showMatchOver()
    {
        GreenfootImage image =
            new GreenfootImage(
                "MATCH OVER",
                44,
                Color.RED,
                new Color(0, 0, 0, 0)
            );

        setImage(image);
    }
}