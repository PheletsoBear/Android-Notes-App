package com.example.to_dolist.util

import NotesAdapter
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.to_dolist.R
import com.example.to_dolist.constants.SortType
import com.example.to_dolist.viewModel.ViewNoteViewModel
import com.google.android.material.snackbar.Snackbar


class SwipeToDeleteHelper (
    private val recyclerView: RecyclerView,
    private val notesAdapter: NotesAdapter,
    private val viewModel: ViewNoteViewModel,
    private val context: Context
)
{

    fun attach() {

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
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

                Snackbar.make(recyclerView, "Note deleted!", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        notesAdapter.notes.add(position, removedNote)
                        notesAdapter.notifyItemInserted(position)
                        viewModel.insert(removedNote)
                    }.show()
                viewModel.fetchSortedNotes(SortType.DATE_DESC)
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
                        ContextCompat.getDrawable(context, R.drawable.ic_delete_sweep)
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
        // Attach ItemTouchHelper to RecyclerView
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)
    }
}