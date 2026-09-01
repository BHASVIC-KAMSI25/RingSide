import greenfoot.*;

public class PlayButton extends Actor
{
    public PlayButton()
    {
        setImage(
            new GreenfootImage("play.png")
        );
    }

    public void act()
    {
        if(Greenfoot.mouseClicked(this))
        {
            Greenfoot.setWorld(
                new NameScreen()
            );
        }
    }
}