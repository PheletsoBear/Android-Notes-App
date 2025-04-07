import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.to_dolist.data.local.Notes
import com.example.to_dolist.databinding.NotesListBinding
import com.example.to_dolist.R
import com.example.to_dolist.view.NoteListFragmentDirections

class NotesAdapter(
    var notes: MutableList<Notes>,
    private var context: Context,
    private val navController: NavController,

) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: NotesListBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(note: Notes){
       binding.apply {
        noteTitle.text = note.title
         contentDisplay.text = note.content
           noteDate.text = note.date
           cvBottomNav.setOnClickListener {
               val action = NoteListFragmentDirections.actionNoteListFragmentToViewNoteFragment(note)
                navController.navigate(action)

//               val removedNote = notes[adapterPosition]
//               val removedPosition = adapterPosition
//
//               notes.removeAt(adapterPosition)
//               notifyItemRemoved(adapterPosition)
//
//               Snackbar.make(binding.root, "Note deleted", Snackbar.LENGTH_LONG)
//                   .setAction("Undo") {
//                       notes.add(removedPosition, removedNote)
//                       notifyItemInserted(removedPosition)
//                   }.show()
           }
       }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NotesListBinding.inflate(inflater, parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])

    }

    override fun getItemCount(): Int {
      return notes.size
    }

    fun updateNotes(newNotes: List<Notes>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }
}