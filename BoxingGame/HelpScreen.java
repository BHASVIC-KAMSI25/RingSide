import greenfoot.*;

public class HelpScreen extends World
{
    private BackButton backButton;

    public HelpScreen()
    {
        super(800, 450, 1);
        setBackground(
            new GreenfootImage("bg.png")
        );

        // TITLE
        showText(
            "HOW TO PLAY",
            400,
            30
        );

        // LEFT COLUMN - BASIC RULES

        showText(
            "THE FIGHT",
            200,
            75
        );

        showText(
            "3 ROUNDS",
            200,
            105
        );

        showText(
            "90 SECONDS PER ROUND",
            200,
            130
        );

        showText(
            "200 HEALTH",
            200,
            155
        );

        showText(
            "100 STAMINA",
            200,
            180
        );

        showText(
            "THE TIMER IS SHOWN",
            200,
            210
        );

        showText(
            "AT THE TOP OF THE FIGHT",
            200,
            230
        );

        // RIGHT COLUMN - CONTROLS

        showText(
            "CONTROLS",
            600,
            75
        );

        showText(
            "A / D  -  MOVE",
            600,
            105
        );

        showText(
            "J  -  JAB",
            600,
            130
        );

        showText(
            "K  -  HOOK",
            600,
            155
        );

        showText(
            "L  -  UPPERCUT",
            600,
            180
        );

        showText(
            "S  -  BLOCK",
            600,
            205
        );

        // ATTACKS

        showText(
            "ATTACKS",
            200,
            270
        );

        showText(
            "JAB",
            120,
            300
        );

        showText(
            "4 DAMAGE  |  10 STAMINA",
            280,
            300
        );

        showText(
            "HOOK",
            120,
            325
        );

        showText(
            "7 DAMAGE  |  20 STAMINA",
            280,
            325
        );

        showText(
            "UPPERCUT",
            135,
            350
        );

        showText(
            "10 DAMAGE  |  30 STAMINA",
            300,
            350
        );

        // BLOCKING

        showText(
            "BLOCKING",
            600,
            270
        );

        showText(
            "BLOCK REDUCES DAMAGE",
            600,
            300
        );

        showText(
            "BY 50%",
            600,
            325
        );

        showText(
            "BLOCK STAMINA LOSS:",
            600,
            350
        );

        showText(
            "JAB 5  |  HOOK 10  |  UPPERCUT 15",
            600,
            375
        );

        // KO RULE

        showText(
            "KNOCKOUT",
            400,
            405
        );

        showText(
            "HEALTH REACHING 0 FROM A PUNCH",
            400,
            425
        );

        // BACK BUTTON

        backButton = new BackButton();

        addObject(
            backButton,
            700,
            420
        );
    }
}