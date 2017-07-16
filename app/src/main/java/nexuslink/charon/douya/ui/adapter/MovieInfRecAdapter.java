package nexuslink.charon.douya.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nexuslink.charon.douya.R;
import nexuslink.charon.douya.bean.movie.MovieInf;
import nexuslink.charon.douya.biz.OnRecItemClickListener;

///**
// * Created by Charon on 2017/7/10.
// */

public class MovieInfRecAdapter extends RecyclerView.Adapter {
    private MovieInf list; //数据
    private OnRecItemClickListener onRecItemClickListener = null;
    private Context context;
    private final static String TAG = MovieInfRecAdapter.class.getSimpleName();

    public MovieInfRecAdapter(Context context, MovieInf list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnRecItemClickListener onItemClickListener) {
        onRecItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_casts_movie, parent, false);
        return new MovieInfRecAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position < list.getDirectors().size()) {
            ((MyViewHolder) holder).tv_name.setText(list.getDirectors().get(position).getName());
            ((MyViewHolder) holder).tv_duty.setText("导演");
            if (list.getDirectors().get(position).getAvatars() != null) {
                Picasso.with(context).load(list.getDirectors().get(position).getAvatars().getLarge()).into(((MyViewHolder) holder).iv_head);
            }
        } else {
            ((MyViewHolder) holder).tv_name.setText(list.getCasts().get(position - list.getDirectors().size()).getName());
            ((MyViewHolder) holder).tv_duty.setText("演员");
            if (list.getCasts().get(position - list.getDirectors().size()).getAvatars() != null) {
                Picasso.with(context).load(list.getCasts().get(position - list.getDirectors().size()).getAvatars().getLarge()).into(((MyViewHolder) holder).iv_head);
            }
        }
        ((MovieInfRecAdapter.MyViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecItemClickListener.onItemClick(v, position);
            }
        });
        ((MovieInfRecAdapter.MyViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onRecItemClickListener.onItemLongClick(v, position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.getCasts().size() + list.getDirectors().size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_head;
        private TextView tv_name;
        private TextView tv_duty;

        MyViewHolder(View itemView) {
            super(itemView);
            iv_head = (ImageView) itemView.findViewById(R.id.item_movie_cast_img);
            tv_name = (TextView) itemView.findViewById(R.id.item_movie_cast_text_name);
            tv_duty = (TextView) itemView.findViewById(R.id.item_movie_cast_text_duty);
        }
    }
}
