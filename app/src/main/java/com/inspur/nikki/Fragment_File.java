package com.inspur.nikki;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.BaseNetCallBack;
import net.MonitorNetWork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Fragment_File extends Fragment {

    private EditText edit;
    private Button create_database;
    private MyDatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_file, container, false);

        edit = (EditText) view.findViewById(R.id.edit);

        create_database = (Button) view.findViewById(R.id.create_database);
        String inputText = load();
        if (!TextUtils.isEmpty(inputText)) {
            edit.setText(inputText);
            edit.setSelection(inputText.length());
            Toast.makeText(getContext(), "恢复成功", Toast.LENGTH_LONG).show();
        }
        dbHelper = new MyDatabaseHelper(getContext(), "BookStore.db", null, 1);
        create_database.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dbHelper.getWritableDatabase();
            }
        });
        Button addData = (Button) view.findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装第一天数据
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                db.insert("Book", null, values);//插入第一条数据
                values.clear();
                //开始组装第二条数据
                values.put("name", "The Lost Sysbol");
                values.put("author", "Dan Brown");
                values.put("pages", 510);
                values.put("price", 19.95);
                db.insert("Book", null, values);

            }
        });
        Button updateData = (Button) view.findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 10.99);
                db.update("Book", values, "name = ?", new String[]{"The Da Vinci Code"});
            }
        });
        Button deleteButton = (Button) view.findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Book", "pages > ?", new String[]{"500"});
            }
        });

        Button speedButton = (Button) view.findViewById(R.id.speed_test);
        speedButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final File file = new File("/mnt/speedfile.ts");
                final String url = "http://60.208.86.91:8080/iptv-task/speedtest";
                final long nowTimeStamp = System.currentTimeMillis();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new MonitorNetWork(getContext()).reportFile(url, file, new BaseNetCallBack() {
                            @Override
                            public void onCallSuccess(String s) {
                                super.onCallSuccess(s);

                                Log.i("Nikki", "len:" + file.length());

                                long speed = ((file.length() * 1000 / 8 / 1024) / (System.currentTimeMillis() - nowTimeStamp));//毫秒转换

                                Log.i("Nikki", "speed:" + speed);
//
                            }

                            @Override
                            public void onCallFailed() {
                                super.onCallFailed();
                                Log.i("Nikki", "failed");
                            }
                        });
                    }
                }).start();
            }
        });


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        String inputText = edit.getText().toString();
        save(inputText);

    }

    private void save(String inputText) {

        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = getActivity().openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = getActivity().openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();

    }

    public class MyDatabaseHelper extends SQLiteOpenHelper {

        public static final String CREATE_BOOK = "create table book ("
                + "id integer primary key autoincrement, "
                + "author text,"
                + "price real,"
                + "pages integer,"
                + "name text)";

        private Context mContext;

        public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            mContext = context;

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_BOOK);
            Toast.makeText(mContext, "create succeed", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        }
    }

}
