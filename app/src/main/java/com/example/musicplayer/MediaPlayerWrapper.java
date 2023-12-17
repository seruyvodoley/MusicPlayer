package com.example.musicplayer;

// MediaPlayerWrapper.java
import android.media.MediaPlayer;
import java.io.IOException;


/**
 * Класс MediaPlayerWrapper предоставляет реализацию интерфейса AudioPlayer,
 * используя внутренний объект MediaPlayer для управления воспроизведением аудио.
 */
public class MediaPlayerWrapper implements AudioPlayer {

    private MediaPlayer mediaPlayer;

    /**
     * Конструктор класса. Инициализирует MediaPlayer и подготавливает его для воспроизведения указанного аудиофайла.
     *
     * @param path Путь к аудиофайлу.
     */
    public MediaPlayerWrapper(String path) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Запускает воспроизведение аудио.
     */
    @Override
    public void play() {
        mediaPlayer.start();
    }

    /**
     * Приостанавливает воспроизведение аудио.
     */
    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    /**
     * Останавливает воспроизведение аудио и подготавливает MediaPlayer для
     * последующего использования.
     */
    @Override
    public void stop() {
        mediaPlayer.stop();
        mediaPlayer.prepareAsync();
    }

    /**
     * Проверяет, проигрывается ли в данный момент аудио.
     *
     * @return true, если аудио воспроизводится, в противном случае - false.
     */
    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    /**
     * Получает текущую позицию воспроизведения в миллисекундах.
     *
     * @return Текущая позиция воспроизведения.
     */
    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    /**
     * Получает длительность аудио в миллисекундах.
     *
     * @return Длительность аудио.
     */
    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    /**
     * Устанавливает позицию воспроизведения на указанное время в миллисекундах.
     *
     * @param position Время, на которое нужно перемотать воспроизведение.
     */
    @Override
    public void seekTo(int position) {
        mediaPlayer.seekTo(position);
    }

    /**
     * Устанавливает уровень громкости для левого и правого каналов звука.
     *
     * @param leftVolume  Уровень громкости для левого канала (от 0.0 до 1.0).
     * @param rightVolume Уровень громкости для правого канала (от 0.0 до 1.0).
     */
    @Override
    public void setVolume(float leftVolume, float rightVolume) {
        mediaPlayer.setVolume(leftVolume, rightVolume);
    }

    /**
     * Устанавливает уровень громкости для обоих каналов звука одновременно.
     *
     * @param volume Уровень громкости (от 0.0 до 1.0).
     */
    public void setVolume(float volume) {
        setVolume(volume, volume);
    }
}
