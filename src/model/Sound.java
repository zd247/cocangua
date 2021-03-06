/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2020A
  Assessment: Final Project
  Created date: 20/12/2019

  By:
  Phan Quoc Binh (3715271)
  Tran Mach So Han (3750789)
  Tran Kim Bao (3740819)
  Nguyen Huu Duy (3703336)
  Nguyen Minh Trang (3751450)

  Last modified: 14/1/2019

  By:
  Nguyen Huu Duy (3703336)

  Acknowledgement: see readme.md
*/

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
    public static MediaPlayer THEME_AUDIO = new MediaPlayer(new Media(new File("data/audio/theme.mp3").toURI().toString()));
    public static MediaPlayer MOVE_AUDIO = new MediaPlayer(new Media(new File("data/audio/move.mp3").toURI().toString()));
    public static MediaPlayer DEPLOY_AUDIO = new MediaPlayer(new Media(new File("data/audio/deploy.wav").toURI().toString()));
    public static MediaPlayer BLOCK_AUDIO = new MediaPlayer(new Media(new File("data/audio/block.wav").toURI().toString()));
    public static MediaPlayer KICK_AUDIO = new MediaPlayer(new Media(new File("data/audio/kick.mp3").toURI().toString()));
    public static MediaPlayer HOME_AUDIO = new MediaPlayer(new Media(new File("data/audio/home.wav").toURI().toString()));
    public static MediaPlayer ROLL_AUDIO = new MediaPlayer(new Media(new File("data/audio/roll.wav").toURI().toString()));
    public static MediaPlayer WIN_AUDIO = new MediaPlayer(new Media(new File("data/audio/win.mp3").toURI().toString()));

    // Constructor : Perhaps add sounds to List
    public Sound() {
        isMute = false;
    }

    // Template play sound function (mute handled)
    public static void playSound(int soundID) {
        MediaPlayer sfx = null;

        THEME_AUDIO.setCycleCount(MediaPlayer.INDEFINITE); //Only theme audio repeats

        switch (soundID) {
            case THEME: sfx = THEME_AUDIO; break;
            case MOVE: sfx = MOVE_AUDIO; break;
            case DEPLOY: sfx = DEPLOY_AUDIO; break;
            case BLOCKED: sfx = BLOCK_AUDIO; break;
            case KICK: sfx = KICK_AUDIO; break;
            case HOME: sfx = HOME_AUDIO; break;
            case ROLL: sfx = ROLL_AUDIO; break;
            case WIN: sfx = WIN_AUDIO; break;
        }

        if (!isMute) {
            assert sfx != null;
            if (sfx != THEME_AUDIO) {
                sfx.seek(Duration.ZERO);
            }
            sfx.play();
        } else {
            if (sfx == THEME_AUDIO) { //pause for theme audio and stop for other audios
                sfx.pause();
            } else {
                sfx.stop();
            }
        }

    }

}
