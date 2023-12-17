package com.example.musicplayer;

// AudioPlayer.java
public interface AudioPlayer {
    void play();
    void pause();
    void stop();
    boolean isPlaying();
    int getCurrentPosition();
    int getDuration();
    void seekTo(int position);
    void setVolume(float leftVolume, float rightVolume);
}
