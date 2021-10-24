package com.rajgm.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText editItem;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editItem = findViewById(R.id.editItem);
        saveButton = findViewById(R.id.editButton);

        getSupportActionBar().setTitle("Edit item");

        editItem.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

    }
}