package com.example.rsasb.mycontacts;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Contact.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();

    private final static String NAME_DATABASE = "contact_db";

    //Static instance
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if(sInstance == null) {
            sInstance = Room.databaseBuilder(context, AppDatabase.class, NAME_DATABASE)
                    .allowMainThreadQueries().build();
        }

        return sInstance;
    }
}
