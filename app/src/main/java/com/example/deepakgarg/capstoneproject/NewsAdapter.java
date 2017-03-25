package com.example.deepakgarg.capstoneproject;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
/**
 * Created by Deepak Garg on 22-03-2017.
 */

public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private Context mContext;
    private int mMutedColor = 0xFF333333;
    private FragmentManager fm;
    private ArrayList<String> id,name, description, newsurl, image;

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ImageView imageView;
        TextView newstitle;

        MyViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            newstitle = (TextView) v.findViewById(R.id.news_title);
            imageView = (ImageView) v.findViewById(R.id.grid_image);
        }
    }


    public NewsAdapter(Context c, ArrayList<String> id,ArrayList<String> name,
                       ArrayList<String> description, ArrayList<String> newsurl,
                       ArrayList<String> image) {
        mContext = c;
        this.id = id;
        this.name = name;
        this.description = description;
        this.newsurl = newsurl;
        this.image = image;

    }


    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        MyViewHolder holder;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        holder = new MyViewHolder(v);
        holder.mCardView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        try{
            Picasso.with(mContext)
                    .load(image.get(position))
                    .into(holder.imageView);

            holder.newstitle.setText(name.get(position));
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                 public void onClick(View view) {

                    Intent intent = new Intent(mContext,SourceActivity.class);
                    intent.putExtra("SOURCE_NAME", id.get(holder.getAdapterPosition()));
                    mContext.startActivity(intent);
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return name.size();
    }
}

