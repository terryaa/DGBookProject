package com.frenzy.ebook.dgbook2.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frenzy.ebook.dgbook2.R;
import com.frenzy.ebook.dgbook2.GetnSet.MySellingListItem;

import java.util.List;

/**
 * Created by 민경 on 2016-11-13.
 * derpData를 여기로 받아
 */

/**
 클래스명 : public class MySellingAdapter extends RecyclerView.Adapter<MySellingAdapter.DerpHolder>
 주된기능 : MySellingData의 정보를 어댑터를 통해 받아들인다.
 멤버변수 :
 private List<MySellingListItem> listData : 내가 판 책들을 하나의 가변배열에 저장한것
 private LayoutInflater inflater : xml의 layout을 읽어 실제 뷰를 생성하는 객체
 private ItemClickCallback itemClickCallback :

 메소드 :
 public void setItemClickCallback(final ItemClickCallback itemClickCallback)
 * 매개변수: final ItemClickCallback itemClickCallback
 * 기능 : 아이템을 눌렀을 때 반응함
 public MySellingAdapter(List<MySellingListItem> listData, Context c)
 * 매개변수 :List<MySellingListItem> listData, Context c
 * 기능 : MySellingListItem의 데이터를 가지고 뷰를 생성
 public DerpHolder onCreateViewHolder(ViewGroup parent, int viewType)
 * 매개변수 : ViewGroup parent, int viewType
 * 기능 : viewholder를 생성
 public void onBindViewHolder(DerpHolder holder, int position)
 * 매개변수 : DerpHolder holder, int position
 * 기능 : 만들어진 ViewHolder에 데이터를 넣는 작업
 public int getItemCount()
 * 매개변수 :
 * 기능 : 데이터 갯수 반환 */

/**
 클래스명 : class DerpHolder extends RecyclerView.ViewHolder implements View.OnClickListener
 주된기능 : 보여지는 텍스트와 이미지뷰를 컨테이너에 묶고 이를 클릭했을 시 반응한다.
 멤버변수 :
 private TextView title : 책제목 텍스트뷰
 private TextView writer : 저자 텍스트뷰
 private TextView company : 출판사 텍스트뷰
 private ImageView thumnail : 책사진 썸네일 이미지뷰
 private View container : 위의 뷰들을 묶는 컨테이너 뷰

 메소드 :
 public DerpHolder(View itemView)
 * 매개변수 : DView itemView
 * 기능 : 컨테이너의 뷰들과 listitem xml파일의 객체들을 연결시킨다.
 public void onClick(View v)
 * 매개변수 : View v
 * 기능 : 버튼을 클릭했을때 하나하나 setOnClickListener로 하기엔 가독성이 좋지 않아 하나로 묶어놓음
 */


public class MySellingAdapter extends RecyclerView.Adapter<MySellingAdapter.DerpHolder>{

    private List<MySellingListItem> listData;
    private LayoutInflater inflater;

    private ItemClickCallback itemClickCallback;
    public interface  ItemClickCallback{
        void onItemClick(int p);
        void onSecondaryIconClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback =itemClickCallback;
    }
    public MySellingAdapter(List<MySellingListItem> listData, Context c){
        this.inflater=LayoutInflater.from(c);
        this.listData=listData;

    }

    @Override //create view holder object
    public DerpHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.myselling_list_item,parent,false);

        return new DerpHolder(view);
    }

    @Override
    public void onBindViewHolder(DerpHolder holder, int position) {
        MySellingListItem item=listData.get(position);
        holder.title.setText("제목 : "+item.getTitle());
        holder.writer.setText("저자 : "+item.getWriter());
        holder.company.setText("출판사 : "+item.getCompany());
        if(item.getType()==1) {
            if(item.getProgress()==1)
                holder.type.setText("판매 / 대여 : 판매완료");
            else
                holder.type.setText("판매 / 대여 : 판매중");
            //button= BitmapFactory.decodeResource(context.getResources(),R.drawable.sale_button);
        }
        else {
            if(item.getProgress()==2)
                holder.type.setText("판매 / 대여 : 대여중");
            else
                holder.type.setText("판매 / 대여 : 대여가능");
            //button= BitmapFactory.decodeResource(context.getResources(),R.drawable.rent_button);
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap bitmap=getResizedBitmap(item.getBitmap(),500,360);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);
        holder.thumnail.setImageBitmap(rotatedBitmap);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class DerpHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title;
        private TextView writer;
        private TextView company;
        private ImageView thumnail;
        private TextView type;
        //private ImageView icon2;
        private View container;

        public DerpHolder(View itemView) {
            super(itemView);

            title=(TextView)itemView.findViewById(R.id.lbl_Sitem_book);
            writer=(TextView)itemView.findViewById(R.id.lbl_Sitem_writer);
            company=(TextView)itemView.findViewById(R.id.lbl_Sitem_company);
            type=(TextView) itemView.findViewById(R.id.lbl_Sitem_type);
            thumnail = (ImageView) itemView.findViewById(R.id.im_Sitem_icon);
            //thumnail=(ImageView)itemView.findViewById(R.id.im_item_icon);
           // icon2=(ImageView)itemView.findViewById(R.id.im_item_icon2);
            //icon2.setOnClickListener(this);
            container=itemView.findViewById(R.id.cont_Sitem_root);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(v.getId()==R.id.cont_Sitem_root) {
                itemClickCallback.onItemClick(getAdapterPosition());
            }
            else{
                //itemClickCallback.onSecondaryIconClick(getAdapterPosition());
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
}
