package com.example.to_dolist.view

import NotesAdapter
import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_dolist.R
import com.example.to_dolist.constants.SortType
import com.example.to_dolist.databinding.FragmentNoteListBinding
import com.example.to_dolist.util.SwipeToDeleteHelper
import com.example.to_dolist.viewModel.ViewNoteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListFragment : Fragment(){

    private lateinit var binding: FragmentNoteListBinding
    private lateinit var notesAdapter: NotesAdapter
    private val viewModel: ViewNoteViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.title = "Notes"
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

        val navHostFragment =
            activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        setAdapter(navController)

        val swipeToDeleteHelper = SwipeToDeleteHelper(
            binding.notesListRV,
            notesAdapter,
            viewModel,
            requireContext()
        )
        swipeToDeleteHelper.attach()
        clearFocus()
        hideKeyboardAndClearFocus(binding.searchBar)
        onSearchNote()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.note_list_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.sort_icon ->{
                showSortBottomSheet()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun setAdapter(navController: NavController){

        notesAdapter = NotesAdapter(
            mutableListOf(),
            requireContext(),
            navController
        )
        binding.notesListRV.layoutManager = LinearLayoutManager(requireContext())
        binding.notesListRV.adapter = notesAdapter

        viewModel.allNotes.observe(viewLifecycleOwner){ notes ->
            if (notes != null) {
                notesAdapter.updateNotes(notes)
            }
        }
    }


    private fun showSortBottomSheet() {

        val bindingBottomSheet = com.example.to_dolist.databinding.BottomSheetSortBinding.inflate(layoutInflater)
        val bottomSheetDialog = BottomSheetDialog(requireContext())

        bottomSheetDialog.setContentView(bindingBottomSheet.root)

        bindingBottomSheet.sortAz.setOnClickListener {
            viewModel.fetchSortedNotes(SortType.A_Z)
            bottomSheetDialog.dismiss()
            }

        bindingBottomSheet.sortZa.setOnClickListener {
            viewModel.fetchSortedNotes(SortType.Z_A)
            bottomSheetDialog.dismiss()
        }

        bindingBottomSheet.sortDateAsc.setOnClickListener {
            viewModel.fetchSortedNotes(SortType.DATE_ASC)
            bottomSheetDialog.dismiss()
        }

        bindingBottomSheet.sortDateDesc.setOnClickListener {
            viewModel.fetchSortedNotes(SortType.DATE_DESC)
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    fun onSearchNote(){

        binding.searchBar.doOnTextChanged { text, _, _, _ ->

            val query = text?.toString() ?: ""
            if (query.isNotBlank() && binding.searchBar.isFocused ) {

                viewModel.searchQuery(query)
                viewModel.filteredNotes.observe(viewLifecycleOwner) { notes ->
                   notes?.let {
                        notesAdapter.updateNotes(notes)
                    }
                }
            } else {

                viewModel.allNotes.observe(viewLifecycleOwner) { notes ->
                    notes?.let {
                        notesAdapter.updateNotes(notes)
                    }
                }
            }
        }
    }

    private fun hideKeyboardAndClearFocus(view: View) {
        val imm = view.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun clearFocus(){

        binding.nestedScrollView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.searchBar.windowToken, 0)
                binding.searchBar.clearFocus()
            }
            false
        }
    }
}
