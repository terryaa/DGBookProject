package com.frenzy.ebook.dgbook2.Activity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.frenzy.ebook.dgbook2.BackPressHandler;
import com.frenzy.ebook.dgbook2.GetnSet.Book;
import com.frenzy.ebook.dgbook2.DAO;
import com.frenzy.ebook.dgbook2.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


/**
 * 클래스명 : public class EnrollBookActivity extends AppCompatActivity implements View.OnClickListener
 * 주된기능 : 판매자의 도서정보를 입력을 시키면 Web Server의 DB에 업로드 되는 기능
 *
 * 멤버변수
 * private RadioButton rb :  라디오 버튼
 * private RadioGroup rg_Type : 라디오버튼의 그룹
 * private DAO dao // 데이터베이스의 추상객체
 * private static final int PICK_FROM_CAMERA = 0; : 사진찍기에서 카메라도 바로 촬영을 진행할 경우, 0
 * private static final int PICK_FROM_ALBUM = 1; : 사진을 앨범에서 가져올 경우 1
 * private static final int CROP_FROM_CAMERA = 2; : 사진을 카메라로부터 크롭할 경우(잘라내기)는 2
 * private EditText book_name; : 책 이름
 * private EditText book_comp; : 책 출판사
 * private EditText book_writer; : 책 저자
 * private EditText prof_name; : 담당 강좌 교수님 성함
 * private EditText class_name; : 강좌 이름
 * private EditText book_price; : 책 가격
 * private Uri mImageCaptureUri; : 카메라로든 앨범으로든 크롭된 이미지를 담는 경로
 * private ImageView mPhotoImageView; // 크롭한 이미지를 올림
 * private Button mButton; // 0,1,2중에서 하나 고를 수 있게함
 * private String photo_str; // 사진을 불러와서 스트링형태로 저장
 * private Bitmap bitmap; // 비트맵 객체
 * private BackPressHandler backPressHandler; // 작성하다말고 도중에 취소를 눌렀을경우 액티비티를 벗어나는 것 을 방지하는 객체
 * private Button button_enroll; // 책등록 버튼
 * private int radio; : //라디오버튼 그룹
 * private int Type;  : 입력된 라디오버튼의 값을 담는곳
                
 * 메소드
 * protected void onCreate(Bundle savedInstanceState)
 * 매개변수 : Bundle savedInstanceState
 * 기능 : EnrollBookActivity의 뷰 생성자
 * public void initview()
 * 매개변수 :
 * 기능 : 객체를 형성해주고 기본적인 초기값 설정
 * private void doTakePhotoAction()
 * 매개변수 :
 * 기능 : 카메라에서 이미지 가져오기
 * private void doTakeAlbumAction()
 * 매개변수 :
 * 기능 : 앨범에서 이미지 가져오기
 * protected void onActivityResult(int requestCode, int resultCode, Intent data)
 * 매개변수 :
 * 기능 : 이미지를 앨범 혹은 카메라에서 가져와 크롭하는 과정
 * public void onClick(View v)
 * 매개변수 : View v
 * 기능 : 버튼을 클릭했을때 하나하나 setOnClickListener로 하기엔 가독성이 좋지 않아 하나로 묶어놓음
 * public void onBackPressed()
 * 매개변수 :
 * 기능 : 작성도중 실수로 취소를 눌렀을때 데이터 손실을 방지하는 인텐트 변환방지 메소드
 * public String getStringImage(Bitmap bitmap)
 * 매개변수 :
 * 기능 : 비트맵이미지를 스트링이미지로 변환하는 과정
 * */

/**
 * 클래스 명 : class InsertBook extends AsyncTask<Book, Void, String>
 * 주된 기능 : DB에 데이터를 올리는 실질적인 기능을 수행
 * 멤버 변수 : ProgressDialog loading( DB에 올라가는 도중 사용자에게 정보를 제공하기 위해 알림메세징 객체)
 * 메소드
 * protected void onPreExecute()
 * 매개변수 :
 * 기능 : 다이얼로그를 띄우는 역할
 * protected void onPreExecute()
 * 매개변수 :
 * 기능 : 다이얼로그를 없애고 성공 유무에 따라 토스트 메세지를 날려주는 메소드
 * protected String doInBackground(Book... params)
 * 매개변수 :
 * 기능 : 실질적으로 데이터를 DB에 올리는 과정
 *
 */

public class EnrollBookActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioButton rb;
    private RadioGroup rg_Type;
    private DAO dao;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private EditText book_name;
    private EditText book_comp;
    private EditText book_writer;
    private EditText prof_name;
    private EditText class_name;
    private EditText book_price;
    private Uri mImageCaptureUri;
    private ImageView mPhotoImageView;
    private Button mButton;
    private String photo_str;
    private Bitmap bitmap;
    private BackPressHandler backPressHandler;
    private Button button_enroll;
    private int radio;
    private int Type;
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;
    public Bitmap photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_book);

        pref = getSharedPreferences("Univtable", MODE_PRIVATE);
        prefEditor = pref.edit();

        initview();

    }

    public void initview()
    {
        dao = new DAO();
        mButton = (Button) findViewById(R.id.btn_UploadPicture);
        mPhotoImageView = (ImageView) findViewById(R.id.user_image);
        mButton.setOnClickListener(this);

        book_name = (EditText) findViewById(R.id.book_name);
        book_comp = (EditText) findViewById(R.id.book_comp);
        book_writer = (EditText) findViewById(R.id.book_writer);
        prof_name = (EditText) findViewById(R.id.prof_name);
        class_name = (EditText) findViewById(R.id.class_name);
        book_price = (EditText) findViewById(R.id.book_price);
        backPressHandler = new BackPressHandler(this);
        rg_Type = (RadioGroup) findViewById(R.id.rgType);
        button_enroll = (Button) findViewById(R.id.button_enroll);
        rb = (RadioButton) findViewById(radio);
        button_enroll.setOnClickListener(this);
    }

    private void doTakePhotoAction()
    {
    /*
     * 참고 해볼곳
     * http://2009.hfoss.org/Tutorial:Camera_and_Gallery_Demo
     * http://stackoverflow.com/questions/1050297/how-to-get-the-url-of-the-captured-image
     * http://www.damonkohler.com/2009/02/android-recipes.html
     * http://www.firstclown.us/tag/android/
     */

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        // 특정기기에서 사진을 저장못하는 문제가 있어 다음을 주석처리 합니다.
        //intent.putExtra("return-data", true);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    private void doTakeAlbumAction()
    {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode != RESULT_OK)
        {
            return;
        }

        switch(requestCode)
        {
            case CROP_FROM_CAMERA:
            {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                final Bundle extras = data.getExtras();

                //사진을 불러와 View에 넣은 후, Bitmap이미지를 Base64형태의 String으로 바꿈
                if(extras != null)
                {
                    photo = extras.getParcelable("data");
                    mPhotoImageView.setImageBitmap(photo);
                }

                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists())
                {
                    f.delete();
                }

                break;
            }

            case PICK_FROM_ALBUM:
            {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.

                mImageCaptureUri = data.getData();
                //크롭되지 않은 이미지 저장
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageCaptureUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            case PICK_FROM_CAMERA:
            {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.


                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("outputX", 130);
                intent.putExtra("outputY", 130);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_CAMERA);

                break;
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        if(v==mButton) {
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakePhotoAction();
                }
            };

            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakeAlbumAction();
                }
            };

            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };

            new AlertDialog.Builder(this)
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("사진촬영", cameraListener)
                    .setNeutralButton("앨범선택", albumListener)
                    .setNegativeButton("취소", cancelListener)
                    .show();
        }
        else if(v==button_enroll)
        {
            int Type=-1;
            if((book_name.getText().toString()==null)||(book_comp.getText().toString()==null)
                || (book_writer.getText().toString()==null)|| (prof_name.getText().toString()==null)
            ||(class_name.getText().toString()==null)|| (book_price.getText().toString()==null)
                    || (photo==null))
            {
                Toast.makeText(getApplicationContext(), "모든 정보를 입력하세요", Toast.LENGTH_LONG).show();
            }
            radio = rg_Type.getCheckedRadioButtonId();
            if(radio!=-1)
            {
                rb = (RadioButton) findViewById(radio);
                String Types = rb.getText().toString();
                if (Types.contains("판매"))
                    Type = 1;
                else
                    Type = 0;
                try {
                    Book book = new Book(book_name.getText().toString(), book_comp.getText().toString(),
                            book_writer.getText().toString(), prof_name.getText().toString(), class_name.getText().toString(),
                            Integer.valueOf(book_price.getText().toString()), Type,getStringImage(photo),
                            null,pref.getString("id","Load failed"));
                    InsertBook task=new InsertBook();
                    task.execute(book); // execute ㅇㄷ??
                    //여기도 마찬가지로 같이onPreExecute->doinbackground->onPostEx; 이런식으로 진행
                }catch (Exception e) {
                    //return new String("Exception: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Price에는 숫자만 입력가능합니다.", Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "판매/대여를 선택해주세요", Toast.LENGTH_LONG).show();
            }
        }
    }

    class InsertBook extends AsyncTask<Book, Void, String> {
        ProgressDialog loading;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = new ProgressDialog(EnrollBookActivity.this); // 객체 형성을 해준후
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
                Toast.makeText(getApplicationContext(), "책등록 완료!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainmenuActivity.class);
                startActivity(intent);
            }
            else
                Toast.makeText(getApplicationContext(), "책등록 오류", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Book... params) {
            boolean task=dao.insert(params[0]);
            if(task==true)
                return "success";
            else
                return "failure";
        }
    }
    public String getStringImage(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,15, stream); //compress to which format you want.
        byte [] byte_arr = stream.toByteArray();
        String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
        bitmap.recycle();
        return image_str;
    }

    @Override
    public void onBackPressed() {
        backPressHandler.onBackPressed();
    }
}
