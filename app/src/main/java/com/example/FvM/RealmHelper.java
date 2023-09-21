package com.example.FvM;

import android.content.Context;
import android.util.Log;

import com.example.FvM.models.Task;
import com.example.FvM.models.TaskStatus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.MutableSubscriptionSet;
import io.realm.mongodb.sync.Subscription;
import io.realm.mongodb.sync.SyncConfiguration;

public class RealmHelper {

    private static Realm realm;
    public static void init(Context context){

        Realm.init(context);

        App app = new App(new AppConfiguration.Builder("truthordare-slpuf")
                .build());

        Credentials credentials = Credentials.anonymous();
        app.loginAsync(credentials, result -> {
            if (result.isSuccess()) {
                Log.v("QUICKSTART", "Successfully authenticated anonymously.");
                User user = app.currentUser();
                String partitionValue = "My Project";

                SyncConfiguration config = new SyncConfiguration.Builder(user).initialSubscriptions(
                                new SyncConfiguration.InitialFlexibleSyncSubscriptions() {
                                    @Override
                                    public void configure(Realm realm, MutableSubscriptionSet subscriptions) {
                                        subscriptions.addOrUpdate(Subscription.create("Task", realm.where(Task.class).equalTo("status", TaskStatus.Open.name())));
                                    }
                                }
                        )
                        .build();
                Realm.getInstanceAsync(config, new Realm.Callback() {
                    @Override
                    public void onSuccess(Realm realm) {
                        RealmHelper.realm = realm;
                        Subscription subscription = realm.getSubscriptions().find("Task");
                        Log.v("QUICKSTART", "Successfully opened a realm.");
                    }
                });
//                realm = Realm.getInstance(config);
//                addChangeListenerToRealm(realm);

            } else {
                Log.e("QUICKSTART", "Failed to log in. Error: " + result.getError());
            }
        });
    }

    public static void addTask(){
        Task task = new Task("My task");
        realm.executeTransactionAsync(transactionRealm -> {
            transactionRealm.insert(task);
        });
    }

    public static RealmResults<Task> getTasks(){
        Log.i("FASZ", String.valueOf(realm.where(Task.class).findAll())) ;

        return null;
    }


}