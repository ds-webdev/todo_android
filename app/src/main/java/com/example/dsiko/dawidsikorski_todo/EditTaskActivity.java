package com.example.dsiko.dawidsikorski_todo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.dsiko.dawidsikorski_todo.Model.Task;
import com.example.dsiko.dawidsikorski_todo.Repository.TaskRepository;

import java.time.LocalDateTime;

@SuppressLint("NewApi")
public class EditTaskActivity extends AppCompatActivity {

    Button btnSave;
    EditText taskName;
    RadioGroup taskStatus;
    RadioButton radioStatus;
    RadioGroup taskPriority;
    RadioButton radioPriority;
    EditText taskDesc;

    String tempPriority;
    String tempStatus;

    final String statusNew = "New";
    final String statusDone = "Done";
    final String priority = "Normal";

    Task task;
    TaskRepository taskRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        taskRepo = new TaskRepository(this);
        task = (Task) getIntent().getSerializableExtra("task");
        initViewItems();
        setDataInputs();

        tempPriority = task.getPriority();
        tempStatus = task.getStatus();

        taskPriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                View btnRadio = radioGroup.findViewById(i);
                int id = radioGroup.indexOfChild(btnRadio);
                tempPriority = id == 0 ? "Normal" : "Hight";
            }
        });

        taskStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View btnRadio = radioGroup.findViewById(i);
                int id = radioGroup.indexOfChild(btnRadio);

                if (id == 0)
                    tempStatus = "New";
                else if (id == 1)
                    tempStatus = "In progress";
                else
                    tempStatus = "Done";
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == btnSave.getId()){
                    if (TextUtils.isEmpty(taskName.getText()) || TextUtils.isEmpty(taskDesc.getText()) ||
                            TextUtils.isEmpty(taskPriority.toString()) || TextUtils.isEmpty(taskStatus.toString())){
                        TextView errorMsg = (TextView) findViewById(R.id.errorMsg);
                        errorMsg.setText("You must complete all fields");
                    } else {
                        updateTask();
                        taskRepo.updateTask(task);
                        Intent intent = new Intent(getBaseContext(), ShowTaskActivity.class);
                        intent.putExtra("task", task);
                        startActivity(intent);
                    }
                }

            }
        });

    }

    public void initViewItems(){
        taskName = (EditText) findViewById(R.id.task_name);
        taskStatus = (RadioGroup) findViewById(R.id.task_status_radio_group);
        taskPriority = (RadioGroup) findViewById(R.id.priority_radio_group);
        taskDesc = (EditText) findViewById(R.id.task_desc);
        btnSave = (Button) findViewById(R.id.btn_save_edit_task);
    }

    public void setDataInputs(){
        taskName.setText(task.getName());
        taskDesc.setText(task.getDesc());

        if (task.getStatus().equals(statusNew))
            radioStatus = (RadioButton) findViewById(R.id.task_new);
        else if (task.getStatus().equals(statusDone))
            radioStatus = (RadioButton) findViewById(R.id.task_done);
        else
            radioStatus = (RadioButton) findViewById(R.id.task_in_progress);

        radioStatus.setChecked(true);

        if (task.getPriority().equals(priority))
            radioPriority = (RadioButton) findViewById(R.id.normal_priority);
        else
            radioPriority = (RadioButton) findViewById(R.id.hight_priority);

        radioPriority.setChecked(true);

    }

    public void updateTask(){
        task.setName(taskName.getText().toString());
        task.setDesc(taskDesc.getText().toString());
        task.setStatus(tempStatus);
        task.setPriority(tempPriority);

        if (tempStatus.equals("Done"))
            task.setEndDate(LocalDateTime.now());

    }

}
