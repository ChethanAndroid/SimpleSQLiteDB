package com.example.simplesqlitedb.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.simplesqlitedb.DataBase.BaseHelper;
import com.example.simplesqlitedb.DataBase.BasePojo;
import com.example.simplesqlitedb.R;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    TextView name,age,dept,gender;


    BaseHelper baseHelper;
    BasePojo basePojo;

    List<BasePojo> basePojos = new ArrayList<>();

    String s1,s2,s3,s4;

    List<String> NameList = new ArrayList<>();
    List<String> AgeList = new ArrayList<>();
    List<String> GenderList = new ArrayList<>();
    List<String> DeptList = new ArrayList<>();


    RecyclerView rcv;
    Adapter dbAdapter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        name = findViewById(R.id.txt_name);
        age = findViewById(R.id.txt_age);
        dept = findViewById(R.id.txt_dept);
        gender = findViewById(R.id.txt_gen);


        rcv = findViewById(R.id.rcv_id);

        rcv.setHasFixedSize(true);
        dbAdapter = new Adapter();
        rcv.setAdapter(dbAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        rcv.setLayoutManager(manager);
        rcv.setItemAnimator(new DefaultItemAnimator());
        rcv.setNestedScrollingEnabled(false);

        new GetDetailsFromSQLiteDb().execute();


//        System.out.println("name:"+s1);

//        name.setText(s1);
//        age.setText(s2);
//        dept.setText(s3);
//        gender.setText(s4);


    }


    public class GetDetailsFromSQLiteDb extends AsyncTask<String,String,Boolean>{

        Boolean asdf = false;

        @Override
        protected void onPreExecute() {
//            super.onPreExecute();
            progressDialog = ProgressDialog.show(DisplayActivity.this,"Loading","Please Wait",true);

            NameList.clear();
            AgeList.clear();
            DeptList.clear();
            GenderList.clear();
        }


        @Override
        protected Boolean doInBackground(String... strings) {

            basePojo = new BasePojo();
            baseHelper = new BaseHelper(DisplayActivity.this);

            basePojos = baseHelper.FetchAll();

            for (BasePojo basePojo:basePojos){

//            s1 = basePojo.getName();
//            s2 = basePojo.getAge();
//            s3 = basePojo.getGend();
//            s4 = basePojo.getDept();

                NameList.add(basePojo.getName());
                AgeList.add(basePojo.getAge());
                GenderList.add(basePojo.getGend());
                DeptList.add(basePojo.getDept());

            }
            asdf = true;


            return asdf;
        }

        @Override
        protected void onPostExecute(Boolean s) {
//            super.onPostExecute(s);
            progressDialog.dismiss();

            if (s){

                System.out.println("true");
                dbAdapter.notifyDataSetChanged();



            }else {

                AlertDialog.Builder builder = new AlertDialog.Builder(DisplayActivity.this);
                builder.setCancelable(false);
                builder.setTitle("Note:");
                builder.setMessage("Are You Sure");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


            }
        }

    }


    public class Adapter extends RecyclerView.Adapter<Adapter.AdHolder>{
        @NonNull
        @Override
        public AdHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(DisplayActivity.this).inflate(R.layout.rcv_layout,viewGroup,false);
            AdHolder adHolder = new AdHolder(view);
            return adHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull AdHolder adHolder, final int position) {

            adHolder.name.setText(NameList.get(position));
            adHolder.age.setText(AgeList.get(position));
            adHolder.gend.setText(GenderList.get(position));
            adHolder.dept.setText(DeptList.get(position));

            adHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    baseHelper = new BaseHelper(DisplayActivity.this);
                    baseHelper.deleteByName(NameList.get(position));

                    new GetDetailsFromSQLiteDb().execute();

                }
            });

            adHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    ValuesPojo.setSname(NameList.get(position));

                    Intent intent = new Intent(DisplayActivity.this,MainActivity.class);
                    intent.putExtra("update",true);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                    finish();

                }
            });


        }

        @Override
        public int getItemCount() {
            return NameList.size();
        }

        public class AdHolder extends RecyclerView.ViewHolder{

            TextView name,age,dept,gend;
            LinearLayout mainLayout;
            ImageView imageView;

            public AdHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.text_name);
                age = itemView.findViewById(R.id.text_age);
                gend = itemView.findViewById(R.id.text_gen);
                dept = itemView.findViewById(R.id.text_dept);

                mainLayout = itemView.findViewById(R.id.mainLay_id);
                imageView = itemView.findViewById(R.id.delete_id);
            }
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(DisplayActivity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}
