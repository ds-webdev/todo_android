package com.example.dsiko.dawidsikorski_todo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.dsiko.dawidsikorski_todo.Model.Task;
import com.example.dsiko.dawidsikorski_todo.Repository.TaskRepository;

import java.time.LocalDateTime;

@SuppressLint("NewApi")
public class CreateTaskAvtivity extends AppCompatActivity {

    private Button save;
    private TaskRepository taskRepo;
    private EditText name;
    private EditText desc;
    private RadioGroup priorityRadio;
    private String priority;
    private final String status = "New";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task_avtivity);

        this.taskRepo = new TaskRepository(this);

        initViewItems();

        this.priorityRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                View btnRadio = radioGroup.findViewById(i);
                int id = radioGroup.indexOfChild(btnRadio);

                String p = id == 0 ? "Normal" : "Hight";
                setPriority(p);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == save.getId()){
                    if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(desc.getText())
                            || TextUtils.isEmpty(getPriority())){
                        TextView errorMsg = (TextView) findViewById(R.id.errorMsg);
                        errorMsg.setText("You must complete all fields");
                    } else {
                        saveTask();
                        showMainActivity();
                    }
                }
            }
        });

    }

    public void saveTask(){
        this.taskRepo.addTask(new Task(this.getName(),
            this.getDesc(), this.status,
            this.getPriority(), LocalDateTime.now())
        );
    }

    public void showMainActivity(){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

    public void initViewItems(){
        this.save = (Button) findViewById(R.id.save_task);
        this.name = (EditText) findViewById(R.id.task_name);
        this.desc = (EditText) findViewById(R.id.desc_task);
        this.priorityRadio = findViewById(R.id.priority_radio_group);
    }

    public void setPriority(String priority){
        this.priority = priority;
    }

    public String getPriority(){
        return this.priority;
    }

    public String getName(){
        return this.name.getText().toString();
    }

    public String getDesc(){
        return this.desc.getText().toString();
    }

}
