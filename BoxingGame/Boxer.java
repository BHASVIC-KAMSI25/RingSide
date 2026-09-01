import greenfoot.*;

public class Boxer extends Actor
{
    private GreenfootImage[] idleFrames;
    private GreenfootImage[] walkFrames;
    private GreenfootImage[] jabFrames;
    private GreenfootImage[] hookFrames;
    private GreenfootImage[] uppercutFrames;
    private GreenfootImage[] knockoutFrames;

    private int frame = 0;
    private int walkFrame = 0;
    private int jabFrame = 0;
    private int animationTimer = 0;

    protected int punchTimer = 0;

    protected boolean punching = false;
    protected int punchCooldown = 0;
    protected int attackType = 0;

    protected int health = 200;
    protected int maxHealth = 200;

    protected HealthBar healthBar;

    protected StaminaBar staminaBar;
    protected int stamina = 100;
    protected int maxStamina = 100;
    protected int staminaTimer = 0;

    protected boolean hurt = false;
    protected int hurtTimer = 0;

    protected boolean blocking = false;

    protected boolean knockedOut = false;
    protected int knockoutFrame = 0;
    protected int knockoutTimer = 0;

    protected String jabHurtImage;
    protected String hookHurtImage;
    protected String uppercutHurtImage;

    protected boolean facingRight = true;

    private GreenfootImage currentBaseImage;

    public Boxer()
    {
    }
    

    protected void loadAnimationFrames(String prefix)
    {
        idleFrames = new GreenfootImage[3];

        idleFrames[0] = new GreenfootImage(prefix + "_idle_1.png");
        idleFrames[1] = new GreenfootImage(prefix + "_idle_2.png");
        idleFrames[2] = new GreenfootImage(prefix + "_idle_1.png");

        walkFrames = new GreenfootImage[4];

        walkFrames[0] = new GreenfootImage(prefix + "_idle_1.png");
        walkFrames[1] = new GreenfootImage(prefix + "_walk_2.png");
        walkFrames[2] = new GreenfootImage(prefix + "_idle_1.png");
        walkFrames[3] = new GreenfootImage(prefix + "_walk_4.png");

        jabFrames = new GreenfootImage[5];

        jabFrames[0] = new GreenfootImage(prefix + "_idle_1.png");
        jabFrames[1] = new GreenfootImage(prefix + "_jab_2.png");
        jabFrames[2] = new GreenfootImage(prefix + "_jab_3.png");
        jabFrames[3] = new GreenfootImage(prefix + "_jab_4.png");
        jabFrames[4] = new GreenfootImage(prefix + "_idle_2.png");

        hookFrames = new GreenfootImage[5];

        hookFrames[0] = new GreenfootImage(prefix + "_idle_1.png");
        hookFrames[1] = new GreenfootImage(prefix + "_hook_2.png");
        hookFrames[2] = new GreenfootImage(prefix + "_hook_3.png");
        hookFrames[3] = new GreenfootImage(prefix + "_hook_4.png");
        hookFrames[4] = new GreenfootImage(prefix + "_idle_2.png");

        uppercutFrames = new GreenfootImage[5];

        uppercutFrames[0] = new GreenfootImage(prefix + "_idle_1.png");
        uppercutFrames[1] = new GreenfootImage(prefix + "_uppercut_2.png");
        uppercutFrames[2] = new GreenfootImage(prefix + "_uppercut_3.png");
        uppercutFrames[3] = new GreenfootImage(prefix + "_uppercut_4.png");
        uppercutFrames[4] = new GreenfootImage(prefix + "_idle_2.png");

        /*
         * Knockout animation.
         *
         * Frame 0 = normal standing position
         * Frame 1 = beginning to fall
         * Frame 2 = falling further
         * Frame 3 = completely on the ground
         */
        knockoutFrames = new GreenfootImage[4];

        knockoutFrames[0] =
            new GreenfootImage(prefix + "_idle_1.png");

        knockoutFrames[1] =
            new GreenfootImage(prefix + "_knockout_2.png");

        knockoutFrames[2] =
            new GreenfootImage(prefix + "_knockout_3.png");

        knockoutFrames[3] =
            new GreenfootImage(prefix + "_knockout_4.png");

        currentBaseImage = idleFrames[0];
        updateDisplayedImage();
    }

    protected void showImage(GreenfootImage original)
    {
        currentBaseImage = original;
        updateDisplayedImage();
    }

    private void updateDisplayedImage()
    {
        if(currentBaseImage == null)
        {
            return;
        }

        GreenfootImage display =
            new GreenfootImage(currentBaseImage);

        /*
         * PLAYER images are drawn facing RIGHT.
         * ENEMY images are drawn facing LEFT.
         */

        if(this instanceof Player)
        {
            if(!facingRight)
            {
                display.mirrorHorizontally();
            }
        }
        else if(this instanceof Enemy)
        {
            if(facingRight)
            {
                display.mirrorHorizontally();
            }
        }

        setImage(display);
    }

    protected void faceOpponent(Boxer opponent)
    {
        if(opponent == null || knockedOut)
        {
            return;
        }

        boolean shouldFaceRight =
            opponent.getX() > getX();

        if(facingRight != shouldFaceRight)
        {
            facingRight = shouldFaceRight;
            updateDisplayedImage();
        }
    }

    public void setFacingRight(boolean direction)
    {
        facingRight = direction;
    }

    public void refreshFacingImage()
    {
        updateDisplayedImage();
    }

    public boolean movePlayer()
    {
        if(knockedOut)
        {
            return false;
        }

        boolean moving = false;

        int speed = 3;

        if(Greenfoot.isKeyDown("d") ||
           Greenfoot.isKeyDown("right"))
        {
            setLocation(getX() + speed, getY());
            moving = true;
        }

        if(Greenfoot.isKeyDown("a") ||
           Greenfoot.isKeyDown("left"))
        {
            setLocation(getX() - speed, getY());
            moving = true;
        }

        keepInRing();

        return moving;
    }

    public void animateIdle()
    {
        if(knockedOut)
        {
            return;
        }

        animationTimer++;

        if(animationTimer % 10 == 0)
        {
            frame++;

            if(frame >= idleFrames.length)
            {
                frame = 0;
            }

            showImage(idleFrames[frame]);
        }
    }

    public void animateWalk()
    {
        if(knockedOut)
        {
            return;
        }

        animationTimer++;

        if(animationTimer % 10 == 0)
        {
            walkFrame++;

            if(walkFrame >= walkFrames.length)
            {
                walkFrame = 0;
            }

            showImage(walkFrames[walkFrame]);
        }
    }

    public void punch(int type)
    {
        if(knockedOut)
        {
            return;
        }

        int cost = getStaminaCost(type);

        if(stamina < cost)
        {
            return;
        }

        stamina -= cost;

        if(staminaBar != null)
        {
            staminaBar.updateStamina(stamina);
        }

        punching = true;
        blocking = false;

        attackType = type;
        jabFrame = 0;
        punchTimer = 0;
    }

    protected void block()
    {
        if(punching || knockedOut)
        {
            return;
        }

        if(stamina <= 0)
        {
            blocking = false;
            return;
        }

        blocking = true;

        String imageName;

        if(this instanceof Player)
        {
            imageName = "boxer_block.png";
        }
        else
        {
            imageName = "enemy_block.png";
        }

        showImage(new GreenfootImage(imageName));
    }

    protected void stopBlocking()
    {
        blocking = false;
    }

    public void animatePunch()
    {
        if(knockedOut)
        {
            return;
        }

        punchTimer++;

        if(punchTimer % 5 == 0)
        {
            GreenfootImage[] currentAttack;

            if(attackType == 1)
            {
                currentAttack = jabFrames;
            }
            else if(attackType == 2)
            {
                currentAttack = hookFrames;
            }
            else
            {
                currentAttack = uppercutFrames;
            }

            showImage(currentAttack[jabFrame]);

            if(attackType == 1 && jabFrame == 3)
{
    GameSound.playJab();
    checkHit(4);
}
else if(attackType == 2 && jabFrame == 3)
{
    GameSound.playHook();
    checkHit(7);
}
else if(attackType == 3 && jabFrame == 4)
{
    GameSound.playUppercut();
    checkHit(10);
}

            jabFrame++;

            if(jabFrame >= currentAttack.length)
            {
                punching = false;
                jabFrame = 0;
                attackType = 0;
            }
        }
    }

    public void checkHit(int damage)
    {
        if(knockedOut)
        {
            return;
        }

        java.util.List<Boxer> fighters =
            getWorld().getObjects(Boxer.class);

        for(Boxer fighter : fighters)
        {
            if(fighter != this &&
               !fighter.knockedOut)
            {
                int distance =
                    Math.abs(getX() - fighter.getX());

                if(distance < 70)
                {
                    fighter.takeDamage(
                        damage,
                        attackType
                    );
                }
            }
        }
    }

    public void takeDamage(int damage, int attackType)
    {
        if(knockedOut)
        {
            return;
        }

        /*
         * BLOCKING
         */
        if(blocking)
        {
            GameSound.playBlock();
            int staminaDamage = 0;
            int knockback = 0;

            if(attackType == 1)
            {
                staminaDamage = 5;
                knockback = 6;
            }
            else if(attackType == 2)
            {
                staminaDamage = 10;
                knockback = 10;
            }
            else if(attackType == 3)
            {
                staminaDamage = 15;
                knockback = 14;
            }

            applyKnockback(knockback);

            stamina -= staminaDamage;

            if(stamina < 0)
            {
                stamina = 0;
            }

            if(staminaBar != null)
            {
                staminaBar.updateStamina(stamina);
            }

            damage /= 2;

            health -= damage;

            if(health < 0)
            {
                health = 0;
            }

            if(healthBar != null)
            {
                healthBar.updateHealth(health);
            }

            if(stamina <= 0)
            {
                blocking = false;
                hurt = true;
                hurtTimer = 10;

                showHurtImage(attackType);
            }

            return;
        }

        /*
         * NORMAL HIT
         */

        int knockback = 0;

        if(attackType == 1)
        {
            knockback = 12;
        }
        else if(attackType == 2)
        {
            knockback = 20;
        }
        else if(attackType == 3)
        {
            knockback = 28;
        }

        applyKnockback(knockback);

        health -= damage;

        if(health < 0)
        {
            health = 0;
        }

        if(healthBar != null)
        {
            healthBar.updateHealth(health);
        }

        /*
         * HEALTH REACHED ZERO
         *
         * This is the actual knockout condition.
         */
        if(health <= 0)
        {
            startKnockout();
            return;
        }

        hurt = true;
        hurtTimer = 10;

        showHurtImage(attackType);
    }

    /*
     * Starts the knockout animation.
     */
    protected void startKnockout()
    {
        if(knockedOut)
        {
            return;
        }

        knockedOut = true;

        punching = false;
        blocking = false;
        hurt = false;

        punchTimer = 0;
        punchCooldown = 0;
        attackType = 0;

        knockoutFrame = 0;
        knockoutTimer = 0;

        /*
         * First KO frame is their normal standing position.
         */
        showImage(knockoutFrames[0]);
    }

    /*
     * Plays the knockout animation.
     */
    protected void animateKnockout()
    {
        if(!knockedOut)
        {
            return;
        }

        /*
         * Slow fall.
         * Increase this number to make the fall slower.
         */
        knockoutTimer++;

        if(knockoutTimer >= 10)
        {
            knockoutTimer = 0;

            if(knockoutFrame < knockoutFrames.length - 1)
            {
                knockoutFrame++;

                showImage(
                    knockoutFrames[knockoutFrame]
                );
            }
            else
            {
                /*
                 * Frame 4 has been reached.
                 * The boxer stays down.
                 */
                knockoutFrame =
                    knockoutFrames.length - 1;

                showImage(
                    knockoutFrames[knockoutFrame]
                );
            }
        }
    }

    protected void showHurtImage(int attackType)
    {
        String imageName = null;

        if(attackType == 1)
        {
            imageName = jabHurtImage;
        }
        else if(attackType == 2)
        {
            imageName = hookHurtImage;
        }
        else if(attackType == 3)
        {
            imageName = uppercutHurtImage;
        }

        if(imageName != null)
        {
            showImage(new GreenfootImage(imageName));
        }
    }

    protected void applyKnockback(int distance)
    {
        if(distance <= 0)
        {
            return;
        }

        java.util.List<Boxer> fighters =
            getWorld().getObjects(Boxer.class);

        Boxer attacker = null;

        for(Boxer fighter : fighters)
        {
            if(fighter != this &&
               !fighter.knockedOut)
            {
                attacker = fighter;
                break;
            }
        }

        if(attacker == null)
        {
            return;
        }

        int direction;

        if(attacker.getX() < getX())
        {
            direction = 1;
        }
        else
        {
            direction = -1;
        }

        int newX =
            getX() + (direction * distance);

        if(newX < 200)
        {
            newX = 200;
        }

        if(newX > 600)
        {
            newX = 600;
        }

        setLocation(newX, getY());
    }

    public void setHealthBar(HealthBar bar)
    {
        healthBar = bar;

        if(healthBar != null)
        {
            healthBar.updateHealth(health);
        }
    }

    public void setStaminaBar(StaminaBar bar)
    {
        staminaBar = bar;

        if(staminaBar != null)
        {
            staminaBar.updateStamina(stamina);
        }
    }

    public void resetForNewRound()
    {
        punching = false;
        blocking = false;
        hurt = false;
        knockedOut = false;

        punchTimer = 0;
        punchCooldown = 0;
        attackType = 0;
        hurtTimer = 0;

        knockoutFrame = 0;
        knockoutTimer = 0;

        stamina = maxStamina;
        staminaTimer = 0;

        if(health < 100)
        {
            health = 100;
        }

        if(healthBar != null)
        {
            healthBar.updateHealth(health);
        }

        if(staminaBar != null)
        {
            staminaBar.updateStamina(stamina);
        }

        refreshFacingImage();
    }

    protected void keepInRing()
    {
        int x = getX();
        int y = getY();

        if(x < 200)
        {
            x = 200;
        }

        if(x > 600)
        {
            x = 600;
        }

        if(y < 200)
        {
            y = 200;
        }

        if(y > 280)
        {
            y = 280;
        }

        setLocation(x, y);
    }

    protected int getStaminaCost(int attackType)
    {
        if(attackType == 1)
        {
            return 10;
        }

        if(attackType == 2)
        {
            return 20;
        }

        if(attackType == 3)
        {
            return 30;
        }

        return 0;
    }

    protected void regenerateStamina()
    {
        if(knockedOut)
        {
            return;
        }

        staminaTimer++;

        if(staminaTimer >= 30)
        {
            staminaTimer = 0;

            if(stamina < maxStamina)
            {
                stamina += 5;

                if(stamina > maxStamina)
                {
                    stamina = maxStamina;
                }

                if(staminaBar != null)
                {
                    staminaBar.updateStamina(stamina);
                }
            }
        }
    }

    public boolean isKnockedOut()
    {
        return knockedOut;
    }

    public boolean isFightActive()
    {
        if(getWorld() instanceof BoxingWorld)
        {
            return ((BoxingWorld)getWorld()).isFightActive();
        }

        return true;
    }
    public boolean isKnockoutFinished()
{
    return knockedOut && knockoutFrame >= 3;
}
public int getHealth()
{
    return health;
}
}