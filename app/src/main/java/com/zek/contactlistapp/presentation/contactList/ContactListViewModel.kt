package com.zek.contactlistapp.presentation.contactList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.zek.contactlistapp.data.local.entities.Contact
import com.zek.contactlistapp.data.repository.ContactRepository
import kotlinx.coroutines.launch

class ContactListViewModel(
    private val repository: ContactRepository
) : ViewModel() {

    private val _searchedValue = MutableLiveData("")

    val contacts: LiveData<List<Contact>> = _searchedValue.switchMap {
        when(it) {
            "" -> repository.getAllContacts()
            else -> repository.getContactsBySearch(it)
        }
    }

    fun deleteContact(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }

    fun insertContact(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
    }

    fun updateSearchValue(value: String) {
        _searchedValue.value = value
    }
}
class ContactViewModelFactory(
    private val repository: ContactRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}