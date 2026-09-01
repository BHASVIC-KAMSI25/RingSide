import greenfoot.*;

public class Player extends Boxer
{
    public Player()
    {
        facingRight = true;

        loadAnimationFrames("boxer");

        jabHurtImage = "boxer_jab_hurt.png";
        hookHurtImage = "boxer_hook_hurt.png";
        uppercutHurtImage = "boxer_uppercut_hurt.png";

        facingRight = true;
        refreshFacingImage();
    }

    public void act()
    {
        /*
         * Once knocked out, only play the KO animation.
         */
        if(knockedOut)
        {
            animateKnockout();
            return;
        }

        if(!isFightActive())
        {
            return;
        }

        java.util.List<Enemy> enemies =
            getWorld().getObjects(Enemy.class);

        if(enemies.size() > 0)
        {
            faceOpponent(enemies.get(0));
        }

        if(Greenfoot.isKeyDown("s") &&
           !punching)
        {
            block();
        }
        else
        {
            stopBlocking();

            if(punching)
            {
                animatePunch();
            }
            else
            {
                boolean moving =
                    movePlayer();

                if(moving)
                {
                    animateWalk();
                }
                else
                {
                    animateIdle();
                }
            }
        }

        if(Greenfoot.isKeyDown("j") &&
           !punching &&
           !blocking &&
           punchCooldown == 0)
        {
            punch(1);
            punchCooldown = 20;
        }

        if(Greenfoot.isKeyDown("k") &&
           !punching &&
           !blocking &&
           punchCooldown == 0)
        {
            punch(2);
            punchCooldown = 20;
        }

        if(Greenfoot.isKeyDown("l") &&
           !punching &&
           !blocking &&
           punchCooldown == 0)
        {
            punch(3);
            punchCooldown = 20;
        }

        if(punchCooldown > 0)
        {
            punchCooldown--;
        }

        regenerateStamina();
    }
}