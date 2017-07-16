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
import nexuslink.charon.douya.biz.OnRecItemClickListener;

/**
 * Created by Charon on 2017/7/11.
 */

public class MainBookRecAdapter extends RecyclerView.Adapter {
    private BookData list; //数据
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_END = 2;
    private static final int TYPE_ERROR = 3;
    private OnRecItemClickListener onRecItemClickListener = null;
    private Context context;
    private boolean noMore = false;
    private boolean onError = false;

    public MainBookRecAdapter(BookData list, Context context) {
        this.list = list;
        this.context = context;
    }


    public void setOnItemClickListener(OnRecItemClickListener onItemClickListener) {
        onRecItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item_main, parent, false);
                return new MyViewHolder(view);
            case TYPE_FOOTER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_item_main, parent, false);
                return new FooterViewHolder(view);
            case TYPE_ERROR:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.error_item_main, parent, false);
                return new ErrorViewHolder(view);
            case TYPE_END:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.end_item_main, parent, false);
                return new EndViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            //用picasso ,如果没得图，用默认的。
            Picasso.with(context).load(list.getBooks().get(position).getImages().getLarge() + "").into(((MyViewHolder) holder).iv_img);
            //书籍名称
            Log.d("BookRec", list.getBooks().get(position).getTitle());
            ((MyViewHolder) holder).tv_name.setText(list.getBooks().get(position).getTitle() + "");
            //作者
            if (list.getBooks().get(position).getAuthor().size() > 0) {
                ((MyViewHolder) holder).tv_author.setText("作者：" + list.getBooks().get(position).getAuthor().get(0) + "");
            }

            //评分，还可以加星星
            if (list.getBooks().get(position).getRating().getAverage() > 0)
                ((MyViewHolder) holder).tv_rating.setText("评分:" + list.getBooks().get(position).getRating().getAverage() + "");

            ((MyViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecItemClickListener.onItemClick(v, position);
                }
            });
            ((MyViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onRecItemClickListener.onItemLongClick(v, position);
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.getBooks().size() == 0 ? 0 : list.getBooks().size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            if (noMore) {
                return TYPE_END;
            } else if (onError) {
                return TYPE_ERROR;
            } else return TYPE_FOOTER;
        } else return TYPE_ITEM;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
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

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class EndViewHolder extends RecyclerView.ViewHolder {
        EndViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class ErrorViewHolder extends RecyclerView.ViewHolder {
        ErrorViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void addData(BookData bookData) {
        for (int i = 0; i < bookData.getBooks().size(); i++) {
            list.getBooks().add(bookData.getBooks().get(i));
        }
        list.setCount(bookData.getCount());
        list.setTotal(bookData.getTotal());
        if (list.getTotal() == getItemCount() - 1) {
            noMore = true;
        }
        notifyDataSetChanged();
    }

    public boolean ifMore() {
        return getItemCount() < list.getTotal();
    }

    public void ifError(boolean ifError) {
        onError = ifError;
    }
}

