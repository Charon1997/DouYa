package nexuslink.charon.douya.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nexuslink.charon.douya.R;
import nexuslink.charon.douya.bean.book.BookData;
import nexuslink.charon.douya.bean.movie.MovieData;
import nexuslink.charon.douya.biz.OnRecItemClickListener;

/**
 * Created by Administrator on 2017/7/11.
 */

public class MainBookRecAdapter extends RecyclerView.Adapter{
    private BookData list; //数据
    private OnRecItemClickListener onRecItemClickListener = null;
    private Context context;

    public MainBookRecAdapter(BookData list,Context context) {
        this.list = list;
        this.context = context;
    }



    public void setOnItemClickListener(OnRecItemClickListener onItemClickListener) {
        onRecItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item_main, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //用picasso ,如果没得图，用默认的。
        Picasso.with(context).load(list.getBooks().get(position).getImages().getLarge()+"").into(((MyViewHolder) holder).iv_img);
        //书籍名称
        Log.d("BookRec", list.getBooks().get(position).getTitle());
        ((MyViewHolder)holder).tv_name.setText(list.getBooks().get(position).getTitle()+"");
        //作者
        if (list.getBooks().get(position).getAuthor().size()>0){
            ((MyViewHolder) holder).tv_author.setText("作者："+list.getBooks().get(position).getAuthor().get(0)+"");
        }

        //评分，还可以加星星
        ((MyViewHolder)holder).tv_rating.setText("评分:"+list.getBooks().get(position).getRating().getAverage()+"");

        ((MyViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecItemClickListener.onItemClick(v,position);
            }
        });
        ((MyViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onRecItemClickListener.onItemLongClick(v,position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.getBooks().size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_img;
        private TextView tv_name;
        private TextView tv_rating;
        private TextView tv_author;
        MyViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.item_book_img);
            tv_name = (TextView) itemView.findViewById(R.id.item_book_name);
            tv_rating = (TextView) itemView.findViewById(R.id.item_book_rating);
            tv_author = (TextView) itemView.findViewById(R.id.item_book_author);
        }
    }
}
