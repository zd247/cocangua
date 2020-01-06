package model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

public class Sound {
    public static boolean isMute; // Control sound's playability

    // List of sounds
    public static ArrayList<MediaPlayer> sounds;

    // Sound indexes in list, feel free to add more
    final public static int THEME = 0;
    final public static int PICK = 1;
    final public static int MOVE = 2;
    final public static int DEPLOY = 3;
    final public static int BLOCKED = 4;
    final public static int KICK = 5;
    final public static int ROLL = 6;
    final public static int WIN = 7;
    final public static int LOSE = 8;

    // Template for new MediaPlayer
    //public static MediaPlayer DUMMY = new MediaPlayer(new Media(new File("src/model/sounds/DUMMY.mp3").toURI().toString()));

    // Constructor : Perhaps add sounds to List
    Sound() {
        // Your default settings here if any
        for (MediaPlayer sound: sounds) {
            //sound.setOnEndOfMedia();
            //sound.setOnStopped();
        }
    }

    // Template play sound function (mute handled)
    public static void playSound(int soundID) {
        MediaPlayer sfx = sounds.get(soundID);
        if (!isMute) {
            // First, reset the sound to its beginning
            sfx.seek(Duration.ZERO);
            // Then play
            sfx.play();
        }

        /*
        THEME NEEDS SPECIAL HANDLER
        BECAUSE IT IS THE ONLY SOUND LONG ENOUGH
        TO NOT BE RESET WHENEVER WE PLAY IT
        BUT INSTEAD RESUMED, PAUSED, AND RESET ONLY WHEN setOnEndMedia()
        WILL GET TO THAT LATER
         */
    }

}
