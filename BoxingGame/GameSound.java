import greenfoot.*;

public class GameSound
{
    private static GreenfootSound bgMusic =
        new GreenfootSound("bg_music.mp3");

    public static void startMusic()
    {
        if(!bgMusic.isPlaying())
        {
            bgMusic.setVolume(40);
            bgMusic.playLoop();
        }
    }

    public static void stopMusic()
    {
        bgMusic.stop();
    }

    public static void playJab()
    {
        Greenfoot.playSound("jab.mp3");
    }

    public static void playHook()
    {
        Greenfoot.playSound("hook.mp3");
    }

    public static void playUppercut()
    {
        Greenfoot.playSound("uppercut.mp3");
    }

    public static void playBlock()
    {
        Greenfoot.playSound("block.mp3");
    }

    public static void playKO()
    {
        Greenfoot.playSound("KO.mp3");
    }

    public static void playBell()
    {
        Greenfoot.playSound("bell.mp3");
    }
}