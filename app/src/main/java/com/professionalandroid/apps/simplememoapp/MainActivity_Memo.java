package com.professionalandroid.apps.simplememoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity_Memo extends AppCompatActivity {

    //버튼
    private Button bt_Save;
    private Button bt_Load;
    private Button bt_Delete;
    //편집 텍스트
    private EditText editText_TextArea;
    //메모를 저장할 파일
    private String fileName = "SimpleMemo.txt";
    //시간 출력
    private TextView textView_Time;
    private TextView textView_Title;
    long mNow = System.currentTimeMillis();
    Date mReDate = new Date(mNow);
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formatDate = mFormat.format(mReDate);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼, 편집 텍스트 가져오기
        bt_Save = findViewById(R.id.button_save);
        bt_Load = findViewById(R.id.button_load);
        bt_Delete = findViewById(R.id.button_delete);
        editText_TextArea = findViewById(R.id.editText);
        //시간 텍스트 가져오기
        textView_Time = findViewById(R.id.textView);

        //버튼 클릭시 기능 추가하는 함수 생성
        bt_Save.setOnClickListener(btnSave);
        bt_Load.setOnClickListener(btnLoad);
        bt_Delete.setOnClickListener(btnDelete);
        //시간 기능 추가하는 함수 생성
        textView_Time.setText(formatDate);
    }


    //로드 버튼 클릭시 기능
    View.OnClickListener btnLoad = new View.OnClickListener() {
        public void onClick(View view) {
            FileInputStream inputStream = null;

            try {
                inputStream = openFileInput(fileName);
                byte[] data = new byte[inputStream.available()];
                while(inputStream.read(data) != -1) {}
                editText_TextArea.setText(new String(data));
                //화면 아래 메시지 출력
                Toast.makeText(getApplicationContext(), "로드 완료", Toast.LENGTH_LONG).show();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                    //성공 여부 상관없이 닫기
                    if(inputStream != null)
                        inputStream.close();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    //저장 버튼 클릭시 기능
    View.OnClickListener btnSave = new View.OnClickListener() {
        public void onClick(View view) {
            FileOutputStream outputStream = null;

            try {
                outputStream = openFileOutput(fileName, MODE_PRIVATE);
                outputStream.write(editText_TextArea.getText().toString().getBytes());
                outputStream.close();
                //화면 아래에 메세지 출력
                Toast.makeText(getApplicationContext(), "저장 완료", Toast.LENGTH_LONG).show();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    };

    //삭제 버튼 클릭시 기능
    View.OnClickListener btnDelete = new View.OnClickListener() {
        public void onClick(View view) {
            boolean del = deleteFile(fileName);
            //화면 아래에 메세지 출력
            if(del)
                Toast.makeText(getApplicationContext(), "메모 삭제 완료", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "메모 삭제 실패", Toast.LENGTH_LONG).show();
        }
    };
}