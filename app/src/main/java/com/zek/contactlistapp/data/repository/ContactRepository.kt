package com.zek.contactlistapp.data.repository

import androidx.lifecycle.LiveData
import com.zek.contactlistapp.data.local.entities.Contact

interface ContactRepository {
    fun getContactsBySearch(query: String): LiveData<List<Contact>>
    fun getAllContacts(): LiveData<List<Contact>>
    suspend fun insert(contact: Contact)
    suspend fun delete(id: Int)
    suspend fun getContactById(id: Int): Contact?
}