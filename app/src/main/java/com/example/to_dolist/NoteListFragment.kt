package com.example.to_dolist

import NotesAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
        Notes(
            title = "Study Kotlin",
            content = "This is a description that will be limited to a certain three number of lines. If the text exceeds the maximum lines which is three in our case, an ellipsis will be added at the end.",
            date = "2024/12/18"
        ),
        Notes(title = "Festive declaration",
            content = "Take Max for a walk around the park,because it is festive and I am so anxious about coding",
            date = "2023/03/30"),
        Notes(title = "Walk the Dog",
            content = "Take Max for a walk around the park",
            date = "2022/03/03"),
        Notes(title = "Festive declaration",
            content = "Take Max for a walk around the park,because it is festive and I am so anxious about coding",
            date = "2004/05/06"),
        Notes(title = "Walk the Dog",
            content = "Take Max for a walk around the park",
            date = "2015/06/04"),
        Notes(title = "Walk the Dog",
            content = "Take Max for a walk around the park",
            date = "2019/07/18"),
        Notes(title = "Walk the Dog",
            content = "Take Max for a walk around the park",
            date = "2016/06/009"),
        Notes(title = "Study Kotlin test",
            content = "This is a description that will be limited to a certain three number of lines test cases. If the text exceeds the maximum lines which is three in our case, an ellipsis will be added at the end.",
            date = "2023/04/06"),
        Notes(title = "Walk the Dog",
            content = "Take Max for a walk around the park",
            date = "2005/06/01"),
        Notes(title = "Festive declaration",
            content = "Take Max for a walk around the park,because it is festive and I am so anxious about coding",
            date = "2009/06/08"),
        Notes(title = "Walk the Dog",
            content = "Take Max for a walk around the park",
            date = "2013/09/05"),
        Notes(title = "Study Kotlin test",
            content = "This is a description that will be limited to a certain three number of lines test cases. If the text exceeds the maximum lines which is three in our case, an ellipsis will be added at the end.",
            date = "2016/08/08"),
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
        //activity.supportActionBar?.setDisplayHomeAsUpEnabled(true) //back button
        setHasOptionsMenu(true)

        notesAdapter = NotesAdapter(mockNotes.toMutableList(), requireContext())
        binding.notesListRV.layoutManager = LinearLayoutManager(requireContext())
        binding.notesListRV.adapter = notesAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
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