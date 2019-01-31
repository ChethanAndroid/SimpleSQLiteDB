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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText name,age,dept,gender;
    Button submit,next,delete,update;

    BasePojo basePojo;
    BaseHelper baseHelper;

    List<BasePojo> NamePojoList = new ArrayList<>();

    Boolean isUpdate = false;

    String updateName;

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
//        update = findViewById(R.id.update);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isUpdate){

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

                }else {





                    basePojo = new BasePojo();

                    basePojo.setName(name.getText().toString());
                    basePojo.setAge(age.getText().toString());
                    basePojo.setGend(gender.getText().toString());
                    basePojo.setDept(dept.getText().toString());

                    baseHelper = new BaseHelper(MainActivity.this);
                    baseHelper.update(updateName,basePojo);

                    name.setText("");
                    age.setText("");
                    dept.setText("");
                    gender.setText("");

                }





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

        if (getIntent().getBooleanExtra("update",false)){

            System.out.println("Update");

            submit.setText("Update");

            submit.setBackgroundColor(getResources().getColor(R.color.colorAccent));

            System.out.println("GetName:"+ValuesPojo.getSname());

            baseHelper = new BaseHelper(MainActivity.this);
            NamePojoList = baseHelper.FetchByName(ValuesPojo.getSname());

            for (BasePojo pojo:NamePojoList){

                name.setText(pojo.getName());
                age.setText(pojo.getAge());
                gender.setText(pojo.getGend());
                dept.setText(pojo.getDept());

                updateName = pojo.getName();

                isUpdate =true;

                System.out.println("update:"+updateName);
            }

        }else {
            isUpdate=false;

            submit.setText("Submit");

            submit.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            System.out.println("Pain");
        }




    }
}
