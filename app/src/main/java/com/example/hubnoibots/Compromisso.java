package com.example.hubnoibots;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "compromisso")
public class Compromisso {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String titulo;
    @NonNull
    private String data;
    @NonNull
    private String horario;
    private String descricao;

    public Compromisso(long id, @NonNull String titulo, @NonNull String data, @NonNull String horario, String descricao) {
        this.id = id;
        this.titulo = titulo;
        this.data = data;
        this.horario = horario;
        this.descricao = descricao;
    }

    public Compromisso() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(@NonNull String titulo) {
        this.titulo = titulo;
    }

    @NonNull
    public String getData() {
        return data;
    }

    public void setData(@NonNull String data) {
        this.data = data;
    }

    @NonNull
    public String getHorario() {
        return horario;
    }

    public void setHorario(@NonNull String horario) {
        this.horario = horario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
