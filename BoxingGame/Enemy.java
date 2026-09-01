import greenfoot.*;

public class Enemy extends Boxer
{
    private Boxer player;

    private int attackCooldown = 0;
    private int attackRecovery = 0;

    private int blockCooldown = 0;
    private int blockTimer = 0;

    private int decisionTimer = 0;

    private boolean retreating = false;

    public Enemy()
    {
        facingRight = false;

        loadAnimationFrames("enemy");

        jabHurtImage = "enemy_jab_hurt.png";
        hookHurtImage = "enemy_hook_hurt.png";
        uppercutHurtImage = "enemy_uppercut_hurt.png";

        facingRight = false;
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

        keepInRing();
        regenerateStamina();

        if(attackCooldown > 0)
        {
            attackCooldown--;
        }

        if(attackRecovery > 0)
        {
            attackRecovery--;
        }

        if(blockCooldown > 0)
        {
            blockCooldown--;
        }

        if(hurt)
        {
            hurtTimer--;

            if(hurtTimer <= 0)
            {
                hurt = false;
            }
            else
            {
                return;
            }
        }

        findPlayer();

        if(player == null)
        {
            return;
        }

        faceOpponent(player);

        if(punching)
        {
            animatePunch();
            return;
        }

        if(blocking)
        {
            blockTimer--;

            if(blockTimer <= 0)
            {
                stopBlocking();
                blockCooldown = 50;
            }

            return;
        }

        if(decisionTimer > 0)
        {
            decisionTimer--;
        }

        if(stamina <= 25)
        {
            retreating = true;
        }

        if(retreating)
        {
            retreatFromPlayer();

            if(stamina >= 60)
            {
                retreating = false;
            }

            return;
        }

        if(player.punching && blockCooldown == 0)
        {
            int random =
                Greenfoot.getRandomNumber(100);

            if(random < 35)
            {
                block();
                blockTimer = 25;
                return;
            }
        }

        if(isPlayerClose())
        {
            animateIdle();

            if(decisionTimer == 0)
            {
                attackPlayer();

                decisionTimer =
                    20 +
                    Greenfoot.getRandomNumber(30);
            }
        }
        else
        {
            moveTowardsPlayer();
        }
    }

    private void findPlayer()
    {
        if(player == null)
        {
            java.util.List<Player> players =
                getWorld().getObjects(Player.class);

            if(players.size() > 0)
            {
                player = players.get(0);
            }
        }
    }

    private void moveTowardsPlayer()
    {
        if(player == null)
        {
            return;
        }

        int distance =
            player.getX() - getX();

        if(Math.abs(distance) > 80)
        {
            if(distance < 0)
            {
                setLocation(getX() - 2, getY());
            }
            else
            {
                setLocation(getX() + 2, getY());
            }

            keepInRing();
            faceOpponent(player);
            animateWalk();
        }
        else
        {
            faceOpponent(player);
            animateIdle();
        }
    }

    private void retreatFromPlayer()
    {
        if(player == null)
        {
            return;
        }

        int distance =
            player.getX() - getX();

        if(distance < 0)
        {
            setLocation(getX() + 2, getY());
        }
        else
        {
            setLocation(getX() - 2, getY());
        }

        keepInRing();
        faceOpponent(player);
        animateWalk();
    }

    private void attackPlayer()
    {
        if(attackCooldown > 0 ||
           attackRecovery > 0 ||
           stamina < 10)
        {
            return;
        }

        int chosenAttack;

        if(stamina >= 60)
        {
            int random =
                Greenfoot.getRandomNumber(100);

            if(random < 50)
            {
                chosenAttack = 1;
            }
            else if(random < 80)
            {
                chosenAttack = 2;
            }
            else
            {
                chosenAttack = 3;
            }
        }
        else if(stamina >= 30)
        {
            int random =
                Greenfoot.getRandomNumber(100);

            if(random < 70)
            {
                chosenAttack = 1;
            }
            else
            {
                chosenAttack = 2;
            }
        }
        else
        {
            chosenAttack = 1;
        }

        punch(chosenAttack);

        attackCooldown = 60;
        attackRecovery = 35;
    }

    private boolean isPlayerClose()
    {
        if(player == null)
        {
            return false;
        }

        int distance =
            Math.abs(getX() - player.getX());

        return distance < 80;
    }
}