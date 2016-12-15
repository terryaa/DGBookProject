package com.frenzy.ebook.dgbook2.MyAccount;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.frenzy.ebook.dgbook2.R;

/**
 클래스명 : public class MyDealingDetail_Activity extends AppCompatActivity
 주된기능 : 내가 거래중인 책 목록 중 하나를 터치했을 때 이 액티비티를 통해 해당 도서의 상세정보를 보여준다.
 멤버변수 :

 메소드 :
 protected void onCreate(Bundle savedInstanceState)
 * 매개변수: Bundle savedInstanceState
 * 기능 : 뷰의 생성자
 */


public class MyDealingDetail_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dealing_detail_);

        Button button_selled=(Button)findViewById(R.id.button_selled);
        Button button_lend_start=(Button)findViewById(R.id.button_lend_start);
        Button button_lend_finish=(Button)findViewById(R.id.button_lend_finish);

        button_selled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "판매완료 되었습니다", Toast.LENGTH_LONG).show();
            }
        });

        button_lend_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "대여가 시작됩니다", Toast.LENGTH_LONG).show();
            }
        });

        button_lend_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "대여기간이 만료되었습니다", Toast.LENGTH_LONG).show();
            }
        });
    }
}
