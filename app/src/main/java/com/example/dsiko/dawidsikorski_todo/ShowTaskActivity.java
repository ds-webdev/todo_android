package com.example.dsiko.dawidsikorski_todo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.dsiko.dawidsikorski_todo.Model.Task;
import com.example.dsiko.dawidsikorski_todo.Repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressLint("NewApi")
public class ShowTaskActivity extends AppCompatActivity {

    private TextView taskName;
    private TextView taskStatus;
    private TextView taskPriority;
    private TextView taskCreateDate;
    private TextView taskDoneDate;
    private TextView taskDesc;

    private TaskRepository taskRepo;
    private Task task;

    final String done = "Done";
    final String inProgress = "In progress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);

        taskRepo = new TaskRepository(this);
        task = (Task) getIntent().getSerializableExtra("task");
        initViewItems();
        setDataTextViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        if (done.equals(task.getStatus()))
            getMenuInflater().inflate(R.menu.task_done_menu, menu);
        else
            getMenuInflater().inflate(R.menu.show_task_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        switch (menuItem.getItemId()){

            case R.id.btn_done:
                taskRepo.updateColumn(Integer.toString(task.getId()), TaskRepository.STATUS, done);
                task.setStatus(done);
                task.setEndDate(LocalDateTime.now());
                invalidateOptionsMenu();
                setDataTextViews();
                return true;
            case R.id.btn_in_progress:
                taskRepo.updateColumn(Integer.toString(task.getId()), TaskRepository.STATUS, inProgress);
                task.setStatus(inProgress);
                setDataTextViews();
                return true;
            case R.id.btn_edit_task:
                Intent intent = new Intent(getBaseContext(), EditTaskActivity.class);
                intent.putExtra("task", task);
                startActivity(intent);
                return true;
            case R.id.btn_delete_task:
                taskRepo.deleteTask(Integer.toString(task.getId()));
                showTaskList();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    public void setDataTextViews(){
        taskName.setText(task.getName());
        taskStatus.setText(task.getStatus());
        taskPriority.setText(task.getPriority());
        taskCreateDate.setText(String.valueOf(
                task.getCreateDate().
                        format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));

        if (task.getEndDate() == null)
            taskDoneDate.setText("Task is not finished");
        else
            taskDoneDate.setText(String.valueOf(
                    task.getEndDate().
                            format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));

        taskDesc.setText(task.getDesc());
    }

    public void initViewItems(){
        taskName = (TextView) findViewById(R.id.task_name);
        taskStatus = (TextView) findViewById(R.id.task_status);
        taskPriority = (TextView) findViewById(R.id.task_priority);
        taskCreateDate = (TextView) findViewById(R.id.task_create_date);
        taskDoneDate = (TextView) findViewById(R.id.task_end_date);
        taskDesc = (TextView) findViewById(R.id.task_desc);
    }

    public void showTaskList(){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

}
