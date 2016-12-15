package com.frenzy.ebook.dgbook2.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.frenzy.ebook.dgbook2.BackPressHandler;
import com.frenzy.ebook.dgbook2.DAO;
import com.frenzy.ebook.dgbook2.GetnSet.Book;
import com.frenzy.ebook.dgbook2.R;

/**
 * 클래스 이름 : public class FindBookActivity extends AppCompatActivity implements View.OnClickListener
 * 주된 기능 : 도서검색을 위한 데이터를 입력받는 단계.
 *
 * 멤버 변수 :
 * private RadioButton rb; : 라디오버튼
 * private EditText book_name; : 책 이름
 * private EditText book_comp; : 출판사
 * private EditText book_writer; : 저자
 * private EditText prof_name; : 교수님 성함
 * private EditText class_name; : 책 제목
 * private Button book_search; : 책 검색 버튼
 * private RadioGroup rg_Type; : 라디오버튼을 하나로 묶는 그룹
 * private int radio; : 라디오버튼의 값
 * private int Type; : 판매인지 대여인지 구분
 * private BackPressHandler backPressHandler : 검색 도중 실수로 이전버튼을 눌러버리는 바람에 이전액티비티로 넘어가는것을 방지.
 *
 * 메소드 :
 * protected void onCreate(Bundle savedInstanceState)
 * 매개변수 : Bundle savedInstanceState
 * 주요 기능 : 뷰 생성자
 * public void onBackPressed()
 * 매개변수 :
 * 주요 기능 : 이전버튼을 눌렀을경우, 경고메시지를 띄운 후 같은 작업이 반복될 경우 액티비티 전환하는 메소드
 * public void initview()
 * 매개변수 :
 * 주요 기능 : 클래스 상의 모든 객체를 생성시키는 즉 초기화 하는 단계
 * public void onClick(View v)
 * 매개변수 : View v
 * 주요 기능 : 버튼을 클릭했을 때 처리되는 부분을 하나로 통합한 것.


 */

public class FindBookActivity extends AppCompatActivity implements View.OnClickListener{
    private RadioButton rb;
    private EditText book_name;
    private EditText book_comp;
    private EditText book_writer;
    private EditText prof_name;
    private EditText class_name;
    private Button book_search;
    private RadioGroup rg_Type;
    private int radio;
    private int Type;
    private BackPressHandler backPressHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_book);
        initview();
    }

    @Override
    public void onBackPressed() {
        backPressHandler.onBackPressed();
    }

    public void initview()
    {
        book_name = (EditText) findViewById(R.id.book_name_F);
        book_comp = (EditText) findViewById(R.id.company_F);
        book_writer = (EditText) findViewById(R.id.book_writer_F);
        prof_name = (EditText) findViewById(R.id.prof_name_F);
        class_name = (EditText) findViewById(R.id.class_name_F);
        rg_Type = (RadioGroup) findViewById(R.id.rgType_F);
        book_search = (Button) findViewById(R.id.book_search);
        radio = rg_Type.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(radio);
        book_search.setOnClickListener(this);
        backPressHandler = new BackPressHandler(this);
    }

    @Override
    public void onClick(View v) {
        if(v==book_search)
        {int Type=-1;
            radio = rg_Type.getCheckedRadioButtonId();
            if(radio!=-1)
            {
                rb = (RadioButton) findViewById(radio);
                String Types = rb.getText().toString();
                if (Types.contains("판매"))
                    Type = 1;
                else
                    Type = 0;
            }

            Book book = new Book(book_name.getText().toString(), book_comp.getText().toString(),
                    book_writer.getText().toString(), prof_name.getText().toString(), class_name.getText().toString(),
                    0,Type,null,null,null);
            Intent intent = new Intent(getApplicationContext(), FindResultList_Activity.class);
            intent.putExtra("book",book);
            startActivity(intent);
            finish();
        }
    }
}
