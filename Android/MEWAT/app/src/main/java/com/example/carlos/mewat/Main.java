package com.example.carlos.mewat;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import SongFounder.SongManager;


public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<HashMap<String,String>> songList=SongManager.getPlayList("/storage/sdcard1/");
        if(songList!=null){
            for(int i=0;i<songList.size();i++){
                String fileName=songList.get(i).get("file_name");
                String filePath=songList.get(i).get("file_path");


            }
        }
        display = (Button) findViewById(R.id.Display);

        final <ArrayList> songsListData = new <ArrayList>();

        SongManager plm = new SongManager();
        this.songList = plm.getPlayList();

        // Adding menuItems to ListView
        final ListAdapter adapter = new SimpleAdapter(this, songsListData,
                R.layout.playlist_item, new String[] { "songTitle" }, new int[] {
                R.id.songTitle });






        display.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // get all songs from sdcard




                // looping through playlist
                for (int i = 0; i < songsList.size(); i++) {
                    // creating new HashMap
                    HashMap song = songsList.get(i);

                    // adding HashList to ArrayList
                    songsListData.add(song);
                }



                setListAdapter(adapter);

            }});


    }
    }




}
