package model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Sound {
    public static boolean isMute; // Control sound's playability

    // List of sounds
    public static ArrayList<MediaPlayer> sounds;

    // Sound indexes in list, feel free to add more
    final public static int THEME = 0;
    final public static int MOVE = 1;
    final public static int DEPLOY = 2;
    final public static int BLOCKED = 3;
    final public static int KICK = 4;
    final public static int HOME = 5;
    final public static int ROLL = 6;
    final public static int WIN = 7;

    // Template for new MediaPlayer
    final private static MediaPlayer THEME_AUDIO = new MediaPlayer(new Media(new File("src/Audio/theme.mp3").toURI().toString()));
    final private static MediaPlayer MOVE_AUDIO = new MediaPlayer(new Media(new File("src/Audio/move.wav").toURI().toString()));
    final private static MediaPlayer DEPLOY_AUDIO = new MediaPlayer(new Media(new File("src/Audio/deploy.wav").toURI().toString()));
    final private static MediaPlayer BLOCK_AUDIO = new MediaPlayer(new Media(new File("src/Audio/block.wav").toURI().toString()));
    final private static MediaPlayer KICK_AUDIO = new MediaPlayer(new Media(new File("src/Audio/kick.wav").toURI().toString()));
    final private static MediaPlayer HOME_AUDIO = new MediaPlayer(new Media(new File("src/Audio/home.wav").toURI().toString()));
    final private static MediaPlayer ROLL_AUDIO = new MediaPlayer(new Media(new File("src/Audio/roll.wav").toURI().toString()));
    final private static MediaPlayer WIN_AUDIO = new MediaPlayer(new Media(new File("src/Audio/win.mp3").toURI().toString()));



    // Constructor : Perhaps add sounds to List
    Sound() {
        sounds.addAll(List.of(THEME_AUDIO, MOVE_AUDIO, DEPLOY_AUDIO, BLOCK_AUDIO, KICK_AUDIO, HOME_AUDIO, ROLL_AUDIO, WIN_AUDIO));
        // Your default settings here if any
        sounds.get(0).setCycleCount(MediaPlayer.INDEFINITE); //repeat the theme audio
        isMute = false;
    }

    // Template play sound function (mute handled)
    public static void playSound(int soundID) {
        MediaPlayer sfx = sounds.get(soundID);
        if (!isMute) {
            if (sounds.get(soundID) != THEME_AUDIO) { //reset if it is not theme audio
                // First, reset the sound to its beginning
                sfx.seek(Duration.ZERO);
                // Then play
            }
            sfx.play();
        } else {
            if (sounds.get(soundID) == THEME_AUDIO) { //pause if it is theme audio
                sfx.pause();
            }
        }
    }

}
