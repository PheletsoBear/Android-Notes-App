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
           mutableListOf(), requireContext(),
            navController = navController
        )
        binding.notesListRV.layoutManager = LinearLayoutManager(requireContext())
        binding.notesListRV.adapter = notesAdapter

        viewModel.allNotes.observe(viewLifecycleOwner){ notes ->
            if (notes != null) {
                notesAdapter.updateNotes(notes)
            }
        }


    val itemTouchHelperCallback = object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val removedNote = notesAdapter.notes[position]

            notesAdapter.notes.removeAt(position)
            notesAdapter.notifyItemRemoved(position)

            viewModel.delete(removedNote)

            Snackbar.make(binding.root, "Note deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo") {
                    notesAdapter.notes.add(position, removedNote)
                    notesAdapter.notifyItemInserted(position)
                    viewModel.insert(removedNote)
                }.show()
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                val itemView = viewHolder.itemView
                val paint = Paint()
                val icon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete_sweep)
                val iconMargin = (itemView.height - icon!!.intrinsicHeight) / 2

                if (dX < 0) { // Swiping left
                    paint.color = Color.RED
                    val background = RectF(
                        itemView.right.toFloat() + dX,
                        itemView.top.toFloat() + 35f,
                        itemView.right.toFloat(),
                        itemView.bottom.toFloat() - 8f
                    )
                    c.drawRect(background, paint)

                    val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                    val iconRight = itemView.right - iconMargin
                    val iconTop = itemView.top + iconMargin
                    val iconBottom = iconTop + icon.intrinsicHeight
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    icon.draw(c)
                }
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }
    ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.notesListRV)
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
