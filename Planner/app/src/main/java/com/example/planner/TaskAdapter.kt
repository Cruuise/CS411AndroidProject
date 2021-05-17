package com.example.planner

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.default_task.view.*
import org.jetbrains.anko.db.*


//contains main logic
class TaskAdapter (
    var tasks: MutableList<Task>
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder> (){
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.default_task, parent, false))
    }

    // Adds tasks to the recycler view
    fun addTask(task: Task){
        tasks.add(task)
        notifyItemInserted(tasks.size - 1 )

    }

    // Updates recycler view by copying tasks from the db
    fun updateTasks(dbtasks: List<Task>){
        tasks = dbtasks.toMutableList()
    }

    // removes task from recycler view, returns list of names it deleted
    fun deleteTask(): MutableList<String> {
        var listofDeletes = mutableListOf<String>()
        for (x in tasks){
            if (x.done){
                listofDeletes.add(x.name)
            }
        }
        tasks.removeAll { task -> task.done
        }
        notifyDataSetChanged()
        return listofDeletes
    }

    // Strikes through tasks that are to be deleted
    private fun toggleStrikeThrough(tvTaskTitle: TextView, done: Boolean) {
        if(done){
            tvTaskTitle.paintFlags = tvTaskTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        }
        else {
            tvTaskTitle.paintFlags = tvTaskTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = tasks[position]
        holder.itemView.apply {
            tvTaskTitle.text = currentTask.name
            tvTaskSubTitle.text = currentTask.course_name + "- Due on "
            tvDueDate.text = currentTask.due_date
            checkBox_complete.isChecked = currentTask.done
            toggleStrikeThrough(tvTaskTitle, currentTask.done)
            checkBox_complete.setOnCheckedChangeListener { _, done -> toggleStrikeThrough(tvTaskTitle, done)
                currentTask.done = !currentTask.done
                // two separate ones just in case. To be deleted is for deleting from database
                currentTask.toBeDeleted = currentTask.done
            }

        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}