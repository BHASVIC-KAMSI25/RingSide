
import greenfoot.*;

public class BoxingWorld extends World
{
    private HealthBar playerBar;
    private HealthBar enemyBar;

    private StaminaBar playerStaminaBar;
    private StaminaBar enemyStaminaBar;

    private Player player;
    private Enemy opponent;

    private RoundDisplay display;

    private final int TOTAL_ROUNDS = 3;
    private final int ROUND_LENGTH = 90 * 60;

    private int currentRound = 1;
    private int roundTimer = ROUND_LENGTH;

    /*
     * Round states:
     *
     * 0 = Fight
     * 1 = Between rounds countdown
     * 2 = Match over
     * 3 = Knockout animation
     */
    private int roundState = 0;

    private int countdownTimer = 0;
    private int countdownNumber = 5;

    /*
     * KO information.
     */
    private String knockoutWinner = "";

    /*
     * Delay after KO animation reaches the ground.
     */
    private int knockoutEndTimer = 0;
    private final int KNOCKOUT_END_DELAY = 90;

    /*
     * DECISION SCORING
     *
     * Number of rounds won by each fighter.
     */
    private int playerRoundsWon = 0;
    private int opponentRoundsWon = 0;

    /*
     * Used as a tiebreaker if the round score
     * somehow ends completely level.
     */
    private int playerTotalHealth = 0;
    private int opponentTotalHealth = 0;

    private final int PLAYER_START_X = 260;
    private final int ENEMY_START_X = 500;
    private final int FIGHTER_START_Y = 260;

    private String playerName;
    private String opponentName;

    public BoxingWorld(
        String playerName,
        String opponentName
    )
    {
        super(800, 450, 1);

        this.playerName = playerName;
        this.opponentName = opponentName;

        prepare();
        startRound();
    }

    private void prepare()
    {
        player = new Player();
        opponent = new Enemy();

        playerBar = new HealthBar();
        enemyBar = new HealthBar();

        playerStaminaBar = new StaminaBar();
        enemyStaminaBar = new StaminaBar();

        addObject(
            player,
            PLAYER_START_X,
            FIGHTER_START_Y
        );

        addObject(
            opponent,
            ENEMY_START_X,
            FIGHTER_START_Y
        );

        addObject(playerBar, 150, 30);
        addObject(enemyBar, 650, 30);

        addObject(playerStaminaBar, 150, 50);
        addObject(enemyStaminaBar, 650, 50);

        showText("HEALTH", 35, 30);
        showText("STAMINA", 42, 50);

        showText("HEALTH", 760, 30);
        showText("STAMINA", 758, 50);

        player.setHealthBar(playerBar);
        opponent.setHealthBar(enemyBar);

        player.setStaminaBar(playerStaminaBar);
        opponent.setStaminaBar(enemyStaminaBar);

        display = new RoundDisplay();
        addObject(display, 400, 100);

        player.setFacingRight(true);
        opponent.setFacingRight(false);

        player.refreshFacingImage();
        opponent.refreshFacingImage();

        showText(
            playerName,
            150,
            75
        );

        showText(
            opponentName,
            650,
            75
        );
    }

    public void act()
    {
        /*
         * NORMAL FIGHT
         */
        if(roundState == 0)
        {
            checkForKnockout();

            if(roundState == 0)
            {
                updateFightTimer();
            }
        }

        /*
         * BETWEEN ROUNDS
         */
        else if(roundState == 1)
        {
            updateCountdown();
        }

        /*
         * KO ANIMATION
         */
        else if(roundState == 3)
        {
            updateKnockout();
        }
    }

    private void startRound()
    {
        roundTimer = ROUND_LENGTH;
        roundState = 0;

        knockoutWinner = "";
        knockoutEndTimer = 0;
        GameSound.playBell();

        player.setLocation(
            PLAYER_START_X,
            FIGHTER_START_Y
        );

        opponent.setLocation(
            ENEMY_START_X,
            FIGHTER_START_Y
        );

        player.setFacingRight(true);
        opponent.setFacingRight(false);

        player.resetForNewRound();
        opponent.resetForNewRound();

        player.refreshFacingImage();
        opponent.refreshFacingImage();

        display.showFightTimer(
            90,
            currentRound
        );
    }

    private void updateFightTimer()
    {
        if(roundTimer > 0)
        {
            roundTimer--;
        }

        int seconds = roundTimer / 60;

        display.showFightTimer(
            seconds,
            currentRound
        );

        if(roundTimer <= 0)
        {
            endRound();
        }
    }

    /*
     * Checks whether either boxer has been knocked out.
     */
    private void checkForKnockout()
    {
        /*
         * Enemy has been knocked out.
         */
        if(opponent.isKnockedOut())
{
    GameSound.playKO();

    knockoutWinner = "PLAYER";
    roundState = 3;
    knockoutEndTimer = 0;

    return;
}
        /*
         * Player has been knocked out.
         */
        if(player.isKnockedOut())
{
    GameSound.playKO();

    knockoutWinner = "ENEMY";
    roundState = 3;
    knockoutEndTimer = 0;

    return;
}
    }

    /*
     * Waits for the losing boxer's knockout animation
     * to finish and then waits before changing screen.
     */
    private void updateKnockout()
    {
        if(knockoutWinner.equals("PLAYER"))
        {
            if(opponent.isKnockoutFinished())
            {
                knockoutEndTimer++;

                if(knockoutEndTimer >= KNOCKOUT_END_DELAY)
                {
                    showKnockoutResult();
                }
            }
        }
        else if(knockoutWinner.equals("ENEMY"))
        {
            if(player.isKnockoutFinished())
            {
                knockoutEndTimer++;

                if(knockoutEndTimer >= KNOCKOUT_END_DELAY)
                {
                    showKnockoutResult();
                }
            }
        }
    }

    /*
     * Sends the player to the correct KO result screen.
     */
    private void showKnockoutResult()
    {
        roundState = 2;

        /*
         * PLAYER WON BY KO
         */
        if(knockoutWinner.equals("PLAYER"))
        {
            Greenfoot.setWorld(
                new WinScreen(
                    playerName,
                    opponentName,
                    playerRoundsWon,
                    opponentRoundsWon,
                    "KNOCKOUT"
                )
            );
        }

        /*
         * PLAYER LOST BY KO
         */
        else if(knockoutWinner.equals("ENEMY"))
        {
            Greenfoot.setWorld(
                new LoseScreen(
                    playerName,
                    opponentName,
                    playerRoundsWon,
                    opponentRoundsWon,
                    "KNOCKOUT"
                )
            );
        }
    }

    /*
     * Called when the 90-second round timer reaches zero.
     */
    private void endRound()
    {
        /*
         * FIRST:
         * Decide who won this round.
         *
         * This happens BEFORE startRound(),
         * because startRound() resets round information.
         */
        scoreCurrentRound();

        /*
         * If this was round 3, the match is finished.
         */
        if(currentRound >= TOTAL_ROUNDS)
        {
            matchOver();
            return;
        }

        /*
         * Otherwise start the between-round countdown.
         */
        roundState = 1;

        countdownNumber = 5;
        countdownTimer = 60;

        display.showBeginning(
            currentRound + 1,
            countdownNumber
        );
    }

    /*
     * Decides who won the current round.
     *
     * Higher remaining health = round winner.
     *
     * Equal health = drawn round.
     */
    private void scoreCurrentRound()
    {
        int playerHealth = player.getHealth();
        int opponentHealth = opponent.getHealth();

        /*
         * Save health for the overall tiebreaker.
         */
        playerTotalHealth += playerHealth;
        opponentTotalHealth += opponentHealth;

        /*
         * PLAYER WON THE ROUND
         */
        if(playerHealth > opponentHealth)
        {
            playerRoundsWon++;
        }

        /*
         * OPPONENT WON THE ROUND
         */
        else if(opponentHealth > playerHealth)
        {
            opponentRoundsWon++;
        }

        /*
         * Equal health = draw.
         * Nobody receives a round point.
         */
    }

    private void updateCountdown()
    {
        countdownTimer--;

        if(countdownTimer <= 0)
        {
            countdownNumber--;

            if(countdownNumber <= 0)
            {
                currentRound++;

                startRound();

                return;
            }

            countdownTimer = 60;
        }

        display.showBeginning(
            currentRound + 1,
            countdownNumber
        );
    }

    /*
     * Three rounds have finished.
     *
     * Decide the winner based on rounds won.
     */
    private void matchOver()
    {
        roundState = 2;

        /*
         * PLAYER WON THE DECISION
         */
        if(playerRoundsWon > opponentRoundsWon)
        {
            Greenfoot.setWorld(
                new WinScreen(
                    playerName,
                    opponentName,
                    playerRoundsWon,
                    opponentRoundsWon,
                    "DECISION"
                )
            );

            return;
        }

        /*
         * OPPONENT WON THE DECISION
         */
        if(opponentRoundsWon > playerRoundsWon)
        {
            Greenfoot.setWorld(
                new LoseScreen(
                    playerName,
                    opponentName,
                    playerRoundsWon,
                    opponentRoundsWon,
                    "DECISION"
                )
            );

            return;
        }

        /*
         * If the round score is completely tied,
         * use total remaining health as a tiebreaker.
         */
        if(playerTotalHealth > opponentTotalHealth)
        {
            Greenfoot.setWorld(
                new WinScreen(
                    playerName,
                    opponentName,
                    playerRoundsWon,
                    opponentRoundsWon,
                    "DECISION"
                )
            );
        }
        else if(opponentTotalHealth > playerTotalHealth)
        {
            Greenfoot.setWorld(
                new LoseScreen(
                    playerName,
                    opponentName,
                    playerRoundsWon,
                    opponentRoundsWon,
                    "DECISION"
                )
            );
        }
        else
        {
            /*
             * Extremely unlikely true draw.
             *
             * For now, send the player to the lose screen.
             * We can make a separate DrawScreen later.
             */
            Greenfoot.setWorld(
                new LoseScreen(
                    playerName,
                    opponentName,
                    playerRoundsWon,
                    opponentRoundsWon,
                    "DRAW"
                )
            );
        }
    }

    public boolean isFightActive()
    {
        return roundState == 0;
    }
}