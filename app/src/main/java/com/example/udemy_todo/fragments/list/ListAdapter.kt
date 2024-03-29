package com.example.udemy_todo.fragments.list

import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.udemy_todo.R
import com.example.udemy_todo.data.models.Priority
import com.example.udemy_todo.data.models.ToDoData
import java.util.zip.Inflater

class ListAdapter:RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<ToDoData>()

    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val row_background: ConstraintLayout = holder.itemView.findViewById(R.id.row_background)
        row_background.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)

        }

        val title_txt:TextView = holder.itemView.findViewById(R.id.title_txt)
        title_txt.text = dataList[position].title
        val description_txt:TextView = holder.itemView.findViewById(R.id.description_txt)
        description_txt.text = dataList[position].description

        var priority_indicator: CardView = holder.itemView.findViewById(R.id.priority_indicator)
        Log.w("COLOR", position.toString() +" " + dataList[position].priority)
        Log.w("COLOR", R.color.red.toString())
        Log.w("COLOR1", priority_indicator.cardBackgroundColor.toString())
        when(dataList[position].priority) {
            Priority.HIGH -> priority_indicator.setCardBackgroundColor(ContextCompat.getColor(
                holder.itemView.context,
                R.color.red
            ))
            Priority.MEDIUM -> priority_indicator.setCardBackgroundColor(ContextCompat.getColor(
                holder.itemView.context,
                R.color.yellow
            ))
            Priority.LOW -> priority_indicator.setCardBackgroundColor(ContextCompat.getColor(
                holder.itemView.context,
                R.color.green
            ))
        }
        Log.w("COLOR2", priority_indicator.cardBackgroundColor.toString())
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setData(toDoData: List<ToDoData>) {
        this.dataList = toDoData
        notifyDataSetChanged()
    }
}