package com.zek.contactlistapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zek.contactlistapp.data.local.entities.Contact

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: Contact)

    @Query("DELETE FROM contacts WHERE id == :id")
    suspend fun delete(id: Int): Void

    @Query("SELECT * FROM contacts WHERE name LIKE '%' || :query || '%' COLLATE NOCASE OR phone LIKE '%' || :query || '%' COLLATE NOCASE ORDER BY name ASC")
    fun getContactsBySearch(query: String): LiveData<List<Contact>>

    @Query("SELECT * FROM contacts ORDER BY name ASC")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contacts WHERE id == :id")
    suspend fun getContactById(id: Int): Contact
}
