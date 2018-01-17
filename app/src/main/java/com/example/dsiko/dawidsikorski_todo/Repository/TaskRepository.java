package com.example.dsiko.dawidsikorski_todo.Repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dsiko.dawidsikorski_todo.Model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dsiko on 08.01.2018.
 */

@SuppressLint("NewApi")
public class TaskRepository extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TODOLIST";
    public static final String TABLE_NAME = "task";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESC = "description";
    public static final String STATUS = "status";
    public static final String PRIORITY = "priority";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";

    public  TaskRepository(Context context){
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "("+ ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "description TEXT," +
                "status TEXT, " +
                "priority TEXT, " +
                "start_date TEXT, " +
                "end_date TEXT DEFAULT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Collection<Task> getAll() {

        Collection<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME,null);

        while(cursor.moveToNext()){

            LocalDateTime endDate = null;

            if (cursor.getString(6) != null)
                endDate = LocalDateTime.parse(cursor.getString(6));


            tasks.add(new Task(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    LocalDateTime.parse(cursor.getString(5)),
                    endDate)
            );
        }

        return tasks;
    }

    public boolean addTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME, task.getName());
        contentValues.put(DESC, task.getDesc());
        contentValues.put(STATUS, task.getStatus());
        contentValues.put(PRIORITY, task.getPriority());
        contentValues.put(START_DATE, String.valueOf(LocalDateTime.now()));

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result == -1 ? false : true;
    }

    public void updateColumn(String taskId, String colName, String value){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if (colName.equals(STATUS) && value.equals("Done"))
            contentValues.put(END_DATE, String.valueOf(LocalDateTime.now()));

        contentValues.put(colName, value);

        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {taskId});
    }

    public boolean updateTask(Task task) {

        String taskId = Integer.toString(task.getId());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ID, taskId);
        contentValues.put(NAME, task.getName());
        contentValues.put(DESC, task.getDesc());
        contentValues.put(STATUS, task.getStatus());
        contentValues.put(PRIORITY, task.getPriority());

        if (task.getStatus().equals("Done"))
            contentValues.put(END_DATE, String.valueOf(LocalDateTime.now()));

        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { taskId });

        return true;
    }

    public Integer deleteTask (String taskId) {

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "ID = ?", new String[] {taskId});
    }

}
