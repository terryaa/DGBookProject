package com.frenzy.ebook.dgbook2.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.frenzy.ebook.dgbook2.BackPressHandler;
import com.frenzy.ebook.dgbook2.DAO;
import com.frenzy.ebook.dgbook2.GetnSet.Book;
import com.frenzy.ebook.dgbook2.R;

/**
 * 클래스 이름 : public class FindResultActivity extends AppCompatActivity implements View.OnClickListener
 * 주요 기능 : 결과목록이 FindResultList_Activity에서 불러와지고 이에 대한 상세내용을 확인하기위해서, 새로운 액티비티가 형성되면서 책 정보에 대한 자료 출력.
 *
 * 멤버 변수
 * private DAO dao; : 데이터베이스 추상 객체
 * private TextView book_nameV;  // 데이터베이스에서 받은 책에 대한 정보 중, 책이름 출력 담당
 * private TextView book_compV;  // 데이터베이스에서 받은 책에 대한 정보 중,  출판사 출력 담당
 * private TextView book_writerV;// 데이터베이스에서 받은 책에 대한 정보 중, 지은이 출력 담당
 * private TextView prof_nameV;  // 데이터베이스에서 받은 책에 대한 정보 중, 해당과목 교수님 성함 출력 담당
 * private TextView class_nameV; // 데이터베이스에서 받은 책에 대한 정보 중, 교재를쓰는 해당 강좌명을 출력 담당
 * private TextView book_priceV; // 데이터베이스에서 받은 책에 대한 정보 중, 책의 가격을 출력을 담당
 * private TextView book_typeV;  // 데이터베이스에서 받은 책에 대한 정보 중, 책의 상태에 대해 출력 담당
 * private ImageView imageV;     // 데이터베이스에서 받은 책에 대한 정보 중, 책의 이미지를 출력 담당
 * private Button button_writeComment; // 댓글 넣는 액티비티로 전환
 *
 * 메소드
 * protected void onCreate(Bundle savedInstanceState)
 * 매개변수 : Bundle savedInstanceState
 * 주요 기능 : 뷰의 생성자
 * public void initview()
 * 매개변수 :
 * 주요 기능 : 클래스 내의 객체들을 실체화 시킴
 * public void onBackPressed()
 * 매개변수 :
 * 주요 기능 : 이전버튼을 누르면 이전 액티비티로 돌아감.
 * public void onClick(View v)
 * 매개변수 : View v
 * 주요 기능 : 클릭을 했을 경우, 댓글을 입력하는 액티비티가 형성
 * public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight)
 * 매개변수 : Bitmap bm, int newWidth, int newHeight
 * 주요 기능 : 이미지의 크기를 Resizing
 */

/**
 * 클래스 이름 : class selectBook extends AsyncTask<Integer, Void, Book>
 * 주요 기능 : 책의정보를 비동기적으로 받아와 View에 출력시켜주는 selectBook 클래스
 *
 * 멤버 변수 : ProgressDialog loading;
 *
 * 메소드
 * protected Book doInBackground(Integer... params)
 * 매개변수 : Integer... params
 * 주요 기능 : dao 의 select 메소드로 책정보를 담은 task를 리턴
 * protected void onPostExecute(Book book)
 * 매개변수 : Book book
 * 주요 기능 : 검색된 책에대한 정보를 처리
 * protected void onPreExecute()
 * 매개변수 :
 * 주요 기능 : 다이얼로그를 띄어주고 그 다음 작업으로 넘어가기위한 초석
 *
*/

public class FindResultActivity extends AppCompatActivity implements View.OnClickListener{

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
    private static String owner;
    private Button button_writeComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //액티비티 기본선언
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_result);

        initview();
        //이전 액티비티에서 요쳥한 책의 ID를 받아옴
        /*
        Intent intent=getIntent();
        Integer bookID=intent.getIntExtra("BookID",0);
        */
        //이전 액티비티에서 요쳥한 책의 ID를 받아옴
        Intent intent=getIntent();
        bookID=intent.getIntExtra("bookid",0);


        //책의정보를 불러와 View에 출력시킴
        selectBook selectBook=new selectBook();
        selectBook.execute(bookID);

    }
    public void initview()
    {
        //DAO 초기화
        dao=new DAO();
        //XML상의 View와 View객체 연동
        book_nameV = (TextView) findViewById(R.id.book_name_R);
        book_compV = (TextView) findViewById(R.id.book_comp_R);
        book_writerV = (TextView) findViewById(R.id.book_writer_R);
        prof_nameV = (TextView) findViewById(R.id.prof_name_R);
        class_nameV = (TextView) findViewById(R.id.class_name_R);
        book_priceV = (TextView) findViewById(R.id.book_price_R);
        book_typeV = (TextView) findViewById(R.id.rg_type_R);
        imageV=(ImageView) findViewById(R.id.imageDownloaded);
        button_writeComment=(Button)findViewById(R.id.button_writeComment);

        button_writeComment.setOnClickListener(this);
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
            loading = new ProgressDialog(FindResultActivity.this);
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


}