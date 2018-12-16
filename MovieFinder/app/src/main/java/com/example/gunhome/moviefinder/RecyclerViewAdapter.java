package com.example.gunhome.moviefinder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private Activity activity;
    private List<Movie_card> movies;
    private MainActivity ac;
    Handler handler = new Handler();  // 외부쓰레드 에서 메인 UI화면을 그릴때 사용
    public static String global_image_url = "";
    Bitmap result;

    public RecyclerViewAdapter(Activity activity, List<Movie_card> movies) {
        this.activity = activity;
        this.movies = movies;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, year, director, actor;
        ImageView poster;
        RatingBar rating;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.movie_title);
            year = (TextView) itemView.findViewById(R.id.movie_year);
            director = (TextView) itemView.findViewById(R.id.movie_director);
            actor = (TextView) itemView.findViewById(R.id.movie_actor);
            poster = (ImageView)itemView.findViewById(R.id.imageView);
            rating = (RatingBar)itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(activity, "click [" + Html.fromHtml(movies.get(getAdapterPosition()).getTitle()) + "]", Toast.LENGTH_SHORT).show();

                    Uri uri = Uri.parse(movies.get(getAdapterPosition()).getLink());

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    view.getContext().startActivity(intent);

                }
            });
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // 재활용 되는 View가 호출, Adapter가 해당 position에 해당하는 데이터를 결합
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Movie_card data = movies.get(position);

        // 데이터 결합
        holder.title.setText(Html.fromHtml(data.getTitle()));
        holder.year.setText(data.getYear());
        holder.director.setText(data.getDirector());
        holder.actor.setText(data.getActor());

//        Bitmap image_bit;
//        image_bit = load_poster(data.getImage());
        holder.poster.setImageBitmap(load_poster(data.getImage()));

        final Float movie_rating = Float.parseFloat(data.getRating());
        holder.rating.setRating((float)(movie_rating/2));
//        holder.rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                holder.rating.setRating(movie_rating);
//            }
//        });
    }

    public Bitmap load_poster(String image_url){
        global_image_url = image_url;

        Thread t = new Thread(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try{
                    URL url = new URL(global_image_url);
                    InputStream is = url.openStream();
                    result = BitmapFactory.decodeStream(is);
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {  // 화면에 그려줄 작업
//                            poster.setImageBitmap(bm);
//                        }
//                    });
                } catch(Exception e){

                }
            }
        };
        t.start();

        try{
            t.join();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
