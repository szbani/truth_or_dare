package com.example.FvM;

import android.content.Context;
import android.util.Log;

import com.example.FvM.models.Packs;
import com.example.FvM.models.Questions;

import org.bson.types.ObjectId;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.ClientResetRequiredError;
import io.realm.mongodb.sync.DiscardUnsyncedChangesStrategy;
import io.realm.mongodb.sync.Subscription;
import io.realm.mongodb.sync.SyncConfiguration;
import io.realm.mongodb.sync.SyncSession;

public class RealmHelper {

    private static Realm realm;
    private static User user;

    private static App app;

    public static void init(Context context) {

        Realm.init(context);

        app = new App(new AppConfiguration.Builder(context.getString(R.string.realm_app_id))
                .defaultSyncClientResetStrategy(new DiscardUnsyncedChangesStrategy() {
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

                        (realm, subscriptions) -> {
                            String[] owners = new String[]{user.getId(), "default"};
                            RealmQuery<Packs> PacksQuery = realm.where(Packs.class).in("owner_id", owners);
                            subscriptions.addOrUpdate(Subscription.create("Packs", PacksQuery));
                        }
                ).allowWritesOnUiThread(true).waitForInitialRemoteData().build();

                Realm.getInstanceAsync(config, new Realm.Callback() {
                    @Override
                    public void onSuccess(Realm realm) {
                        RealmHelper.realm = realm;
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
  
   public static void logout() {
        user.logOutAsync(result -> {
            if (result.isSuccess()) {
                Log.v("QUICKSTART", "Successfully logged out.");
            } else {
                Log.e("QUICKSTART", "Failed to log out user: " + result.getError());
            }
        });
    }

    public static boolean isLogged() {
        return !String.valueOf(app.currentUser().getProviderType()).equals("anon-user");
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

    public static ObjectId addPack(String name) {
        Packs newPack = new Packs(name);
        newPack.setOwner_id(user.getId());
        realm.executeTransactionAsync(transactionRealm -> {
            transactionRealm.insert(newPack);
        });
        return newPack.get_id();
    }

    public static void deletePack(Packs pack) {
        realm.executeTransactionAsync(transactionRealm -> {
            pack.deleteFromRealm();
        });
    }

    public static void addQuestion(Packs pack, Questions question) {
        Log.v("PACK", pack.toString());

        realm.executeTransaction(transactionRealm -> {
            pack.addQuestion(question);
            transactionRealm.copyToRealmOrUpdate(pack);
        });
    }

    public static void updateName(Packs pack, String name) {
        realm.executeTransactionAsync(transactionRealm -> {
            pack.setName(name);
        });
    }

    public static void updateQuestion(ObjectId pack_id, ObjectId question_id, String question) {
        Packs pack = getPack(pack_id);
        Questions question_obj = pack.getQuestions().where().equalTo("_id", question_id).findFirst();
        realm.executeTransactionAsync(transactionRealm -> {
            pack.setQuestion(question_obj, question);
        });
    }

    public static RealmResults<Packs> getPacks() {
        return realm.where(Packs.class).findAll();
    }

    public static Packs getPack(ObjectId pack_id) {
        return realm.where(Packs.class).equalTo("_id", pack_id).findFirst();
    }

}



