package com.frenzy.ebook.dgbook2.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.frenzy.ebook.dgbook2.R;

/**
 * 클래스 이름 : public class IntroActivity extends AppCompatActivity
 * 주요 기능 : 제일 첫 화면을 담당하며, 스레드를 통해, 튜토리얼 대상인 앱을 처음사용하는지 아닌데 여부 판단후 로그인 화면으로 넘어갈지 아님 액티비티로 넘어갈지 정함
 *
 * 멤버변수
 * private SharedPreferences pref : SharedPreferences는 데이터를 파일로 저장하는 기능의 클래스이며,
 *                                  이번프로젝트에선 튜토리얼 대상여부, 자동로그인기능에 적용
 * private SharedPreferences.Editor prefEditor : SharedPreferences를 통해 만든 파일을 수정하는 에디
 * private Handler h; // Handler를 통해 일정시간 대기시간을 가지며 그 시간동안 조건을 따진 후 인텐트 실행
 * private int delayTime = 1000;
 *
 * 메소드
 * public void onCreate(Bundle savedInstanceState)
 * 매개변수 : Bundle savedInstanceState
 * 주요 기능 : 뷰 생성자
 * public void onBackPressed()
 * 매개변수 :
 * 주요 기능 : 핸들러 메세지 딜래이 줬을때 취소하는 방법
 */



public class IntroActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;
    private Handler h;
    private int delayTime = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        pref = getSharedPreferences("Univtable", MODE_PRIVATE);
        prefEditor = pref.edit();

        h = new Handler();
        h.postDelayed(intro, delayTime);

    }

    private Runnable intro = new Runnable() {
        public void run() {
            if(pref.getBoolean("auto", false)){
                Intent i = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }else{
                Intent i = new Intent(IntroActivity.this, TutorialActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

        }
    };

    @Override
    public void onBackPressed() {
        h.removeCallbacks(intro);
    }
}