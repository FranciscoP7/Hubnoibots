package com.example.hubnoibots;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(
        entities = {com.example.hubnoibots.Compromisso.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDataBase  extends RoomDatabase {
    private static volatile AppDataBase INSTANCIA;
    public abstract com.example.hubnoibots.CompromissoDao compromissoDao();
    public static AppDataBase getDatabase(Context context){
        if(INSTANCIA == null){
            synchronized (AppDataBase.class){
                if(INSTANCIA == null){
                    INSTANCIA = Room.databaseBuilder(
                            context.getApplicationContext(), AppDataBase.class,
                            "hubnoibots.db"
                    ).build();
                }
            }
        }return INSTANCIA;
    }
}
