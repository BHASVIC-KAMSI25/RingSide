import greenfoot.*;

public class HealthBar extends Actor
{
    private int maxValue = 200;
    private int value = 200;

    private int width = 180;
    private int height = 18;

    public HealthBar()
    {
        updateImage();
    }

    public void updateHealth(int newValue)
    {
        value = newValue;

        if(value < 0)
        {
            value = 0;
        }

        if(value > maxValue)
        {
            value = maxValue;
        }

        updateImage();
    }

    private void updateImage()
    {
        GreenfootImage image =
            new GreenfootImage(width, height);

        image.setColor(Color.RED);
        image.fillRect(0, 0, width, height);

        int currentWidth =
            (int)((double)value / maxValue * width);

        image.setColor(Color.GREEN);
        image.fillRect(
            0,
            0,
            currentWidth,
            height
        );

        image.setColor(Color.WHITE);

        String text =
            value + " / " + maxValue;

        image.drawString(text, 65, 14);

        setImage(image);
    }
}