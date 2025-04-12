import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.to_dolist.data.local.Notes
import com.example.to_dolist.databinding.NotesListBinding
import com.example.to_dolist.view.NoteListFragmentDirections
import com.example.to_dolist.util.NotesDiffCallUtil
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class NotesAdapter(
    var notes: MutableList<Notes>,
    private var context: Context,
    private val navController: NavController,

) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: NotesListBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(note: Notes){

        val formattedDate = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault())
            .format(Date(note.date))

        binding.apply {
        noteTitle.text = note.title
         contentDisplay.text = note.content
           noteDate.text = formattedDate
           cvBottomNav.setOnClickListener {
               val action = NoteListFragmentDirections.actionNoteListFragmentToViewNoteFragment(note)
                navController.navigate(action)
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
        val diffCallback = NotesDiffCallUtil(notes, newNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        notes = newNotes.toMutableList()

        diffResult.dispatchUpdatesTo(this)
    }
}