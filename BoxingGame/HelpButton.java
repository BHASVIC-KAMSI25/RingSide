import greenfoot.*;

public class HelpButton extends Actor
{
    public HelpButton()
    {
        setImage(
            new GreenfootImage("help.png")
        );
    }

    public void act()
    {
        if(Greenfoot.mouseClicked(this))
        {
            Greenfoot.setWorld(
                new HelpScreen()
            );
        }
    }
}