package com.example.planner

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DatabaseHelper(ctx : Context) : ManagedSQLiteOpenHelper(ctx, "PlannerDB", null, 1){

    companion object {
        private var instance : DatabaseHelper? = null
        fun getInstance(ctx : Context) : DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(ctx)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create table
        db?.createTable("TASKS", true,
                "TaskTitle" to TEXT, "Course" to TEXT, "Due" to TEXT, "Done" to INTEGER)
    }

    fun DeleteData(nameToBeDeleted: String){
        val TABLE_NAME = "TASKS"
        val COLUMN = "TaskTitle"
        val query = "DELETE FROM $TABLE_NAME WHERE $COLUMN = '$nameToBeDeleted'"
        val db = this.writableDatabase
        db.execSQL(query)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //

    }
}

val Context.database : DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)