package com.example.musicplayer;

import java.io.Serializable;

/**
 * Класс, представляющий музыкальную композицию. Реализует интерфейс Serializable для передачи
 * экземпляров между компонентами Android через Intent.
 */
public class Song implements Serializable {
    private String title;
    private String artist;
    private String path;

    /**
     * Конструктор класса. Инициализирует объект Song с указанным заголовком, исполнителем и путем к файлу.
     *
     * @param title  Заголовок музыкальной композиции.
     * @param artist Исполнитель музыкальной композиции.
     * @param path   Путь к файлу музыкальной композиции.
     */
    public Song(String title, String artist, String path) {
        this.title = title;
        this.artist = artist;
        this.path = path;
    }

    /**
     * Получает заголовок музыкальной композиции.
     *
     * @return Заголовок музыкальной композиции.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Устанавливает заголовок музыкальной композиции.
     *
     * @param title Новый заголовок музыкальной композиции.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Получает исполнителя музыкальной композиции.
     *
     * @return Исполнитель музыкальной композиции.
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Устанавливает исполнителя музыкальной композиции.
     *
     * @param artist Новый исполнитель музыкальной композиции.
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Получает путь к файлу музыкальной композиции.
     *
     * @return Путь к файлу музыкальной композиции.
     */
    public String getPath() {
        return path;
    }

    /**
     * Устанавливает путь к файлу музыкальной композиции.
     *
     * @param path Новый путь к файлу музыкальной композиции.
     */
    public void setPath(String path) {
        this.path = path;
    }
}
