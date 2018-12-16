package com.example.gunhome.moviefinder;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager linearLayoutManager;
    RecyclerViewAdapter recyclerViewAdapter;

    int total=-2;
    int display = 10;
    int total_check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        Button searchBtn = (Button)findViewById(R.id.button);

        searchBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(MainActivity.this, "click" , Toast.LENGTH_SHORT);
                Thread tt = new Thread(){
                    public void run(){
                        EditText editName = (EditText)findViewById(R.id.editText);
                        String search_name;
                        search_name = editName.getText().toString();
                        try {
                            Call_API(search_name);
                            if(total_check == 0) Call_API(search_name);
                        }catch (Exception e){System.out.println(e);}
                    }
                };
                tt.start();

                try{
                    tt.join();
                    make_item(display);
                    total = -2;
                    display = 10;
                    total_check = 0;
                }catch (Exception e){
                    System.out.println(e);
                }

                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }

        });

    }

    public static StringBuilder sb;

    public void Call_API(String search_name) throws Exception{
        String clientID = "LJAHQKdbc42BBJNR0hFe"; //clientID
        String clientSecret = "6QXeuIxWnh";        //clienSecret
        try {
            String moiveName = URLEncoder.encode(search_name,"utf-8");
            String apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + moiveName + "&display=" + display + "&";
//            String apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + moiveName + "&";

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientID);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            con.disconnect();
            System.out.println(sb);

            String data = sb.toString();
            String[] array = data.split("\"");
            String total_string;
            for(int i=0;i<10;i++){
                if(array[i].equals("total")){
                    total_string = array[i+1].replaceAll("[^0-9]","");
                    total = Integer.parseInt(total_string);
                }
            }
            if(total == display) total_check = 1;
            else display = total;
//            make_item(display);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void make_item(int display) throws Exception{
        try{
            String data = sb.toString();
            String[] array;
            array = data.split("\"");
            String[] title = new String[display];
            String[] link = new String[display];
            String[] image = new String[display];
            String[] subtitle = new String[display];
            String[] pubDate = new String[display];
            String[] director = new String[display];
            String[] actor = new String[display];
            String[] userRating = new String[display];
            int k = 0;
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals("title"))
                    title[k] = array[i + 2];
                if (array[i].equals("link"))
                    link[k] = array[i + 2];
                if (array[i].equals("image"))
                    image[k] = array[i + 2];
//                if (array[i].equals("subtitle"))
//                    subtitle[k] = array[i + 2];
                if (array[i].equals("pubDate"))
                    pubDate[k] = array[i + 2];
                if (array[i].equals("director"))
                    director[k] = array[i + 2];
                if (array[i].equals("actor"))
                    actor[k] = array[i + 2];
                if (array[i].equals("userRating")) {
                    userRating[k] = array[i + 2];
                    k++;
                }
            }
            System.out.println(sb);
//            System.out.println("----------------------------------------");
//            for(int i=0;i<k;i++){
//                System.out.println(i + "번째 영화 정보");
//                System.out.println("제목 : " + title[i]);
//                System.out.println("영어제목 : " + subtitle[i]);
//                System.out.println("제작년도 : " + pubDate[i]);
//                System.out.println("감독 : " + director[i]);
//                System.out.println("배우 : " + actor[i]);
//                System.out.println("평점 : " + userRating[i]);
//                System.out.println("----------------------------------------");
//            }

            linearLayoutManager = new LinearLayoutManager(this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            recyclerView.addItemDecoration(new DividerItemDecoration(this,linearLayoutManager.getOrientation()));
                            recyclerView.setLayoutManager(linearLayoutManager);
                        }
                    });
                }
            }).start();

            List < Movie_card > movies = new ArrayList<>();

            // ArrayList에 person 객체(이름과 번호) 넣기
            for(int i=0;i<k;i++){
                movies.add(new Movie_card(title[i], pubDate[i],director[i],actor[i],image[i],userRating[i],link[i]));
            }

            recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, movies);

            recyclerView.setAdapter(recyclerViewAdapter);
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    recyclerView.setAdapter(recyclerViewAdapter);
//                }
//            });

        }catch(Exception e){
            System.out.println(e);
        }
    }
}
