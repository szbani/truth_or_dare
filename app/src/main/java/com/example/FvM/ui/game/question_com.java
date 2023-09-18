package com.example.FvM.ui.game;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.FvM.R;

import java.util.Random;

public class question_com {

    private String kerdes;

    private static Random random;
    private static boolean ran_player;
    //private final Activity activity;
    private final Context context;

    public question_com(Activity activity){
        //this.activity = activity;
        ran_player = GameActivity.settings.getBoolean("p-order");
        Log.i("random players", String.valueOf(ran_player));
        random = new Random();
        this.context = activity.getLayoutInflater().getContext();
    }
    public String getKerdes(int tip){
        kerdes = kivalaszt(tip);
        kerdes = kerdes_Person();
        kerdes = kerdes_Sit();
        //kerdes = kerdes_Gender();
        return kerdes;
    }
    //játkos kiválasztása
    public static String player_valaszt(){
        try {
            if (GameActivity.players.size() > 1){
                if (ran_player) return GameActivity.players.get(random.nextInt(GameActivity.players.size()));
                else {
                    GameActivity.player_num++;
                    if (GameActivity.player_num >= GameActivity.players.size()) GameActivity.player_num = 0;
                    return GameActivity.players.get(GameActivity.player_num);
                }
                //random.nextInt(GameActivity.players.size()
            }
            else {
                return "";
            }
        }catch (Exception e){
            Log.e("Exception","(player_valaszt)hiba: ");
        }
        return "";
    }
    //kérdés kiválasztása
    public String kivalaszt(int type){
        String kerdes = "";
        try {
            if (type == 0 ){
                kerdes = GameActivity.K_f.get(random.nextInt(GameActivity.K_f.size()));
            }
            else if (type == 1){
                kerdes = GameActivity.K_m.get(random.nextInt(GameActivity.K_m.size()));
            }
        }catch (Exception e){
            kerdes = e.toString();
        }
        return kerdes.trim();
    }
    //@person
    public String kerdes_Person(){
        try {
            //kérdés személy cseréléses
            if (kerdes.contains("@person")) {
                String person;

                if (GameActivity.players.size() > 1) {

                    person = player_valaszt();
                    while (person.equals(game_events.player)) {
                        person = player_valaszt();
                    }
                    person = person.replace(person.substring(person.length() - 1), "").trim();
                } else {
                    int ran = random.nextInt(2);
                    if (ran == 0) person = context.getString(R.string.Def_Person_0);
                    else person = context.getString(R.string.Def_Person_1);
                }
                kerdes = kerdes.replace("@person", person);
            }
        } catch (Exception e) {
            Log.e("Exception", "(kerdes_person)ez itt a hiba" + e);
            return e.toString();
        }
        return kerdes;
    }
    //@sit
    public String kerdes_Sit(){
        try {
            //kérdés jobbra/balra ülő
            if(kerdes.contains("@sit")){
                if (GameActivity.players.size() > 2){
                    String way = "jobbra";
                    if ( random.nextInt(2)==0){
                        way = "balra";
                    }
                    int ran = random.nextInt(GameActivity.players.size()-1);
                    if (ran==0){
                        kerdes = kerdes.replace("@sit",way+" ulonek");
                    }else{
                        ran = ran+1;
                        kerdes = kerdes.replace("@sit",way +" "+ ran +".-dik szemely");
                    }
                }else{
                    kerdes = kerdes.replace("@sit","valamelyik személy");
                }
            }
        }
        catch (Exception e){
            Log.e( "Exception" ,"(kerdes_sit)ez itt a hiba" + e);
            return e.toString();
        }
        return kerdes;
    }

    //@gender
//    public String kerdes_Gender() {
//        try {
//            //kérdés személy cseréléses
//            if (kerdes.contains("@gender")) {
//                String person;
//
//                if (GameActivity.players.size() > 1) {
//
//                    person = player_valaszt();
//                    while (person.equals(player) || person.substring(person.length() - 1).equals(player.substring(player.length() -1))) {
//                        person = player_valaszt();
//                    }
//                    person = person.replace(person.substring(person.length() - 1), "").trim();
//                } else {
//                    int ran = random.nextInt(2);
//                    if (ran == 0) person =  context.getString(R.string.Def_Person_0);
//                    else person = context.getString(R.string.Def_Person_1);
//                }
//                kerdes = kerdes.replace("@person", person);
//            }
//        } catch (Exception e) {
//            Log.e("Exception", "(kerdes_gender)ez itt a hiba" + e);
//            return e.toString();
//        }
//        return kerdes;
//    }
}
