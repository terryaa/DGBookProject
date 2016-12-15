package com.frenzy.ebook.dgbook2.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.frenzy.ebook.dgbook2.Crawler.Crawler;
import com.frenzy.ebook.dgbook2.Crawler.DonggukCrawler;
import com.frenzy.ebook.dgbook2.R;

import java.util.ArrayList;

/**
 * 클래스 이름 : LoginActivity extends AppCompatActivity implements View.OnClickListener
 * 주요 기능 : 이클래스 계정을 입력받아 로그인기능 구현
 *
 * 멤버변수
 * private Crawler crawler; jsoup parser를 이용하며, 로그인 기능을 실질적으로 담당하는 객체
 * private SharedPreferences pref;
 * private SharedPreferences.Editor prefEditor;
 * private Button _login : 아이디 비밀번호를 입력받고 ,데이터값을 크롤러에게 넘기는 기능을 함
 * private EditText _id, _pw; : 아이디와 비밀번호를 입력받는 텍스트상
 *
 * 메소드
 * public void onClick(View v)
 * 매개변수 : View v
 * 주요기능 : 로그인 버튼을 눌렀을때의 액션을 담당하는 메소드
 * public void showToast(String message)
 * 매개변수 : String message
 * 주요기능 : 토스트 메세지를 띄어주는 기능 긴 명령어를 짧게 축소할수 있게 재정의
 * public void signIn(String id, String pw)
 * 매개변수 : String id, String pw
 * 주요기능 : id와 pw를 받아서 로그인을 하는 단계로 이때 crawler 객체를 이용 또한 자동로그인 기능까지 추
 * public void initView()
 * 매개변수 :
 * 주요기능 : 멤버변수들을 실체화 시켜주는 메소드
 * public void onCreate(Bundle savedInstanceState)
 * 매개변수 : Bundle savedInstancesState
 * 주요기능 : 뷰의 생성
 * */

/**
 * 클래스 이름 : public class PasswordTransfromMethod extends PasswordTransformationMethod
 * 주요 기능 : 비밀번호를 검은색 동그라미로 대체시켜기 위해 PasswordCharSequence로 입력받은 CharSequence를 전달
 *
 * 멤버변수
 *
 * 메소드
 * public CharSequence getTransformation(CharSequence source, View view)
 * 매개변수 : CharSequence source, View view
 * 주요기능 : 뷰에서 문자열을 받아 PasswordCharSequence함수 객체 형성
 */

/**
 * 클래스 이름 : private class PasswordCharSequence implements CharSequence
 * 주요 기능 : PasswordTransfromMethod에서 받은 문자열을 ●로 변환해주는 클래스
 *
 * 멤버변수
 * private CharSequence mSource // 외부에서 입력받은 source의 값을 저장하는 변수
 *
 * 메소드
 * public PasswordCharSequence(CharSequence source)
 * 매개변수 : CharSequence source
 * 주요기능 : 외부에서 입력받은 source의 값을 클래스 내부인 mSource값에 전달
 * public char charAt(int index)
 * 매개변수 : int index
 * 주요기능 : 문자열의 문자 하나하나를 ●로 변환해주는 메소드
 * public int length()
 * 매개변수 :
 * 주요기능 : 입력받은 문자열의 길이를 산출하는 메소드
 * public CharSequence subSequence(int start, int end)
 * 매개변수 : int start, int end
 * 주요기능 : 문자열을 정규표현식을 사용해서 쪼개고 문자열의 배열로 반환
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Crawler crawler;
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;
    private Button _login;
    private EditText _id, _pw;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                signIn(_id.getText().toString(), _pw.getText().toString());
                break;
            default:
                break;
        }
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void signIn(String id, String pw) {
        final ProgressDialog pdial = new ProgressDialog(this);
        pdial.setMessage("로그인하는 중...");
        pdial.setCancelable(false);
        pdial.show();

        _login.setEnabled(false);
        crawler = new DonggukCrawler(id, pw);
        if (id.length() != Crawler.LENGTH_DONGGUK) {
            showToast(Crawler.LENGTH_DONGGUK + "자리 학번을 사용합니다");
            _login.setEnabled(true);
            pdial.dismiss();
            return;
        }

        if (pw.length() < 1) {
            showToast("패스워드를 입력하세요");
            _login.setEnabled(true);
            pdial.dismiss();
            return;
        }

        crawler.verify(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.e("Handle", msg.getData().toString());
                if (msg.getData().getBoolean("result")) {
                    prefEditor.putBoolean("auto", true);
                    prefEditor.putString("id", msg.getData().getString("id"));
                    prefEditor.putString("pw", msg.getData().getString("pw"));
                    prefEditor.putString("name", msg.getData().getString("name"));
                    prefEditor.commit();
                    Intent i = new Intent(LoginActivity.this, MainmenuActivity.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(),msg.getData().getString("name")+"님 환영합니다",Toast.LENGTH_SHORT).show();
                    pdial.dismiss();
                    _login.setEnabled(true);
                    finish();
                } else {
                    prefEditor.putBoolean("auto", false);
                    prefEditor.putString("id", "#");
                    prefEditor.putString("pw", "#");
                    prefEditor.putString("name", "#");
                    prefEditor.commit();
                    showToast("로그인에 실패하였습니다");
                    _login.setEnabled(true);
                    pdial.dismiss();
                    return;
                }
            }
        });

    }

    public void initView() {
        _login = (Button) findViewById(R.id.login);
        _id = (EditText) findViewById(R.id.user_id2);
        _pw = (EditText) findViewById(R.id.passwd2);
        _pw.setTransformationMethod(new PasswordTransfromMethod());
        _login.setOnClickListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("Univtable", MODE_PRIVATE);
        prefEditor = pref.edit();

        initView();

        if (pref.getBoolean("auto", false)) {
            signIn(pref.getString("id", "#"), pref.getString("pw", "#"));
        }
    }

    public class PasswordTransfromMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source;
            }

            public char charAt(int index) {
                return '●';
            }

            public int length() {
                return mSource.length();
            }

            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }
}

