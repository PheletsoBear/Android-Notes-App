package com.example.to_dolist.util

import androidx.recyclerview.widget.DiffUtil
import com.example.to_dolist.data.local.Notes

class NotesDiffCallUtil (
    private val oldList: List<Notes>,
    private val newList: List<Notes>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}