package com.example.test3.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class beolvas {
    private Context mcontext;
    public beolvas(Context context){
        this.mcontext = context;
    }

    public  List<String> readline(String type){
        List<String> mLines = new ArrayList<>();

        SharedPreferences sh = mcontext.getSharedPreferences("packok", Context.MODE_PRIVATE);
        Set<String> set = new HashSet<>();
        set = sh.getStringSet("packs",set);
        List<String> packs = new ArrayList<>(set);
        if (packs.isEmpty()){
            packs.add("Default");
        }
        String path = "";

        AssetManager am = mcontext.getAssets();
        try {
            for (int i = 0; i < packs.size();i++){
                path = packs.get(i) +"_"+ type + ".txt";
                InputStream is = am.open(path);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String Line;
                while((Line = reader.readLine()) != null){
                    mLines.add(Line);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return mLines;
    }
}
