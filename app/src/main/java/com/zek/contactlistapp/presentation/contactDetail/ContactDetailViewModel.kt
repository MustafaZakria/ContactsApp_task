package com.zek.contactlistapp.presentation.contactDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.zek.contactlistapp.data.local.entities.Contact
import com.zek.contactlistapp.data.repository.ContactRepository
import com.zek.contactlistapp.presentation.contactList.ContactListViewModel
import kotlinx.coroutines.launch

class ContactDetailViewModel(
    private val repository: ContactRepository
): ViewModel() {
    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact> = _contact


    fun getContact(id: Int) = viewModelScope.launch {
        _contact.value = repository.getContactById(id)
    }
}

class ContactDetailViewModelFactory(
    private val repository: ContactRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}