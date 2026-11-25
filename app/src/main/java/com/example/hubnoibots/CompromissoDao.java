package com.example.hubnoibots;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface CompromissoDao {
    @Insert
    long inserir(Compromisso compromisso);
    @Query("SELECT * FROM COMPROMISSO ORDER BY ID DESC")
    List<Compromisso> listarTodos();
    @Query("SELECT * from compromisso where id = :id LIMIT 1")
    Compromisso buscarPorId(long id);
    @Update
    int atualizar(Compromisso compromisso);
    @Delete
    int excluir(Compromisso compromisso);
}
