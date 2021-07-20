package com.example.id20001695.demodatabasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etContent;
    Button btnAdd, btnEdit, btnRetrieve;
    ListView lv;
    ArrayList<Note> al;
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etContent = findViewById(R.id.etContent);
        btnAdd = findViewById(R.id.buttonAdd);
        btnEdit = findViewById(R.id.buttonEdit);
        btnRetrieve = findViewById(R.id.buttonRetrieve);
        lv = findViewById(R.id.lv);

        al = new ArrayList<>();
        aa = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                long inserted_id = dbh.insertNote(etContent.getText().toString());

                if(inserted_id != -1) {
                    al.clear();
                    al.addAll(dbh.getAllNotes());
                    aa.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Insert Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Note target = al.get(0);
                    Intent i = new Intent(MainActivity.this, EditActivity.class);
                    i.putExtra("data", target);
                    startActivity(i);
                }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                al.clear();
                String filterText = etContent.getText().toString().trim();
                if(filterText.length() == 0) {
                    al.addAll(dbh.getAllNotes());
                }
                else{
                    al.addAll(dbh.getAllNotes(filterText));
                }
                aa.notifyDataSetChanged();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long identity) {
                Note data = al.get(position);
                Intent i = new Intent(MainActivity.this,
                        EditActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

        btnRetrieve.performClick();
    }

}
