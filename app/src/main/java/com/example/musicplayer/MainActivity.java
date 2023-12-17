package com.example.musicplayer;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
/**
 * Главная активность приложения, предоставляющая пользовательский интерфейс для управления воспроизведением музыки.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Объявление представлений
    TextView tvTime, tvDuration, tvTitle, tvArtist;
    SeekBar seekBarTime, seekBarVolume;
    Button btnPlay;

    // Объект для управления воспроизведением аудио
    private AudioPlayer audioPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Скрытие ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Получение объекта песни из переданных данных
        Song song = (Song) getIntent().getSerializableExtra("song");

        // Инициализация представлений
        tvTime = findViewById(R.id.tvTime);
        tvTitle = findViewById(R.id.tvTitle);
        tvArtist = findViewById(R.id.tvArtist);
        tvDuration = findViewById(R.id.tvDuration);
        seekBarTime = findViewById(R.id.seekBarTime);
        seekBarVolume = findViewById(R.id.seekBarVolume);
        btnPlay = findViewById(R.id.btnPlay);

        // Установка информации о песне на экране
        tvTitle.setText(song.getTitle());
        tvArtist.setText(song.getArtist());

        // Создание объекта для управления воспроизведением аудио
        audioPlayer = new MediaPlayerWrapper(song.getPath());

        // Установка длительности аудио на экран
        String duration = millisecondsToString(audioPlayer.getDuration());
        tvDuration.setText(duration);

        // Установка слушателя на кнопку воспроизведения/паузы
        btnPlay.setOnClickListener(this);

        // Установка слушателя на изменение громкости
        seekBarVolume.setProgress(50);
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
                float volume = progress / 100f;
                audioPlayer.setVolume(volume, volume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Установка слушателя на перемотку времени
        seekBarTime.setMax(audioPlayer.getDuration());
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean isFromUser) {
                if (isFromUser) {
                    audioPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Запуск отдельного потока для обновления времени воспроизведения
        new Thread(() -> {
            while (audioPlayer != null) {
                if (audioPlayer.isPlaying()) {
                    try {
                        final double current = audioPlayer.getCurrentPosition();
                        final String elapsedTime = millisecondsToString((int) current);

                        // Обновление времени на UI потоке
                        runOnUiThread(() -> {
                            tvTime.setText(elapsedTime);
                            seekBarTime.setProgress((int) current);
                        });

                        // Пауза потока на 1 секунду
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * Преобразует время в миллисекундах в формат "минуты:секунды".
     *
     * @param time Время в миллисекундах.
     * @return Строка с форматированным временем.
     */
    public String millisecondsToString(int time) {
        String elapsedTime = "";
        int minutes = time / 1000 / 60;
        int seconds = time / 1000 % 60;
        elapsedTime = minutes + ":";
        if (seconds < 10) {
            elapsedTime += "0";
        }
        elapsedTime += seconds;

        return elapsedTime;
    }

    /**
     * Обработчик нажатия на кнопку воспроизведения/паузы.
     *
     * @param view Нажатая кнопка.
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnPlay) {
            if (audioPlayer.isPlaying()) {
                audioPlayer.pause();
                btnPlay.setBackgroundResource(R.drawable.img_3);
            } else {
                audioPlayer.play();
                btnPlay.setBackgroundResource(R.drawable.img_6);
            }
        }
    }

    /**
     * Обработчик нажатия на элемент ActionBar.
     *
     * @param item Нажатый элемент меню.
     * @return true, если обработка выполнена успешно, в противном случае - false.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            if (audioPlayer.isPlaying()) {
                audioPlayer.stop();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}

