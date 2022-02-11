package com.example.test3.ui.players;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class query extends players {


    public void player_up(Activity activity, String name, int gender){
        try{

            File file = new File(activity.getFilesDir().getPath(),"players.txt");
            FileWriter fileWriter = new FileWriter(file,true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            if (file.length() != 0){
                writer.newLine();
            }
            String player = name + " " + gender;
            writer.write(player);
            writer.close();
        }catch (IOException e){
            Log.e("Exception", "nem sikerult a fajl megirasa: " + e.toString());
        }

    }

    public List<String> player_down(Context context){
        List<String> list = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(context.getFilesDir().getPath()+"/players.txt");
            BufferedReader reader = new BufferedReader(fileReader);
            String player;
            while((player = reader.readLine())!= null){
                list.add(player);
            }
        }catch (IOException e){
            Log.e("exception", "nem sikerult a fajl olvasasa" + e.toString());
        }
        return list;
    }

    public void player_delete(Activity activity,String name, int gender){
        try {
            File inputFile = new File(activity.getFilesDir().getPath()+"/players.txt");
            File tempFile = new File(activity.getFilesDir().getPath()+"/tempplayers.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String del_player = name + " " + gender;
            String currentline;

            while((currentline = reader.readLine()) != null){
                if (currentline.equals(del_player)) continue;
                if (tempFile.length() == 0) writer.newLine();
                writer.write(currentline);
            }
            writer.close();
            reader.close();
            tempFile.renameTo(inputFile);

        }catch (IOException e){
            Log.e("Exception","hiba a torlesnel"+e.toString());
        }
    }

    public void player_edit(){

    }

}
