package com.example.planner


import androidx.appcompat.app.AppCompatActivity

import org.jetbrains.anko.db.*

// Singleton service object
class TaskService (val ctx : AppCompatActivity) {

    var taskList : List<Task> = listOf()

    companion object {
        private var fService : TaskService? = null

        fun getInstance(ctx: AppCompatActivity) : TaskService {
            if (fService == null) {
                fService = TaskService(ctx)
            }
            return fService!!
        }
    }

    // add function not used here, implemented in Main activity instead
    fun addTaskDB(fObj : Task) {
        ctx.database.use {
                insert("DefaultTable", "TaskTitle" to fObj.name, "Course" to fObj.course_name,
                    "Due" to fObj.due_date)
        }
    }


    // returns list of all tasks in db
    fun getAllTasks(): List<Task> {
        ctx.database.use {
            select("TASKS").exec {
                // Convert the record into DO format
                val parser = classParser<Task>()
                taskList = parseList(parser)
            }
        }
        return taskList
    }

    // function not used, delete done in main activity and databasehelper
    fun deleteTaskDB() {
        ctx.database.use {
            select("TASKS").exec {
                // Convert the record into DO format
                val parser = classParser<Task>()
                taskList = parseList(parser)
            }
        }
        for (fObj in taskList) {
            if (fObj.toBeDeleted) {
                // delete record from database
                ctx.database.use {
                    delete("TASKS", "TaskTitle = {TaskTitle}", "TaskTitle" to fObj.name)
                }
            }
        }
    }
}
