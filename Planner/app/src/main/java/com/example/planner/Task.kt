package com.example.planner

//class that only holds data
data class Task(
    val name: String,
    val course_name: String,
    val due_date: String,
    // done is what helps the strikethourgh function
    var done: Boolean = false
){
    // this bool was meant to help detect what records to delete from the DB,
    // Probably not necessary at the moment, but won't delete it just in case
    var toBeDeleted = false
}

// are 2 boolean variables necessary?

