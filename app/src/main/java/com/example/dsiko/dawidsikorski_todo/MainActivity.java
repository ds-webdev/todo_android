package com.example.dsiko.dawidsikorski_todo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dsiko.dawidsikorski_todo.Model.Task;
import com.example.dsiko.dawidsikorski_todo.Repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity {


    private TaskRepository taskRepo;
    private ListView todoList;
    private SQLiteDatabase db;
    private ArrayAdapter<String> arrayAdapter;
    private List<Task> taskList = new ArrayList<>();
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.taskRepo = new TaskRepository(this);
        this.todoList = (ListView) findViewById(R.id.todo_list);

        updateTaskList();

        customAdapter = new CustomAdapter();
        todoList.setAdapter(customAdapter);

        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Task task = taskList.get(i);
                Intent intent = new Intent(getBaseContext(), ShowTaskActivity.class);
                intent.putExtra("task", task);
                startActivity(intent);

            }
        });

        customAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        switch (menuItem.getItemId()){

            case R.id.btn_add_task:
                createTask();
                return true;

            case R.id.name_desc:
                sortByName();
                customAdapter.notifyDataSetChanged();
                return true;
            case R.id.create_desc:
                sortByCreate();
                customAdapter.notifyDataSetChanged();
                return true;
            case R.id.priority_desc:
                sortByPriority();
                customAdapter.notifyDataSetChanged();
                return true;
            case R.id.status_desc:
                sortByStatus();
                customAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }

    }

    private void sortByPriority() {
        Collections.sort(taskList, (a, b) -> a.getPriority().compareTo(b.getPriority()));
    }

    private void sortByCreate() {
        Collections.sort(taskList, (a, b) -> a.getCreateDate().compareTo(b.getCreateDate()));
        Collections.reverse(taskList);
    }

    private void sortByName() {
        Collections.sort(taskList, (a, b) -> a.getName().compareTo(b.getName()));
    }

    private void sortByStatus() {
        Collections.sort(taskList, (a, b) -> a.getStatus().compareTo(b.getStatus()));
        Collections.reverse(taskList);
    }

    private void createTask(){
        Intent intent = new Intent(this, CreateTaskAvtivity.class);
        startActivity(intent);
    }

    public void updateTaskList(){
        taskList.addAll(taskRepo.getAll());
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return taskList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.todo_item, null);

            TextView name = view.findViewById(R.id.name);
            TextView priority = view.findViewById(R.id.priority);
            TextView status = view.findViewById(R.id.status);

            name.setText(taskList.get(i).getName());
            priority.setText(taskList.get(i).getPriority());
            status.setText(taskList.get(i).getStatus());

            return view;
        }
    }

}