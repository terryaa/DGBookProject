package com.frenzy.ebook.dgbook2.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.frenzy.ebook.dgbook2.BackPressCloseHandler;
import com.frenzy.ebook.dgbook2.MyAccount.MyDealingActivity;
import com.frenzy.ebook.dgbook2.MyAccount.MySellingActivity;
import com.frenzy.ebook.dgbook2.R;

/**
 * 클래스 이름 : public class MainmenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener
 * 주요 기능 : 로그인을 마친상태에서 제일 맨 처음에 뜨는 화면으로, 여러가지 작업을 실행 할 수 있게 한 실질적 메인
 *
 * 멤버변수
 * private SharedPreferences pref; // 파일에 나의 정보를 저장
 * private SharedPreferences.Editor prefEditor; : 파일을 다룰 수 있는 에디터
 * BackPressCloseHandler backPressCloseHandler; // 뒤로가기 버튼 두번눌렀을경우 프로세스를 종료시키는 객체 형성
 * private Button book_enroll; // 도서 등록 버튼을 누르면 액티비티 전환
 * private Button book_search; // 도서 검색 버튼을 누르면 액티비티 전환
 * private Button book_call; // 다향관에 전화버튼을 누르면 액티비티 전환
 * private Button book_library; // 도서관버튼을 누르면 액티비티 전환
 *
 * 메소드
 * protected void onCreate(Bundle savedInstanceState)
 * 매개변수 : Bundle savedInstanceState
 * 주요기능 : 뷰 생성자
 * public void onBackPressed()
 * 매개변수 :
 * 주요기능 : 연속으로 뒤로가기버튼을 눌렀을 경우 앱을 종료시키는 메소드
 * public boolean onNavigationItemSelected(MenuItem item)
 * 매개변수 : MenuItem item
 * 주요기능 : 네비게이션 바를 불러오고 그에 따른 아이템, 즉 액티비티를 누르면, 내가 판매한 책 내가 거래중인 책들의 리스트들을 볼수있다.
 * public void initview()
 * 매개변수 :
 * 주요기능 : 클래스 내의 변수들의 실체화
 * public void onClick(View v)
 * 매개변수 : View v
 * 주요기능 : 버튼들을 클릭했을 때, 액티비티의 전환을 담당하는 메소드
 * public void onLogout()
 * 매개변수 :
 * 주요기능 : SharedPreference를 이용해 만든 파일의 Key와 Value 값을 바꿔 로그아웃기능 구현
 *
 */


public class MainmenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;

    BackPressCloseHandler backPressCloseHandler; // 뒤로가기 버튼 두번눌렀을경우 프로세스를 종료시키는 객체 형성
    private Button book_enroll;
    private Button book_search;
    private Button book_call;
    private Button book_library;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        pref = getSharedPreferences("Univtable", MODE_PRIVATE);
        prefEditor = pref.edit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // 툴바 객체 형성
        setSupportActionBar(toolbar); // 툴바
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initview();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); //옆에 네비게이터가 있는지없는지 검사
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START); //있으면 네비게이터 종료
        } else {
            backPressCloseHandler.onBackPressed(); //일반 메인메뉴액티비티상황일경우 두번뒤로가기 종료 실행
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        AppCompatActivity appCompatActivity = null;
        int id = item.getItemId();

        if (id == R.id.SellingBook) {
            Intent intent=new Intent(getApplicationContext(),MySellingActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.DealingBook) {
            Intent intent=new Intent(getApplicationContext(),MyDealingActivity.class);
            startActivity(intent);
            finish();
        } else if(id==R.id.logout)
        {
            onLogout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initview()
    {

        book_enroll = (Button) findViewById(R.id.book_enroll); //서적등록버튼
        book_search = (Button) findViewById(R.id.book_search); //도서검색버튼
        book_call = (Button) findViewById(R.id.book_call); //내가판매한책
        book_library = (Button) findViewById(R.id.book_library); //중앙도서관링크

        book_enroll.setOnClickListener(this);
        book_search.setOnClickListener(this);
        book_call.setOnClickListener(this);
        book_library.setOnClickListener(this);



        backPressCloseHandler = new BackPressCloseHandler(this); // 두번뒤로가기종료 객체 형성
    }

    @Override
    public void onClick(View v) {
        if(v==book_enroll)
        {
            Intent intent = new Intent(getApplicationContext(),EnrollBookActivity.class);
            startActivity(intent);
            finish();
        }
        else if(v==book_search)
        {
            Intent intent = new Intent(getApplicationContext(),FindBookActivity.class);
            startActivity(intent);
            finish();
        }
        else if(v==book_call)
        {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:02-2260-8964"));
            startActivity(intent);
            finish();
        }
        else if(v==book_library)
        {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mlib.dongguk.edu"));
            startActivity(intent);
        }
    }

    public void onLogout(){
        prefEditor.putBoolean("auto", false);
        prefEditor.putString("id", "#");
        prefEditor.putString("pw", "#");
        prefEditor.putString("name", "#");
        prefEditor.commit();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
