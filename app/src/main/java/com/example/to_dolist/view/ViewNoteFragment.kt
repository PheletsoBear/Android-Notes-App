package com.example.to_dolist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.to_dolist.databinding.FragmentViewNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewNoteFragment: Fragment() {

private lateinit var binding: FragmentViewNoteBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.title = "My Note"
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        binding.editTextHeading.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                binding.editTextSubHeader.requestFocus()
                return@setOnKeyListener true
            }
            false
        }

        binding.editTextSubHeader.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Case 1: Entire field is empty
                if (s.isNullOrEmpty()) {
                    binding.editTextHeading.requestFocus()
                    binding.editTextHeading.setSelection(binding.editTextHeading.text?.length ?: 0)
                }
                // Case 2: User deleted first character only (start at 0 and before > 0)
                else if (start == 0 && before > 0) {
                    binding.editTextHeading.requestFocus()
                    binding.editTextHeading.setSelection(binding.editTextHeading.text?.length ?: 0)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.view_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home ->{
                requireActivity().onBackPressed()
                return true
            }
              else -> return super.onOptionsItemSelected(item)
        }
    }

}