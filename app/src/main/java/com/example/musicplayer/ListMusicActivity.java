package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ListView;
import java.util.ArrayList;
import android.Manifest;

/**
 * Активность, отображающая список музыкальных композиций, доступных на устройстве.
 * Приложение запрашивает разрешение на чтение аудиофайлов. Если разрешение предоставлено,
 * загружаются информация о музыкальных композициях, и их список отображается в виде списка.
 * Пользователь может выбрать композицию из списка, чтобы открыть экран для управления воспроизведением.
 */
public class  ListMusicActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION = 99;
    ArrayList<Song> songArrayList;
    ListView lvSongs;
    SongsAdapter songsAdapter;

    /**
     * Метод, вызываемый при создании активности.
     * Инициализирует представления, запрашивает разрешение, если оно не предоставлено,
     * или загружает список музыкальных композиций, если разрешение уже предоставлено.
     * Устанавливает слушатель для элементов списка, чтобы открыть MusicPlayer при выборе композиции.
     *
     * @param savedInstanceState Объект, содержащий данные о предыдущем состоянии активности.
     */
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_music);
        lvSongs = findViewById(R.id.lvSongs);
        songArrayList = new ArrayList<>();
        this.songsAdapter = new SongsAdapter(this, songArrayList);

        lvSongs.setAdapter(songsAdapter);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            } else {
                getSongs();
            }
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_AUDIO}, REQUEST_PERMISSION);
            } else {
                getSongs();
            }
        }

        lvSongs.setOnItemClickListener((parent, view, position, id) -> {
            Song song = songArrayList.get(position);
            Intent openMusicPlayer = new Intent(ListMusicActivity.this, MainActivity.class);
            openMusicPlayer.putExtra("song", song);
            startActivity(openMusicPlayer);
        });
    }

    /**
     * Метод, вызываемый при получении ответа на запрос разрешения от пользователя.
     * Если разрешение предоставлено, вызывает метод для загрузки музыкальных композиций.
     *
     * @param requestCode Код запроса разрешения.
     * @param permissions Массив запрошенных разрешений.
     * @param grantResults Результаты запроса разрешений.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getSongs();
            }
        }
    }

    /**
     * Метод для загрузки информации о музыкальных композициях с устройства и отображения их в списке.
     * Использует ContentResolver для получения данных из медиахранилища устройства.
     */
    private void getSongs() {
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);
        if (songCursor != null && songCursor.moveToFirst()) {
            int indexTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int indexArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int indexData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                String title = songCursor.getString(indexTitle);
                String artist = songCursor.getString(indexArtist);
                String path = songCursor.getString(indexData);

                songArrayList.add(new Song(title, artist, path));

            } while (songCursor.moveToNext());
        }
        songsAdapter.notifyDataSetChanged();
    }
}
