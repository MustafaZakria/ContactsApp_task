package com.zek.contactlistapp.presentation.contactDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zek.contactlistapp.data.local.ContactDb
import com.zek.contactlistapp.data.repository.ContactRepositoryImp
import com.zek.contactlistapp.databinding.FragmentContactDetailBinding

class ContactDetailFragment : Fragment() {
    private lateinit var binding: FragmentContactDetailBinding

    private val viewModel: ContactDetailViewModel by viewModels {
        ContactDetailViewModelFactory(
            ContactRepositoryImp(
                ContactDb.getDatabase(requireActivity().application).contactDao()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailBinding.inflate(inflater, container, false)

        val id = arguments?.getInt("contactId") ?: 0
        viewModel.getContact(id)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupToolbar()
    }

    private fun setupObservers() {
        viewModel.contact.observe(viewLifecycleOwner) {
            binding.contact = it
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}