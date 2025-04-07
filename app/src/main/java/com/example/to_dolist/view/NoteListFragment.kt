package com.example.to_dolist

import NotesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_dolist.databinding.FragmentNoteListBinding
import kotlin.getValue

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
           mutableListOf(), requireContext(),
            navController = navController
        )
        binding.notesListRV.layoutManager = LinearLayoutManager(requireContext())
        binding.notesListRV.adapter = notesAdapter

        viewModel. getAllNotes().observe(viewLifecycleOwner){ notes ->
            notesAdapter.updateNotes(notes)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.note_list_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.sort_icon ->{
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}