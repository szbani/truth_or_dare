package com.example.FvM;

import com.example.FvM.R;

import android.content.Context;
import android.util.Log;

import com.example.FvM.models.Packs;
import com.example.FvM.models.Questions;
import com.example.FvM.models.Task;
import com.example.FvM.models.TaskStatus;

import android.content.res.Resources;

import org.bson.types.ObjectId;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.ClientResetRequiredError;
import io.realm.mongodb.sync.DiscardUnsyncedChangesStrategy;
import io.realm.mongodb.sync.MutableSubscriptionSet;
import io.realm.mongodb.sync.Subscription;
import io.realm.mongodb.sync.SyncConfiguration;
import io.realm.mongodb.sync.SyncSession;

public class RealmHelper {

    private static Realm realm;
    private static User user;

    private static App app;

    public static void init(Context context) {

        Realm.init(context);

         app = new App(new AppConfiguration.Builder(context.getString(R.string.realm_app_id)).defaultSyncClientResetStrategy(new DiscardUnsyncedChangesStrategy() {
                    @Override
                    public void onBeforeReset(Realm realm) {
                        Log.w("RESET", "Beginning client reset for" + realm.getPath());
                    }

                    @Override
                    public void onAfterReset(Realm before, Realm after) {
                        Log.w("RESET", "Finished client reset for " + before.getPath());
                    }

                    @Override
                    public void onError(SyncSession session, ClientResetRequiredError error) {
                        Log.e("RESET", "Couldn't handle the client reset automatically.");
                    }
                })
                .build());

        Credentials credentials = Credentials.anonymous();
        app.loginAsync(credentials, result -> {
            if (result.isSuccess()) {
                Log.v("QUICKSTART", "Successfully authenticated anonymously.");
                user = app.currentUser();

                SyncConfiguration config = new SyncConfiguration.Builder(user).initialSubscriptions(
                                new SyncConfiguration.InitialFlexibleSyncSubscriptions() {
                                    @Override
                                    public void configure(Realm realm, MutableSubscriptionSet subscriptions) {
                                        subscriptions.addOrUpdate(Subscription.create("Packs", realm.where(Packs.class).equalTo("owner_id", user.getId())));
                                        subscriptions.addOrUpdate(Subscription.create("DefaultPacks", realm.where(Packs.class).equalTo("owner_id", "default")));
                                        subscriptions.addOrUpdate(Subscription.create("Questions", realm.where(Questions.class).equalTo("owner_id", user.getId())));
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

            } else {
                Log.e("QUICKSTART", "Failed to log in. Error: " + result.getError());
            }
        });
    }

    public static void login(String username, String password) {
        Credentials credentials = Credentials.emailPassword(username, password);
        app.loginAsync(credentials, result -> {
            if (result.isSuccess()) {
                Log.v("QUICKSTART", "Successfully authenticated anonymously.");
                user = app.currentUser();
            } else {
                Log.e("QUICKSTART", "Failed to log in. Error: " + result.getError());
            }
        });
    }

    public static void register(String username, String password) {
        app.getEmailPassword().registerUserAsync(username, password, it -> {
            if (it.isSuccess()) {
                Log.v("QUICKSTART", "Successfully registered user.");
            } else {
                Log.e("QUICKSTART", "Failed to register user: " + it.getError());
            }
        });
    }

    public static void addQuestion(Questions question) {
        Questions newQuestion = new Questions();
        newQuestion.setQuestion(question.getQuestion());
        newQuestion.setCategory(question.getCategory());
        newQuestion.setOwner_id(user.getId());
        newQuestion.setPack_id(question.getPack_id());
        Log.v("USERID", user.getId());
        realm.executeTransactionAsync(transactionRealm -> {
            transactionRealm.insert(newQuestion);
        });
    }

    public static RealmResults<Questions> getQuestions() {
        Packs pack = realm.where(Packs.class).equalTo("owner_id", user.getId()).findFirst();
        if (pack != null) {
            ObjectId packId = pack.get_id();
            return realm.where(Questions.class).equalTo("pack_id", packId).findAll();
        } else {
            return null; // Handle the case where no pack is found for the owner
        }
    }

    public static void deleteQuestion(Questions question) {
        realm.executeTransactionAsync(transactionRealm -> {
            question.deleteFromRealm();
        });
    }

    public static void updateQuestion(Questions question, String name) {
        realm.executeTransactionAsync(transactionRealm -> {
            question.setQuestion(name);
        });
    }

    public static ObjectId addPack(Packs pack) {
        Packs newPack = new Packs();
        newPack.setName(pack.getName());
        newPack.setOwner_id(user.getId());
        Log.v("PACKIOWNER", user.getId());
        realm.executeTransactionAsync(transactionRealm -> {
            transactionRealm.insert(newPack);
        });
        Log.v("PACKID", newPack.get_id().toString());
        return newPack.get_id();
    }

    public static void deletePack(Packs pack) {
        realm.executeTransactionAsync(transactionRealm -> {
            pack.deleteFromRealm();
        });
    }

    public static void updatePack(Packs pack, String name) {
        realm.executeTransactionAsync(transactionRealm -> {
            pack.setName(name);
        });
    }

    public RealmResults<Packs> getPacks() {
        return realm.where(Packs.class).findAll();
    }


}