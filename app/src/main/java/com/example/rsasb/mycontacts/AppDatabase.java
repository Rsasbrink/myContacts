package com.example.rsasb.mycontacts;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

@Database(entities = {Contact.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();

    private final static String NAME_DATABASE = "contact_db";


    static final Migration MIGRATION_3_4 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
// Since we didn't alter the table, there's nothing else to do here.
        }
    };




    //Static instance
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if(sInstance == null) {
            sInstance = Room.databaseBuilder(context, AppDatabase.class, NAME_DATABASE)
                    .addMigrations(MIGRATION_3_4) .allowMainThreadQueries().build();
        }

        return sInstance;
    }
}
