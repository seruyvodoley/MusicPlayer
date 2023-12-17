package com.example.musicplayer;

// MediaPlayerWrapper.java
import android.media.MediaPlayer;
import java.io.IOException;


public class MediaPlayerWrapper implements AudioPlayer {

    private MediaPlayer mediaPlayer;

    public MediaPlayerWrapper(String path) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void play() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public void stop() {
        mediaPlayer.stop();
        mediaPlayer.prepareAsync();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public void seekTo(int position) {
        mediaPlayer.seekTo(position);
    }

    @Override
    public void setVolume(float leftVolume, float rightVolume) {
        mediaPlayer.setVolume(leftVolume, rightVolume);
    }

    // Добавленный метод для установки громкости для обоих каналов отдельно
    public void setVolume(float volume) {
        setVolume(volume, volume);
    }
}
