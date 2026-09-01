import greenfoot.*;

public class StaminaBar extends Actor
{
    private int maxValue = 100;
    private int value = 100;

    private int width = 180;
    private int height = 14;

    public StaminaBar()
    {
        updateImage();
    }

    public void updateStamina(int newValue)
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
        GreenfootImage image = new GreenfootImage(width, height);

        // Dark background
        image.setColor(Color.DARK_GRAY);
        image.fillRect(0, 0, width, height);

        // Blue stamina
        int currentWidth =
            (int)((double)value / maxValue * width);

        image.setColor(Color.BLUE);
        image.fillRect(0, 0, currentWidth, height);

        // Number
        image.setColor(Color.WHITE);

        String text = value + " / " + maxValue;

        image.drawString(text, 66, 11);

        setImage(image);
    }
}