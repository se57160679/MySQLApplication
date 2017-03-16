package com.pp.mysqlapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
     public ListView listView;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.TodoListview);
        TodoListDAO todoListDAO = new TodoListDAO(getApplicationContext());
        todoListDAO.open();

       // ArrayList<String> myList = todoListDAO.getAlltodoList();

       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, myList);
       // listView.setAdapter(adapter);
        //todoListDAO.close();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id== R.id.action_add_new_menu){
            Intent addNewIntent = new Intent (this,AddDataActivity.class);
            startActivity(addNewIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TodoListDAO todoListDAO = new TodoListDAO(getApplicationContext());
        todoListDAO.open();
        ArrayList<TodoList> myList = todoListDAO.getAlltodoList();

       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, myList);

        final MyListView adapter = new MyListView(this, myList);
        listView.setAdapter(adapter);
        todoListDAO.close();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editIntent = new Intent(getApplicationContext(), EditActivity.class);
                editIntent.putExtra("editTodoList" ,(Serializable) adapter.getItem(position));
                startActivity(editIntent);
            }
        });
    }

}
