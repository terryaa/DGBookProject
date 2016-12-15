package com.frenzy.ebook.dgbook2.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.frenzy.ebook.dgbook2.DAO;
import com.frenzy.ebook.dgbook2.GetnSet.CommentListItem;
import com.frenzy.ebook.dgbook2.R;
import com.frenzy.ebook.dgbook2.adapter.CommentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
    클래스명 : public class CommentActivity extends AppCompatActivity implements View.OnClickListener,CommentAdapter.ItemClickCallback
    주된기능 : 각 도서마다 판매자와 구매자간의 보다 원할한 거래를 위해 공개대화방형식으로 댓글을 달아 소통을 할 수 있게 구현 함

    멤버변수 : RecyclerView recView(리싸이클러뷰 객체),
             CommentAdapter adapter(댓글들을 하나로 묶는 어뎁터),
             ArrayList listData(댓글을 뿌렸을때 연속적인 나열을 나타내는 가변배열),
             Button button_comment(댓글입력을 마친 후 전송할때 쓰는 버튼)

    메소드 : protected void onCreate(Bundle savedInstanceState)
             * 매개변수: Bundle savedInstanceState
             * 기능 : 뷰의 생성자
            public void initview() : 뷰 안에 있는 멤버변수의 초기화
             * 매개변수 :
             * 기능 : 뷰 안에 있는 멤버변수의 초기화
            public void onBackPressed()
             * 매개변수 :
             * 기능 : 이전버튼을 눌렀을경우 이전화면으로 돌아갈수있게 구현
            public void onItemClick(int p)
             * 매개변수 : int p
             * 기능 : 리싸이클러뷰 기반 전용메소드이며, 예를 들어 댓글을 누르면 상세정보가 출력이 되게 하는 메소드인데, 댓글자체로 보여줄 예정이라 빈 메소드로 정의
            public void onClick(View v)
             * 매개변수 : View v
             * 기능 : 댓글을 입력하면 이에대한 확인을 토스트 메시징을 통해 확인가능
*/

import com.frenzy.ebook.dgbook2.GetnSet.Comment;


public class CommentActivity extends AppCompatActivity implements CommentAdapter.ItemClickCallback{



    private RecyclerView recView;
    private CommentAdapter adapter;
    private ArrayList listData;
    private Integer bookID;
    private String owner;
    private DAO dao;

    private EditText content;

    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        content=(EditText) findViewById(R.id.edit_comment);

        dao=new DAO();

        recView=(RecyclerView)findViewById(R.id.comment_rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));

        Button button_comment= (Button)findViewById(R.id.button_comment);

        pref = getSharedPreferences("Univtable", MODE_PRIVATE);
        prefEditor = pref.edit();

        Intent intent=getIntent();
        bookID=intent.getIntExtra("BookID",0);
        owner=intent.getStringExtra("Owner");

        button_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Comment comment=new Comment(pref.getString("name","fail"),owner,content.getText().toString(),
                //        bookID);

                Comment comment=new Comment(pref.getString("id","fail"),owner,content.getText().toString(),
                        bookID,pref.getString("name","fail"));

                insertComment insertComment=new insertComment();
                insertComment.execute(comment);
            }
        });


        selectComments selectComments=new selectComments();
        selectComments.execute(bookID);


    }
    class selectComments extends AsyncTask<Integer, Void, ArrayList<Comment>> {
        ProgressDialog loading;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = new ProgressDialog(CommentActivity.this);
            loading.setMessage("검색중입니다!!");
            loading.setProgressStyle(loading.STYLE_SPINNER);
            loading.show();
        }

        //검색된 책에대한 정보를 처리
        @Override
        protected void onPostExecute(ArrayList<Comment> comments) {
            super.onPostExecute(comments);
            loading.dismiss();
            //책정보가 성공적으로 검색되었다면
            if(comments!=null) {
                listData=(ArrayList) getListData(comments);
                setRecyclerView();
            }
            else
                Toast.makeText(getApplicationContext(), "첫 댓글을 달아주세요!", Toast.LENGTH_LONG).show();
        }


        @Override
        protected ArrayList<Comment> doInBackground(Integer... params) {
            //dao 의 select 메소드로 책정보를 담은 task를 리턴
            ArrayList<Comment> task=null;
            task=dao.selectcomment(params[0]);
            return task;
        }
    }
    class insertComment extends AsyncTask<Comment, Void, ArrayList<Comment>> {
        ProgressDialog loading;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = new ProgressDialog(CommentActivity.this);
            loading.setMessage("입력중입니다!!");
            loading.setProgressStyle(loading.STYLE_SPINNER);
            loading.show();
        }

        //검색된 책에대한 정보를 처리
        @Override
        protected void onPostExecute(ArrayList<Comment> result) {
            super.onPostExecute(result);
            loading.dismiss();
            //책정보가 성공적으로 검색되었다면
            if (result != null) {
                listData = (ArrayList) getListData(result);
                setRecyclerView();
                Toast.makeText(getApplicationContext(), "댓글 입력 성공!", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(getApplicationContext(), "댓글 입력 실패!", Toast.LENGTH_LONG).show();
        }


        @Override
        protected ArrayList<Comment> doInBackground(Comment... params) {
            //dao 의 select 메소드로 책정보를 담은 task를 리턴
            boolean task;
            task=dao.insertcomment(params[0]);
            ArrayList<Comment> task2=null;
            if(task==true) {
                task2 = dao.selectcomment(bookID);
                return task2;
            }
            else {
                task2 = null;
                return task2;
            }



        }
    }
    public List<CommentListItem> getListData(ArrayList<Comment> comments) {
        List<CommentListItem> data = new ArrayList<>();

        int icons = (android.R.drawable.ic_popup_reminder);
        //4번 반복 스크롤뷰 하려고
        //recyclerView
        //create Listitem with dummy data, then add to list
        for (int i = 0; i <comments.size(); i++) {
            CommentListItem item = new CommentListItem();
            item.setCommentid(comments.get(i).getCommentid());
            item.setAuthor(comments.get(i).getAuthor());
            item.setContent(comments.get(i).getContent());
            item.setOwner(comments.get(i).getOwner());
            item.setBookid(comments.get(i).getBookid());
            item.setTime(comments.get(i).getTime());
            item.setName(comments.get(i).getName());
            data.add(item);
        }

        return data;
    }

    @Override
    public void onItemClick(int p) {
    }

    @Override
    public void onSecondaryIconClick(int p) {

    }
    public void setRecyclerView()
    {
        adapter=new CommentAdapter(listData,this);
        recView.setAdapter(adapter);
        adapter.setItemClickCallback(this);
    }

}



