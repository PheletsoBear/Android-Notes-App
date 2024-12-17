import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.to_dolist.Notes
import com.example.to_dolist.databinding.NotesListBinding
import com.google.android.material.snackbar.Snackbar

class NotesAdapter(private val context: Context, private val notes: MutableList<Notes>) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: NotesListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NoteViewHolder {
        val binding = NotesListBinding.inflate(LayoutInflater.from(context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesAdapter.NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.binding.noteTitle.text = note.title
        holder.binding.contentDisplay.text = note.content

        holder.binding.root.setOnClickListener{
            val removedNote = notes[position]
            val removedPosition = position

            notes.removeAt(position)
            notifyItemRemoved(position)

            Snackbar.make(holder.binding.root, "Note deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo") {
                    notes.add(removedPosition, removedNote)
                    notifyItemInserted(removedPosition)
                }.show()
           true
        }
    }

    override fun getItemCount(): Int {
      return notes.size
    }
}