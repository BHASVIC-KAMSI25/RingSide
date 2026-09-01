# Ringside

Ringside is a 1v1 boxing game developed in Java using Greenfoot.

The player fights an AI-controlled opponent across a maximum of three 90-second rounds. The game features animated combat, health and stamina management, blocking, knockback, knockout animations, sound effects, and a complete menu and match flow.

## Features

* 1v1 player vs AI boxing
* Player name entry
* AI-controlled opponent
* Three 90-second rounds
* Health system with 200 maximum health
* Stamina system with 100 maximum stamina
* Jab, hook, and uppercut attacks
* Different damage and stamina costs for each attack
* Blocking with reduced incoming damage
* Stamina regeneration
* Knockback mechanics
* Knockout detection
* Animated knockout sequences
* Round countdown system
* Fight timer
* Decision system if all three rounds are completed
* Background music and sound effects
* Animated fighter sprites
* Fight introduction screen
* Help and controls screen
* Victory and defeat screens
* Menu navigation

## Combat System

### Attacks

| Attack   | Damage | Stamina Cost |
| -------- | -----: | -----------: |
| Jab      |      4 |           10 |
| Hook     |      7 |           20 |
| Uppercut |     10 |           30 |

### Blocking

Blocking reduces incoming damage but consumes stamina when an attack is blocked.

Different attacks cause different amounts of stamina damage and knockback while blocking.

| Attack   | Block Stamina Damage | Block Knockback |
| -------- | -------------------: | --------------: |
| Jab      |                    5 |               6 |
| Hook     |                   10 |              10 |
| Uppercut |                   15 |              14 |

### Knockouts

A fighter is knocked out when their health reaches 0.

The losing fighter then plays a four-frame knockout animation. Once the animation finishes and a short delay has passed, the game transitions to the appropriate victory or defeat screen.

## Match System

Each match consists of a maximum of three rounds.

* Round length: 90 seconds
* Number of rounds: 3
* Maximum health: 200
* Maximum stamina: 100
* Knockout: Immediately ends the match
* No knockout: Winner is determined by the round results after Round 3

## Controls

### Movement

| Key                 | Action     |
| ------------------- | ---------- |
| `A` / `Left Arrow`  | Move left  |
| `D` / `Right Arrow` | Move right |

### Combat

| Key     | Action   |
| ------- | -------- |
| `W`     | Jab      |
| `S`     | Hook     |
| `E`     | Uppercut |
| `Space` | Block    |



## Game Screens

Ringside includes several screens to create a complete game experience:

* Home Screen
* Name Entry Screen
* Help Screen
* Fight Introduction Screen
* Boxing Match
* Victory Screen
* Defeat Screen

## Audio

The game includes:

* Background music
* Jab sound effect
* Hook sound effect
* Uppercut sound effect
* Block sound effect
* Knockout sound effect
* Boxing bell sound effect

## Project Structure

The main classes in the project are:

| Class              | Purpose                                                           |
| ------------------ | ----------------------------------------------------------------- |
| `BoxingWorld`      | Controls the match, rounds, timer, knockout system, and game flow |
| `Boxer`            | Base class containing shared fighter mechanics                    |
| `Player`           | Handles player-controlled behaviour                               |
| `Enemy`            | Handles AI-controlled behaviour                                   |
| `HealthBar`        | Displays fighter health                                           |
| `StaminaBar`       | Displays fighter stamina                                          |
| `RoundDisplay`     | Displays round and timer information                              |
| `GameSound`        | Handles background music and sound effects                        |
| `HomeScreen`       | Main menu                                                         |
| `HelpScreen`       | Displays game instructions                                        |
| `FightIntroScreen` | Introduces the fight                                              |
| `WinScreen`        | Displays the player's victory                                     |
| `LoseScreen`       | Displays the player's defeat                                      |
| `BackButton`       | Handles screen navigation                                         |

## Technologies

* Java
* Greenfoot
* Object-oriented programming
* Inheritance
* Sprite-based animation
* Game-state management
* AI behaviour
* User input
* Audio integration
* Graphical user interface design

## Running the Game

### Requirements

* [Greenfoot](https://www.greenfoot.org/)

### Instructions

1. Clone or download this repository.
2. Open the project in Greenfoot.
3. Open `project.greenfoot`.
4. Click **Run**.
5. Start the game from the home screen.

All required game assets, including sprites, backgrounds, buttons, and audio files, are included in the repository.

## Screenshots

Screenshots of the finished game will be added here.

## Project Purpose

Ringside was developed as a personal programming project to gain practical experience with Java, object-oriented programming, game development, and software design.

The project provided experience with:

* Designing a complete game structure
* Managing multiple game states
* Creating reusable classes
* Applying inheritance
* Implementing combat mechanics
* Creating sprite animation systems
* Programming AI behaviour
* Handling keyboard input
* Integrating audio
* Designing graphical interfaces
* Debugging and testing
* Structuring a multi-class Java project

## Author

**Kamsi Ndubuisi**

This project was developed as part of my personal software and tech development portfolio.
