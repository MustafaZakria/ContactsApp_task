package com.zek.contactlistapp.presentation.contactList

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zek.contactlistapp.R
import com.zek.contactlistapp.data.local.ContactDb
import com.zek.contactlistapp.data.local.entities.Contact
import com.zek.contactlistapp.data.repository.ContactRepositoryImp
import com.zek.contactlistapp.databinding.FragmentContactListBinding

class ContactsListFragment : Fragment() {

    private lateinit var binding: FragmentContactListBinding
    private val viewModel: ContactListViewModel by viewModels(
        factoryProducer = {
            ContactViewModelFactory(
                ContactRepositoryImp(
                    ContactDb.getDatabase(requireActivity().application).contactDao()
                )
            )
        }
    )
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchView()
        setupFloatingActionButton()
        observeContacts()
    }

    private fun setupRecyclerView() {
        contactAdapter = ContactAdapter(
            onItemClick = { id ->
                navigateToContactDetails(id)
            },
            onItemSwipe = { id ->
                viewModel.deleteContact(id)
            }
        )

        binding.recyclerViewContacts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = contactAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        contactAdapter.setupSwipeToDelete(binding.recyclerViewContacts)
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String) = false

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.updateSearchValue(newText)
                return true
            }
        })
    }

    private fun setupFloatingActionButton() {
        binding.fabAddContact.setOnClickListener {
            showAddContactDialog()
        }
    }

    private fun showAddContactDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_contact, null)
        val editName = dialogView.findViewById<EditText>(R.id.et_name)
        val editPhone = dialogView.findViewById<EditText>(R.id.et_phone)

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.add_contact))
            .setView(dialogView)
            .setPositiveButton(getString(R.string.save)) { _, _ ->
                val name = editName.text.toString().trim()
                val phone = editPhone.text.toString().trim()

                if (name.isNotEmpty() && phone.isNotEmpty()) {
                    viewModel.insertContact(Contact(name = name, phone = phone))
                } else {
                    Toast.makeText(context, getString(R.string.fill_fields), Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .create()
            .show()
    }

    private fun observeContacts() {
        viewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            contactAdapter.submitList(contacts)
            binding.emptyState.visibility = if (contacts.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun navigateToContactDetails(id: Int) {
        val action = ContactsListFragmentDirections
            .actionContactsListFragmentToContactDetailFragment(id)
        findNavController().navigate(action)
    }

}