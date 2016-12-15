package com.frenzy.ebook.dgbook2.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.frenzy.ebook.dgbook2.DAO;
import com.frenzy.ebook.dgbook2.GetnSet.Book;
import com.frenzy.ebook.dgbook2.GetnSet.FindResultListItem;
import com.frenzy.ebook.dgbook2.R;
import com.frenzy.ebook.dgbook2.adapter.FindResultAdapter;
import com.frenzy.ebook.dgbook2.model.FindResultData;

import java.util.ArrayList;
import java.util.List;

/**
 * 클래스 이름 : public class FindResultList_Activity extends AppCompatActivity implements FindResultAdapter.ItemClickCallback
 * 주요 기능 : FindbookActivity에서 입력받은 책 정보를, Recyclerview를 이용하여 리스트방식으로 출력.
 *
 * 멤버변수
 * DAO dao : 데이터베이스의 추상객체
 * private RecyclerView recView : Recyclerview 객체
 * private FindResultAdapter adapter; // recyclerview의 내용을 붙일 adapter
 * private ArrayList listData; // 가변배열로 별다른 메모리 할당없이 유동적으로 메모리 사용량을 조절할수있다.
 *
 * 메소드
 * protected void onCreate(Bundle savedInstanceState)
 * 매개변수 : Bundle savedInstanceState
 * 주요 기능 : 뷰의 생성자
 * public void initview()
 * 매개변수 :
 * 주요 기능 : 클래스 안의 멤버변수들을 할당.
 * public void onBackPressed()
 * 매개변수 :
 * 주요 기능 : 이전버튼을 눌렀을경우 액티비티 스택에서 pop을해, 이전액티비티로 넘어감
 * public void onItemClick(int p)
 * 매개변수 : int p
 * 주요 기능 : 리스트 중, 책 한권에 대한 간략한 정보를 담고 있는 피드를 클릭할 경우, 상세 액티비티가 생기면서
 * public void onSecondaryIconClick(int p)
 * 매개변수 : int p
 * 주요 기능 : 두번째 아이콘 클릭시 취하는 프로시져
 * public void setRecyclerView()
 * 매개변수 :
 * 주요 기능 : Recyclerview의 형성
 * public List<FindResultListItem> getListData(ArrayList<Book> books)
 * 매개변수 : ArrayList<Book>books
 * 주요 기능 : 리스트를 이루는 데이터를 형성하는 메소드
 */

/**
 * 클래스 이름 : class searchBook extends AsyncTask<Book, Void, ArrayList>
 * 주요 기능 : 실질적으로 데이터베이스에서 데이터를 가져오는 클래스
 * 멤버변수
 * ProgressDialog loading : 다이얼로그 로딩화면객체
 * 메소드
 * protected void onPreExecute()
 * 매개변수 :
 * 주요 기능 : 다이얼로그를 만들어주고, doInBackground 로 넘어가서 실질적인 도서검색을 진행함
 * protected void onPostExecute(ArrayList books)
 * 매개변수 :
 * 주요 기능 : 데이터베이스를 통해 도서 검색을 하고 해당사항에 대한 정보를 토스트와 함께 출력
 * protected ArrayList doInBackground(Book... params)
 * 매개변수 :
 * 주요 기능 : 실질적인 데이터베이스에 접속해 데이터를 검색하는 메소
 */


public class FindResultList_Activity extends AppCompatActivity implements FindResultAdapter.ItemClickCallback{

    private DAO dao;
    private RecyclerView recView;
    private FindResultAdapter adapter;
    private ArrayList listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_result_list);

        initview();

        Intent intent=getIntent();
        Book book=(Book) intent.getSerializableExtra("book");
        searchBook task=new searchBook();
        task.execute(book);


    }
    public void initview()
    {
        recView=(RecyclerView)findViewById(R.id.findresult_rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));
        dao=new DAO();
    }
    @Override
    public void onBackPressed() {
        Intent BackIntent = new Intent(this,FindBookActivity.class);
        startActivity(BackIntent);
        finish();
    }

    @Override
    public void onItemClick(int p) {
        FindResultListItem item=(FindResultListItem)listData.get(p);
        if(item.getProgress()==1)
            Toast.makeText(getApplicationContext(), "이미 판매된 책입니다.", Toast.LENGTH_LONG).show();
        else if(item.getProgress()==2)
            Toast.makeText(getApplicationContext(), "대여중인 책입니다.", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(this,FindResultActivity.class);
        intent.putExtra("bookid",item.getBookid());
        startActivity(intent);
    }

    @Override
    public void onSecondaryIconClick(int p) {

    }
    public void setRecyclerView()
    {
        adapter=new FindResultAdapter(listData,this);
        recView.setAdapter(adapter);
        adapter.setItemClickCallback(this);
    }
    public List<FindResultListItem> getListData(ArrayList<Book> books) {
        List<FindResultListItem> data = new ArrayList<>();

        int icons = (android.R.drawable.ic_popup_reminder);
        //4번 반복 스크롤뷰 하려고
        //recyclerView
        //create Listitem with dummy data, then add to list
        for (int i = 0; i <books.size(); i++) {
            FindResultListItem item = new FindResultListItem();
            item.setImageResId(icons);
            item.setTitle(books.get(i).getTitle());
            item.setWriter(books.get(i).getAuthor());
            item.setCompany(books.get(i).getCompany());
            item.setType(books.get(i).getType());
            item.setBitmap(books.get(i).getBitmap());
            item.setBookid(books.get(i).getBookID());
            item.setProgress(books.get(i).getProgress());
            data.add(item);
        }

        return data;
    }

    class searchBook extends AsyncTask<Book, Void, ArrayList> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = new ProgressDialog(FindResultList_Activity.this);
            loading.setMessage("검색중입니다!!");
            loading.setProgressStyle(loading.STYLE_SPINNER);
            loading.show();

        }

        //검색된 책에대한 정보를 처리
        @Override
        protected void onPostExecute(ArrayList books) {
            super.onPostExecute(books);
            loading.dismiss();
            //책정보가 성공적으로 검색되었다면
            if(books!=null) {
                listData=(ArrayList) getListData(books);
                setRecyclerView();
                Toast.makeText(getApplicationContext(), "책 정보검색완료!", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(getApplicationContext(), "책정보 불러오기 실패", Toast.LENGTH_LONG).show();


        }

        @Override
        protected ArrayList doInBackground(Book... params) {
            //dao 의 select 메소드로 책정보를 담은 task를 리턴
            ArrayList<Book> task;
            task=dao.search(params[0]);
            return task;
        }
    }

}
