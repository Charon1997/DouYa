package nexuslink.charon.douya.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nexuslink.charon.douya.R;
import nexuslink.charon.douya.bean.Movie;
import nexuslink.charon.douya.biz.OnRecItemClickListener;

/**
 * Created by Administrator on 2017/4/19.
 */

public class MainRecAdapter extends RecyclerView.Adapter   {
    private List<Movie> list; //数据
    private OnRecItemClickListener onRecItemClickListener = null;

    public MainRecAdapter(List<Movie> list) {
        this.list = list;
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
        ((MyViewHolder)holder).tv_name.setText(list.get(position).getMovieName());
        ((MyViewHolder) holder).iv_head.setImageResource(list.get(position).getImg());
        ((MyViewHolder) holder).tv_protagonist.setText(list.get(position).getActorName());
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
        return list.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_head;
        private TextView tv_name;
        private TextView tv_grade;
        private TextView tv_protagonist;
        MyViewHolder(View itemView) {
            super(itemView);
            iv_head = (ImageView) itemView.findViewById(R.id.item_movie_img);
            tv_name = (TextView) itemView.findViewById(R.id.item_movie_name);
            tv_grade = (TextView) itemView.findViewById(R.id.item_movie_grade);
            tv_protagonist = (TextView) itemView.findViewById(R.id.item_movie_protagonist);
        }
    }
}
