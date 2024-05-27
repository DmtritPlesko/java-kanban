package com.yandex.practicum.adapter;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class LocalDataTimeAdapter extends TypeAdapter<LocalDataTimeAdapter> {
    static DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public void write(JsonWriter jsonWriter, LocalDataTimeAdapter localDataTimeAdapter) throws IOException {

    }

    @Override
    public LocalDataTimeAdapter read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
