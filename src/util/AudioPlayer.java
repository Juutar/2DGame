package util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class AudioPlayer {
    public enum Effect {
        FIRE,
        FALL,
        CHEST,
        DOOR,
        BUTTON,
    }

    private static AudioInputStream getAudio(String track) {
        File audio = new File("res/audio/" + track + ".wav");
        try {
            return AudioSystem.getAudioInputStream(audio);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void playAudio(String track, boolean loop) {
        AudioInputStream audio = getAudio(track);
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playSoundtrack() { playAudio("soundtrack", true); }

    public static void playSoundEffect(Effect effect) {
        switch (effect) {
            case BUTTON -> playAudio("Button", false);
            case CHEST -> playAudio("Chest", false);
            case FIRE -> playAudio("Fire", false);
            case FALL -> playAudio("Falling", false);
            case DOOR -> playAudio("Door", false);
        }
    }
}
