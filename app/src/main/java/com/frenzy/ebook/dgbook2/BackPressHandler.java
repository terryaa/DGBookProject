package com.frenzy.ebook.dgbook2;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.frenzy.ebook.dgbook2.Activity.MainmenuActivity;

/**
 * Created by SeungHyeonPark on 2016. 11. 11..
 */

/**
 * 클래스 이름 : public class BackPressHandler
 * 주요 기능 : 뒤로 두번가기을 실행했을때, 작성중이던 글을 없애고, 다시 돌아가는
 *
 * 멤버변수
 * private long backKeyPressedTime = 0; 뒤로가기 눌렀을떄 카운팅
 * private Toast toast; 토스트
 * private Activity activity; 액티비티
 *
 * 메소드
 * public BackPressHandler(Activity context)
 * 매개변수 : Activity context
 * 주요기능 : 생성자
 * public void onBackPressed()
 * 매개변수 :
 * 주요기능 : 2초 이내로 다시 클릭하지않으면 종료되지않게 함
 * public void showGuide()
 * 매개변수 :
 * 주요기능 : 토스트 알람 울리게 함
 */

public class BackPressHandler {
    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressHandler(Activity context) {
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
            activity.finish();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity, "한번 더 누르시면 작성을중단합니다.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
