import greenfoot.*;

public class HomeScreen extends World
{
    private PlayButton playButton;
    private HelpButton helpButton;

    public HomeScreen()
    {
        super(800, 450, 1);

        setBackground(
            new GreenfootImage("home_screen.png")
        );
        

        playButton = new PlayButton();
        helpButton = new HelpButton();

        addObject(playButton, 400, 300);
        addObject(helpButton, 400, 400);
        showText(
            "BUILT BY KAMSI",
            400,
            435
        );
    }
    public void act(){
                GameSound.startMusic();

    }
}