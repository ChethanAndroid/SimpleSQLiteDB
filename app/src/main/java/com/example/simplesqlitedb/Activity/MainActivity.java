package com.example.simplesqlitedb.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.simplesqlitedb.DataBase.BaseHelper;
import com.example.simplesqlitedb.DataBase.BasePojo;
import com.example.simplesqlitedb.R;

public class MainActivity extends AppCompatActivity {

    EditText name,age,dept,gender;
    Button submit,next,delete;

    BasePojo basePojo;
    BaseHelper baseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.edt_name);
        age = findViewById(R.id.edt_age);
        dept = findViewById(R.id.edt_dept);
        gender = findViewById(R.id.edt_gender);

        submit = findViewById(R.id.btn_submit);
        next = findViewById(R.id.btn_next);
        delete = findViewById(R.id.btn_delete);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                basePojo = new BasePojo();

                basePojo.setName(name.getText().toString());
                basePojo.setAge(age.getText().toString());
                basePojo.setGend(gender.getText().toString());
                basePojo.setDept(dept.getText().toString());

                baseHelper = new BaseHelper(MainActivity.this);
                baseHelper.ADDToDB(basePojo);

                name.setText("");
                age.setText("");
                dept.setText("");
                gender.setText("");



            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                baseHelper = new BaseHelper(MainActivity.this);
                baseHelper.deleteAll();

            }
        });

    }
}
