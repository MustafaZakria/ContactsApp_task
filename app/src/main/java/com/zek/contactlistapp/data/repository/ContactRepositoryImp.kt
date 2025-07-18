package com.zek.contactlistapp.data.repository

import androidx.lifecycle.LiveData
import com.zek.contactlistapp.data.local.ContactDao
import com.zek.contactlistapp.data.local.entities.Contact
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactRepositoryImp(
    private val contactDao: ContactDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ContactRepository {
    override fun getContactsBySearch(query: String): LiveData<List<Contact>> {
        return contactDao.getContactsBySearch(query)
    }

    override fun getAllContacts(): LiveData<List<Contact>> {
        return contactDao.getAllContacts()
    }

    override suspend fun insert(contact: Contact) = withContext(dispatcher) {
        contactDao.insert(contact)
    }

    override suspend fun delete(id: Int): Unit = withContext(dispatcher) {
        contactDao.delete(id)
    }

    override suspend fun getContactById(id: Int): Contact {
        return contactDao.getContactById(id)
    }
}