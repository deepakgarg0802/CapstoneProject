package com.example.deepakgarg.capstoneproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.deepakgarg.capstoneproject.data.FavContract;
import com.example.deepakgarg.capstoneproject.data.FavDBHelper;
import com.example.deepakgarg.capstoneproject.data.FavouritesTable;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deepak Garg on 26-03-2017.
 */

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.MyViewHolder> {
    private Context mContext;
    private MaterialFavoriteButton btnbookmark;
    Cursor dataCursor;
    private ArrayList<String> id, name, description, newsurl, image;
    private int mMutedColor = 0xFF333333;

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ImageView imageView;
        TextView newstitle;

        MyViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.fav_card_view);
            newstitle = (TextView) v.findViewById(R.id.fav_news_title);
            imageView = (ImageView) v.findViewById(R.id.fav_grid_image);
            btnbookmark = (MaterialFavoriteButton) v.findViewById(R.id.fav_bookmark);
        }
    }


    public FavAdapter(Context c, ArrayList<String> id, ArrayList<String> name,
                      ArrayList<String> description, ArrayList<String> newsurl,
                      ArrayList<String> image,Cursor cursor) {
        mContext = c;
        this.id = id;
        this.name = name;
        this.description = description;
        this.newsurl = newsurl;
        this.image = image;
        this.dataCursor=cursor;
    }

    public Cursor swapCursor(Cursor cursor) {
        if (dataCursor == cursor) {
            return null;
         }
        Cursor oldCursor = dataCursor;
        this.dataCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }
    @Override
    public FavAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        FavAdapter.MyViewHolder holder;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_news_grid, parent, false);
        holder = new FavAdapter.MyViewHolder(v);
        holder.mCardView.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(final FavAdapter.MyViewHolder holder, final int position) {
        try{
            dataCursor.moveToPosition(position);
            //Log.d("image loading",dataCursor.getString(dataCursor.getColumnIndex("2")));
            Glide.clear(holder.imageView);
            Glide.with(holder.imageView.getContext())
                    .load(dataCursor.getString(dataCursor.getColumnIndex(FavouritesTable.FIELD_URLTOIMAGE)))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model,
                                                       Target<GlideDrawable> target,
                                                       boolean isFromMemoryCache, boolean isFirstResource) {
                            Bitmap bitmap = ((GlideBitmapDrawable) resource.getCurrent()).getBitmap();
                            Palette palette = Palette.generate(bitmap);
                            int defaultColor = 0xFF333333;
                            int color = palette.getMutedColor(defaultColor);
                            holder.newstitle.setBackgroundColor(color);
                            return false;
                        }
                    })
                    .into(holder.imageView);

            holder.newstitle.setText(dataCursor.getString(dataCursor.getColumnIndex(FavouritesTable.FIELD_TITLE)));
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, WebActivity.class);
                    intent.putExtra("URL", newsurl.get(position));
                    intent.putExtra("SOURCE", name.get(position));
                    mContext.startActivity(intent);
                }
            });
            final String newsUrl = dataCursor.getString(dataCursor.getColumnIndex(FavouritesTable.FIELD_URL));
            ArrayList<String> check = queryFavourites();
            if (check.contains(newsUrl)) {
                btnbookmark.setFavorite(true);
            } else {
                btnbookmark.setFavorite(false);
            }

            btnbookmark.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    mContext.getContentResolver().delete(FavouritesTable.CONTENT_URI, FavContract.COLUMN_URL + " = ?", new String[]{"" + newsUrl});
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private ArrayList<String> queryFavourites() {

        Cursor c = mContext.getContentResolver().query(FavouritesTable.CONTENT_URI, null, null, null, null);
        List<FavDBHelper> list = FavouritesTable.getRows(c, true);
        ArrayList<String> idList = new ArrayList<>();
        for (FavDBHelper element : list) {
            idList.add(element.url);
        }
        return idList;
    }

    @Override
    public int getItemCount() {
        return (dataCursor == null) ? 0 : dataCursor.getCount();
    }
}