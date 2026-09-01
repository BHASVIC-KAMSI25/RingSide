import greenfoot.*;

public class BackButton extends Actor
{
    public BackButton()
    {
        setImage(
            new GreenfootImage("back.png")
        );
    }

    public void act()
    {
        if(Greenfoot.mouseClicked(this))
        {
            Greenfoot.setWorld(
                new HomeScreen()
            );
        }
    }
}