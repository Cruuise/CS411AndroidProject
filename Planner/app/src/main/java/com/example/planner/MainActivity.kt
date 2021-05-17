package com.example.planner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.db.*

class MainActivity : AppCompatActivity() {
    private lateinit var taskAdapter: TaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskAdapter = TaskAdapter(mutableListOf())
        rvTasks.adapter = taskAdapter
        rvTasks.layoutManager = LinearLayoutManager(this)
        // Update with tasks in database
        var testing = TaskService.getInstance(this).getAllTasks()
        taskAdapter.updateTasks(testing)

        btn_add.setOnClickListener {
            val taskTitle = etTitle.text.toString()
            val course = etCourse.text.toString()
            val due = etDue.text.toString()

            // Check to see all fields are filled out
            if(taskTitle.isNotEmpty() and course.isNotEmpty() and due.isNotEmpty()){
                val task = Task(taskTitle, course, due)
                taskAdapter.addTask(task)
                //db.insert("TASKS", "TaskTitle" to task.name, "Group" to task.course_name,
                 //   "Due" to task.due_date)
                etTitle.text.clear()
                etCourse.text.clear()
                etDue.text.clear()
                // add to the DB
                database.use {
                    insert("TASKS", "TaskTitle" to taskTitle, "Course" to course,
                            "Due" to due, "Done" to 0)
                }
            }
        }

        btn_delete.setOnClickListener {
            // mutable list of names that were deleted, we want to delete them from the DB
            val deleteNames = taskAdapter.deleteTask()
            for (fObj in deleteNames) {
                    // delete record from database
                DatabaseHelper.getInstance(this).DeleteData(fObj)
            }
        }
    }
}