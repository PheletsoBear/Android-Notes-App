package com.example.to_dolist.view

import NotesAdapter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.to_dolist.R
import com.example.to_dolist.constants.SortType
import com.example.to_dolist.viewModel.ViewNoteViewModel
import com.example.to_dolist.databinding.FragmentNoteListBinding
import com.example.to_dolist.util.SwipeToDeleteHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.title = "Notes"
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

        val navHostFragment = activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

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

        val swipeToDeleteHelper = SwipeToDeleteHelper(
            binding.notesListRV,
            notesAdapter,
            viewModel,
            requireContext()
        )
        swipeToDeleteHelper.attach()
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
}
