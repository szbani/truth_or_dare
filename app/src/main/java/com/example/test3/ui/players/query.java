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

public class query{

    public static void player_up(Activity activity, String name, int gender){
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

    public static List<String> player_down(Context context){
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

    public static void player_delete(Context context, int id){
        try {

            File inputFile = new File(context.getFilesDir().getPath()+"/players.txt");
            File tempFile = new File(context.getFilesDir().getPath()+"/tempplayers.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String currentline;
            int i = 0;
            while((currentline = reader.readLine()) != null && currentline != ""){
                if (i == id) {
                    i++;
                    continue;
                }
                if (tempFile.length() != 0) writer.newLine();
                writer.write(currentline);
                i++;
                Log.e("exception", "ennyiszer futott le" + String.valueOf(i));
            }
            writer.close();
            reader.close();
            tempFile.renameTo(inputFile);

        }catch (IOException e){
            Log.e("Exception","hiba a torlesnel "+e.toString());
        }
    }

    public static void player_edit(Context context,String name,int gender, int id){
        try {
            File inputFile = new File(context.getFilesDir().getPath()+"/players.txt");
            File tempFile = new File(context.getFilesDir().getPath()+"/tempplayers.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentline;
            int i = 0;
            while((currentline = reader.readLine()) != null && currentline != ""){
                if (i == id){
                    writer.write(name +" "+gender);
                    continue;
                }
                if (tempFile.length() != 0) writer.newLine();
                writer.write(currentline);
                i++;
            }
            writer.close();
            reader.close();
            tempFile.renameTo(inputFile);
        }catch (IOException e){
            Log.e("Exception","hiba az editnel "+e.toString());
        }
    }

}
