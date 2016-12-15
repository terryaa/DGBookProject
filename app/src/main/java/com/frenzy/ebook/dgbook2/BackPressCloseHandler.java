package com.frenzy.ebook.dgbook2;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.frenzy.ebook.dgbook2.Activity.LoginActivity;
import com.frenzy.ebook.dgbook2.Activity.MainmenuActivity;

/**
 * Created by SeungHyeonPark on 2016. 11. 11..
 */

/**
 * 클래스 이름 : public class BackPressCloseHandler
 * 주요 기능 : 연속으로 두번누르면 어플 종료
 *
 * 멤버변수
 * private long backKeyPressedTime = 0; 뒤로가기 눌렀을떄 카운팅
 * private Toast toast; 토스트
 * private Activity activity; 액티비티
 *
 * 메소드
 * public BackPressCloseHandler(Activity context)
 * 매개변수 : Activity context
 * 주요 기능 : 생성자
 * public void onBackPressed()
 * 매개변수 :
 * 주요기능 : 프로그램을 종료시키는 실질적 역한
 * public void showGuide()
 * 매개변수 :
 * 주요기능 : 토스트 알람 메시징
 */


public class BackPressCloseHandler {
    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            toast.cancel();

            Intent t = new Intent(activity, MainmenuActivity.class);
            activity.startActivity(t);

            activity.moveTaskToBack(true);
            activity.finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
