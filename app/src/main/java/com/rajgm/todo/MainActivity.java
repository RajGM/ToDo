package com.rajgm.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.FileUtils.readLines;
import static org.apache.commons.io.FileUtils.writeLines;


public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION  = "item_position";
    public static final int EDIT_TEXT_CODE  = 20;



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
        loadItems();


        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item removed",Toast.LENGTH_SHORT);
                saveItems();
            }
        };

        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d("MainActivity","Single click at position " + position);
                Intent i = new Intent(MainActivity.this,EditText.class);
                i.putExtra(KEY_ITEM_TEXT,items.get(position));
                i.putExtra(KEY_ITEM_POSITION,position);
                registerForActivityResult(i,);
                startActivityForResult(i,EDIT_TEXT_CODE);
            }
        };

        itemsAdapter =  new ItemsAdapter(items,onLongClickListener, onClickListener);
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
                saveItems();
            }
        });

    }

    private File getDataFile(){
        return new File(getFilesDir(),"data.txt");
    }

    //function to load items from file
    private void loadItems(){
        try{
            items = new ArrayList<>(readLines(getDataFile(), Charset.defaultCharset()));
        }catch(IOException e){
            Log.e("MainActivity","Error reading items",e);
            items = new  ArrayList<>();
        }

    }

    //function to save items to files
    private void saveItems(){
        try{
            writeLines(getDataFile(),items);
        }catch(IOException e){
            Log.e("MainActivity","Error writing items",e);
        }

    }



}