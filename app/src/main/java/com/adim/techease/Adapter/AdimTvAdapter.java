package com.adim.techease.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adim.techease.R;
import com.adim.techease.controllers.TvModel;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

/**
 * Created by Adam Noor on 25-Oct-17.
 */

public class AdimTvAdapter extends RecyclerView.Adapter<AdimTvAdapter.MyViewHolder> {

    List<TvModel> modelTv;
    Context context;
    public static final String Key="AIzaSyDnTSqXDRyGbksm4xd2HUuwXRKjUHvBygw";
    public static final String id="ZvMJD7UQVbI";

    public AdimTvAdapter(Context context, List<TvModel> tvModels) {

        this.context=context;
        this.modelTv=tvModels;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customtv, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final TvModel model = modelTv.get(position);

        if (model.getTypeTv().equals("image")) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.layout.setVisibility(View.GONE);
            holder.RLoverThumbView.setVisibility(View.GONE);
            holder.btnPlay.setVisibility(View.GONE);
            holder.frameLayout.setVisibility(View.GONE);
            holder.youtubeTview.setVisibility(View.GONE);
            Glide.with(context).load("http://adadigbomma.com/panel/images/gallery/"+ model.getLinkTv()).into(holder.imageView);
            holder.textViewTitle.setText(model.getTitleTv());

        }
        else
        {
            final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener= new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                @Override
                public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                    holder.frameLayout.setVisibility(View.VISIBLE);
                    youTubeThumbnailView.setVisibility(View.VISIBLE);
                    holder.imageView.setVisibility(View.GONE);
                    holder.RLoverThumbView.setVisibility(View.VISIBLE);
                    holder.layout.setVisibility(View.GONE);
                    holder.btnPlay.setVisibility(View.VISIBLE);
                    holder.textViewTitle.setText(model.getTitleTv());

                }

                @Override
                public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                }
            };
            holder.youtubeTview.initialize(Key, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    youTubeThumbnailLoader.setVideo(id);
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                    Toast.makeText(context, "Thumbnail not loaded", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return modelTv.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        FrameLayout frameLayout;
        ImageView imageView;
        TextView textViewTitle,textViewVideoTitle;
        LinearLayout layout;
        protected RelativeLayout RLoverThumbView;
        YouTubeThumbnailView youtubeTview;
        protected ImageView btnPlay;
        Typeface typeface;

        public MyViewHolder(View itemView) {
            super(itemView);

            typeface=Typeface.createFromAsset(context.getAssets(),"myfont.ttf");
            imageView = (ImageView) itemView.findViewById(R.id.iv_photoTv);
            textViewTitle = (TextView) itemView.findViewById(R.id.tvTitleTv);
            textViewTitle.setTypeface(typeface);
            frameLayout=(FrameLayout)itemView.findViewById(R.id.FrameTv);
            layout = (LinearLayout) itemView.findViewById(R.id.newsVideoTv);
            btnPlay = (ImageView) itemView.findViewById(R.id.btnPlayTv);
            btnPlay.setOnClickListener(this);
            youtubeTview = (YouTubeThumbnailView) itemView.findViewById(R.id.youtubeGalleryTv);
            RLoverThumbView = (RelativeLayout) itemView.findViewById(R.id.RloverTv);
        }

        @Override
        public void onClick(View v) {
            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, Key, id);
            context.startActivity(intent);
        }
    }
}
