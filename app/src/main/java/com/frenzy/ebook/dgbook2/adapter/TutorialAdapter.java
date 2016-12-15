package com.frenzy.ebook.dgbook2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.frenzy.ebook.dgbook2.Tutorial.BookSearchTutorial;
import com.frenzy.ebook.dgbook2.Tutorial.CLConnectTutorial;
import com.frenzy.ebook.dgbook2.Tutorial.EndTutorial;
import com.frenzy.ebook.dgbook2.Tutorial.EnrollTutorial;
import com.frenzy.ebook.dgbook2.Tutorial.MessageTutorial;
import com.frenzy.ebook.dgbook2.Tutorial.MyAccountTutorial;

/**
 * 클래스 이름 : public class TutorialAdapter extends FragmentPagerAdapter
 * 주요 기능 : 튜토리얼에 해당되는 각 프레그먼트들을 하나로 모아 덩어리 화하는 클래스
 *
 * 멤버변수  private final int NUM_ITEMS = 6; //뷰페이저공간을 할당
 *
 * 메소드
 * public TutorialAdapter(FragmentManager fm)
 * 매개변수 : FragmentManager fm
 * 주요기능 : 생성자
 * public int getCount()
 * 매개변수 :
 * 주요기능 : 아이템의 갯수를 얻는것
 * public Fragment getItem(int position)
 * 매개변수 : int position
 * 주요기능 : 실제프레그먼트들을 하나로 묶는 메소드
 */
public class TutorialAdapter extends FragmentPagerAdapter {
    private final int NUM_ITEMS = 6;//페이저공간7개 만들어주고

    public TutorialAdapter(FragmentManager fm) {
        super(fm);
    } //생성자

    public int getCount() {
        return NUM_ITEMS;
    } // getter

    public Fragment getItem(int position) { // 연속된수를가지고 튜토리얼 진행

        if (position == 0)
            return EnrollTutorial.newInstance();
        else if (position == 1)
            return BookSearchTutorial.newInstance();
        else if (position == 2)
            return CLConnectTutorial.newInstance();
        else if (position == 3)
            return MyAccountTutorial.newInstance();
        else if(position==4)
            return MessageTutorial.newInstance();
        else if(position==5)
            return EndTutorial.newInstance();
        return null;
    }
}