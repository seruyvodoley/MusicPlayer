package com.example.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Адаптер для связывания данных музыкальных композиций с пользовательским интерфейсом списка.
 * Используется для отображения списка музыкальных композиций в пользовательском интерфейсе приложения.
 */
public class SongsAdapter extends ArrayAdapter<Song> {

    /**
     * Конструктор адаптера.
     *
     * @param context Контекст приложения.
     * @param objects Список объектов Song, который будет отображаться в списке.
     */
    public SongsAdapter(@NonNull Context context, @NonNull List<Song> objects) {
        super(context, 0, objects);
    }

    /**
     * Получает представление элемента списка и заполняет его данными о музыкальной композиции.
     *
     * @param position    Позиция элемента в списке.
     * @param convertView Представление элемента списка, которое может быть переиспользовано.
     * @param parent      Родительский контейнер элемента списка.
     * @return Представление элемента списка с заполненными данными.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Инфлейтим макет элемента списка
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, null);

        // Находим элементы интерфейса в макете
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvArtist = convertView.findViewById(R.id.tvArtist);

        // Получаем объект Song из списка
        Song song = getItem(position);

        // Устанавливаем заголовок и исполнителя музыкальной композиции в соответствующие TextView
        tvTitle.setText(song.getTitle());
        tvArtist.setText(song.getArtist());

        return convertView;
    }
}
