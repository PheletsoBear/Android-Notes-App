package com.example.to_dolist

import NotesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_dolist.databinding.FragmentNoteListBinding

class NoteListFragment : Fragment(){

    private lateinit var binding: FragmentNoteListBinding
    private lateinit var notesAdapter: NotesAdapter

    // Mock data
    private val mockNotes = listOf(
        Notes(title = "Study Kotlin", content = "This is a description that will be limited to a certain three number of lines. If the text exceeds the maximum lines which is three in our case, an ellipsis will be added at the end."),
        Notes(title = "Walk the Dog", content = "Take Max for a walk around the park"),
        Notes(title = "Study Kotlin test", content = "This is a description that will be limited to a certain three number of lines test cases. If the text exceeds the maximum lines which is three in our case, an ellipsis will be added at the end."),
        Notes(title = "Festive declaration", content = "Take Max for a walk around the park,because it is festive and I am so anxious about coding")
    )

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

        // Initialize the adapter with mock data
        notesAdapter = NotesAdapter(requireContext(), mockNotes.toMutableList())
        binding.notesListRV.layoutManager = LinearLayoutManager(requireContext())
        binding.notesListRV.adapter = notesAdapter
    }
}