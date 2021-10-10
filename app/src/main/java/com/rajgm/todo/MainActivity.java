package com.rajgm.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;
    Button addButton;
    EditText todoItem;
    RecyclerView rvItem;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        todoItem = findViewById(R.id.todoItem);
        rvItem = findViewById(R.id.rvItem);


        items = new ArrayList<>();
        items.add("Drink Water");
        items.add("Do Yoga");
        items.add("Explore CSec");


        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item removed",Toast.LENGTH_SHORT);
            }
        };

        itemsAdapter =  new ItemsAdapter(items,onLongClickListener);
        rvItem.setAdapter(itemsAdapter);
        rvItem.setLayoutManager(new LinearLayoutManager(this));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItemS = todoItem.getText().toString();
                items.add(todoItemS);
                itemsAdapter.notifyItemInserted(items.size()-1);
                todoItem.setText("");
                Toast.makeText(getApplicationContext(),"Item added",Toast.LENGTH_SHORT).show();

            }
        });



    }




}