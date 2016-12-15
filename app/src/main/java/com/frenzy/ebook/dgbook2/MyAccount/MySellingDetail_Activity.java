package com.frenzy.ebook.dgbook2.MyAccount;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.frenzy.ebook.dgbook2.Activity.CommentActivity;
import com.frenzy.ebook.dgbook2.Activity.EnrollBookActivity;
import com.frenzy.ebook.dgbook2.Activity.FindResultActivity;
import com.frenzy.ebook.dgbook2.Activity.MainmenuActivity;
import com.frenzy.ebook.dgbook2.DAO;
import com.frenzy.ebook.dgbook2.GetnSet.Book;
import com.frenzy.ebook.dgbook2.R;

/**
 클래스명 : public class MySellingDetail_Activity extends AppCompatActivity
 주된기능 : 내가 판 책 목록 중 하나를 터치했을 때 이 액티비티를 통해 해당 도서의 상세정보를 보여준다.
 멤버변수 :

 메소드 :
 protected void onCreate(Bundle savedInstanceState)
 * 매개변수: Bundle savedInstanceState
 * 기능 : 뷰의 생성자
 */


public class MySellingDetail_Activity extends AppCompatActivity implements View.OnClickListener {

    //필요변수선언
    private DAO dao;
    private TextView book_nameV;
    private TextView book_compV;
    private TextView book_writerV;
    private TextView prof_nameV;
    private TextView class_nameV;
    private TextView book_priceV;
    private TextView book_typeV;
    private ImageView imageV;
    private Integer bookID;
    private Integer type;
    private static String owner;
    private Button button_writeComment;
    private Button button_selled;
    private Button button_lend_start;
    private Button button_lend_finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //액티비티 기본선언
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_selling_detail_);

        initview();
        //이전 액티비티에서 요쳥한 책의 ID를 받아옴
        /*
        Intent intent=getIntent();
        Integer bookID=intent.getIntExtra("BookID",0);
        */
        //이전 액티비티에서 요쳥한 책의 ID를 받아옴
        Intent intent=getIntent();
        bookID=intent.getIntExtra("bookid",0);
        type=intent.getIntExtra("type",0);


        //책의정보를 불러와 View에 출력시킴
       selectBook selectBook=new selectBook();
        selectBook.execute(bookID);

    }
    public void initview()
    {
        //DAO 초기화
        dao=new DAO();
        //XML상의 View와 View객체 연동
        book_nameV = (TextView) findViewById(R.id.lbl_Sitem_title);
        book_compV = (TextView) findViewById(R.id.lbl_Sitem_company);
        book_writerV = (TextView) findViewById(R.id.lbl_Sitem_writer);
        prof_nameV = (TextView) findViewById(R.id.lbl_Sitem_prof);
        class_nameV = (TextView) findViewById(R.id.lbl_Sitem_writer);
        book_priceV = (TextView) findViewById(R.id.lbl_Sitem_price);
        book_typeV = (TextView) findViewById(R.id.lbl_Sitem_price);
        imageV=(ImageView) findViewById(R.id.imageDownloaded);

        button_writeComment=(Button)findViewById(R.id.button_writeComment);
        button_selled=(Button)findViewById(R.id.button_selled);
        button_lend_start=(Button)findViewById(R.id.button_lend_start);
        button_lend_finish=(Button)findViewById(R.id.button_lend_finish);
        button_writeComment.setOnClickListener(this);
        button_selled.setOnClickListener(this);
        button_lend_start.setOnClickListener(this);
        button_lend_finish.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v == button_writeComment)
        {
            Intent intent = new Intent(getApplicationContext(),CommentActivity.class);
            intent.putExtra("BookID",bookID);
            intent.putExtra("Owner",owner);

            startActivity(intent);
            finish();
        }
        else if(v==button_selled)
        {
            if(type==0)
                Toast.makeText(getApplicationContext(), "대여로 등록된 책입니다!", Toast.LENGTH_LONG).show();
            else {
                buttonSell buttonSell = new buttonSell();
                buttonSell.execute(bookID);
            }
        }
        else if(v==button_lend_start)
        {
            if(type==1)
                Toast.makeText(getApplicationContext(), "판매로 등록된 책입니다!", Toast.LENGTH_LONG).show();
            else {
                buttonLendStart buttonLendStart = new buttonLendStart();
                buttonLendStart.execute(bookID);
            }
        }
        else if(v==button_lend_finish)
        {

            if(type==1)
                Toast.makeText(getApplicationContext(), "판매로 등록된 책입니다!", Toast.LENGTH_LONG).show();
            else {
                buttonLendFinish buttonLendFinish = new buttonLendFinish();
                buttonLendFinish.execute(bookID);
            }
        }
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }



    //책의정보를 비동기적으로 받아와 View에 출력시켜주는 selectBook 클래스
    class selectBook extends AsyncTask<Integer, Void, Book> {
        ProgressDialog loading;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = new ProgressDialog(MySellingDetail_Activity.this);
            loading.setMessage("검색중입니다!!");
            loading.setProgressStyle(loading.STYLE_SPINNER);
            loading.show();
        }

        //검색된 책에대한 정보를 처리
        @Override
        protected void onPostExecute(Book book) {
            super.onPostExecute(book);
            loading.dismiss();
            //책정보가 성공적으로 검색되었다면
            if(book!=null) {
                owner=book.getOwner();
                book_nameV.setText(book.getTitle());
                book_compV.setText(book.getCompany());
                book_writerV.setText(book.getAuthor());
                prof_nameV.setText(book.getProfessor());
                class_nameV.setText(book.getCourse());
                book_priceV.setText(String.valueOf(book.getPrice()));
                if (book.getType() == 1)
                    book_typeV.setText("판매");
                else
                    book_typeV.setText("대여");
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap bitmap=getResizedBitmap(book.getBitmap(),450,300);
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);
                imageV.setImageBitmap(rotatedBitmap);
                Toast.makeText(getApplicationContext(), "책 정보검색완료!", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(getApplicationContext(), "책정보 불러오기 실패", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Book doInBackground(Integer... params) {
            //dao 의 select 메소드로 책정보를 담은 task를 리턴
            Book task;
            task=dao.select(params[0]);
            return task;
        }
    }
    class buttonSell extends AsyncTask<Integer, Void, String> {
        ProgressDialog loading;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = new ProgressDialog(MySellingDetail_Activity.this); // 객체 형성을 해준후
            loading.setTitle("잠시만요!!"); // 타이틀로 잠시만요!
            loading.setMessage("확인좀할게요~"); // 체크좀 할게요~ 로 바꾸고
            loading.setProgressStyle(ProgressDialog.STYLE_SPINNER); // 그 옆에 동그라미
            loading.setCanceledOnTouchOutside(false); // 이건 로그인체크하고있는데 다른데 누르면 쓰레드가 종료되는것을 방지
            loading.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();

            if(s=="success")
            {
                Toast.makeText(getApplicationContext(), "완료!", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(getApplicationContext(), "오류", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Integer... params) {
            boolean task=dao.sellbook(params[0]);
            if(task==true)
                return "success";
            else
                return "failure";
        }
    }
    class buttonLendStart extends AsyncTask<Integer, Void, String> {
        ProgressDialog loading;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = new ProgressDialog(MySellingDetail_Activity.this); // 객체 형성을 해준후
            loading.setTitle("잠시만요!!"); // 타이틀로 잠시만요!
            loading.setMessage("확인좀할게요~"); // 체크좀 할게요~ 로 바꾸고
            loading.setProgressStyle(ProgressDialog.STYLE_SPINNER); // 그 옆에 동그라미
            loading.setCanceledOnTouchOutside(false); // 이건 로그인체크하고있는데 다른데 누르면 쓰레드가 종료되는것을 방지
            loading.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();

            if(s=="success")
            {
                Toast.makeText(getApplicationContext(), "완료!", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(getApplicationContext(), "오류", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Integer... params) {
            boolean task=dao.lendstartbook(params[0]);
            if(task==true)
                return "success";
            else
                return "failure";
        }
    }
    class buttonLendFinish extends AsyncTask<Integer, Void, String> {
        ProgressDialog loading;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = new ProgressDialog(MySellingDetail_Activity.this); // 객체 형성을 해준후
            loading.setTitle("잠시만요!!"); // 타이틀로 잠시만요!
            loading.setMessage("확인좀할게요~"); // 체크좀 할게요~ 로 바꾸고
            loading.setProgressStyle(ProgressDialog.STYLE_SPINNER); // 그 옆에 동그라미
            loading.setCanceledOnTouchOutside(false); // 이건 로그인체크하고있는데 다른데 누르면 쓰레드가 종료되는것을 방지
            loading.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();

            if(s=="success")
            {
                Toast.makeText(getApplicationContext(), "완료!", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(getApplicationContext(), "오류", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Integer... params) {
            boolean task=dao.lendfinishbook(params[0]);
            if(task==true)
                return "success";
            else
                return "failure";
        }
    }

}
