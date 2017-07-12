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
import nexuslink.charon.douya.bean.movie.MovieData;
import nexuslink.charon.douya.biz.OnRecItemClickListener;

/**
 * Created by Administrator on 2017/4/19.
 */

public class MainMovieRecAdapter extends RecyclerView.Adapter   {
    private MovieData list; //数据
    private OnRecItemClickListener onRecItemClickListener = null;
    private Context context;

    public MainMovieRecAdapter(MovieData list,Context context) {
        this.list = list;
        this.context = context;
    }



    public void setOnItemClickListener(OnRecItemClickListener onItemClickListener) {
        onRecItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_main, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //电影名称
        ((MyViewHolder)holder).tv_name.setText(list.getSubjects().get(position).getTitle()+"");
        //用picasso ,如果没得图，用默认的。
        Picasso.with(context).load(list.getSubjects().get(position).getImages().getMedium()+"").into(((MyViewHolder) holder).iv_head);
        //主角
        if (list.getSubjects().get(position).getCasts().size()>0){
            ((MyViewHolder) holder).tv_cast.setText("主演："+list.getSubjects().get(position).getCasts().get(0).getName()+"");
            Log.d("123", list.getSubjects().get(position).getCasts().size() + "sizecasts");
        }

        //导演,就怕没得导演
        if (list.getSubjects().get(position).getDirectors().size()>0) {
            Log.d("123", list.getSubjects().get(position).getDirectors().size() + "size");
            ((MyViewHolder) holder).tv_director.setText("导演：" + list.getSubjects().get(position).getDirectors().get(0).getName() + "");
        }
        //评分，还可以加星星
        ((MyViewHolder)holder).tv_rating.setText("评分："+list.getSubjects().get(position).getRating().getAverage()+"");

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
        return list.getSubjects().size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_head;
        private TextView tv_name;
        private TextView tv_rating;
        private TextView tv_cast;
        private TextView tv_director;
        MyViewHolder(View itemView) {
            super(itemView);
            iv_head = (ImageView) itemView.findViewById(R.id.item_movie_img);
            tv_name = (TextView) itemView.findViewById(R.id.item_movie_name);
            tv_rating = (TextView) itemView.findViewById(R.id.item_movie_rating);
            tv_cast = (TextView) itemView.findViewById(R.id.item_movie_cast);
            tv_director = (TextView) itemView.findViewById(R.id.item_movie_director);
        }
    }
}
