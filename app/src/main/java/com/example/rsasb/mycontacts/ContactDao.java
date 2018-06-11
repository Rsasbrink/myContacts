package com.example.rsasb.mycontacts;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Query("SELECT * FROM contact WHERE uid = :id")
    Contact findById(long id);

    @Query("SELECT * FROM contact WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    Contact findByName(String first, String last);

    @Update
    void update(Contact... contacts);

    @Insert
    long insert(Contact contact);

    @Delete
    void delete(Contact contact);
}
