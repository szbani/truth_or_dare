package com.example.FvM;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.example.FvM.models.Packs;
import com.example.FvM.models.Questions;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.ClientResetRequiredError;
import io.realm.mongodb.sync.DiscardUnsyncedChangesStrategy;
import io.realm.mongodb.sync.MutableSubscriptionSet;
import io.realm.mongodb.sync.Subscription;
import io.realm.mongodb.sync.SubscriptionSet;
import io.realm.mongodb.sync.SyncConfiguration;
import io.realm.mongodb.sync.SyncSession;

public class RealmHelper {

    private static boolean logged = true;
    private static Realm realm;
    private static User user;
    private static User loggedUser;
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

        user = app.currentUser();
        SyncConfiguration config = new SyncConfiguration.Builder(user).initialSubscriptions(

                        (realm, subscriptions) -> {
                            Log.w("queryId", user.getId());
                            String[] owners = new String[]{user.getId(), "default"};
                            RealmQuery<Packs> PacksQuery = realm.where(Packs.class).in("owner_id", owners);
                            subscriptions.addOrUpdate(Subscription.create("Packs", PacksQuery));
                        }
                ).allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        Realm.getInstanceAsync(config, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                RealmHelper.realm = realm;
                realm.isAutoRefresh();
                Log.v("QUICKSTART", "Successfully opened a realm.");
            }

            @Override
            public void onError(Throwable exception) {
                Log.e("QUICKSTART", "Failed to open a realm: " + exception.getMessage());
            }
        });

        Log.w("USER", String.valueOf(user.getId()));

        if (user == null) {
            setLoggedUser(false);
            Credentials credentials = Credentials.anonymous();
            app.loginAsync(credentials, result -> {
                if (result.isSuccess()) {
                    Log.v("QUICKSTART", "Successfully authenticated anonymously.");
                    user = app.currentUser();

                    Realm.getInstanceAsync(config, new Realm.Callback() {
                        @Override
                        public void onSuccess(Realm realm) {
                            RealmHelper.realm = realm;
                            realm.isAutoRefresh();
                            Log.v("QUICKSTART", "Successfully opened a realm.");
                        }

                        @Override
                        public void onError(Throwable exception) {
                            Log.e("QUICKSTART", "Failed to open a realm: " + exception.getMessage());
                        }
                    });
                } else {
                    Log.e("QUICKSTART", "Failed to log in. Error: " + result.getError());
                }
            });

        } else if (user.getProviderType().equals("ANONYMOUS")) {
            // User is already anonymous, you can continue with Realm configuration here
            setLoggedUser(false);

            // Continue with your Realm configuration and subscription setup here
        } else {
            // User is already authenticated, you might want to setLoggedUser(true) here
            setLoggedUser(true);
        }
    }

    public static void closeRealm() {
        realm.close();
    }

    public static void login(String username, String password) {

        Credentials credentials = Credentials.emailPassword(username, password);
        app.loginAsync(credentials, it -> {
            if (it.isSuccess()) {
                user = app.currentUser();
                SyncConfiguration config = new SyncConfiguration.Builder(user).initialSubscriptions(
                                (realm, subscriptions) -> {
                                    String[] owners = new String[]{user.getId(), "default"};
                                    RealmQuery<Packs> PacksQuery = realm.where(Packs.class).in("owner_id", owners);
                                    subscriptions.addOrUpdate(Subscription.create("Packs", PacksQuery));
                                }
                        ).allowQueriesOnUiThread(true)
                        .allowWritesOnUiThread(true)
                        .build();
                RealmHelper.realm = Realm.getInstance(config);
            }
        });
        setLoggedUser(true);
    }


    public static void logout() {
        if (realm != null) {
            realm.close();
        }
        user.logOutAsync(result -> {
            if (result.isSuccess()) {
                Log.v("QUICKSTART", "Successfully logged out.");
                setLoggedUser(false);

                SyncConfiguration config = new SyncConfiguration.Builder(user).initialSubscriptions(

                                (realm, subscriptions) -> {
                                    Log.w("queryId", user.getId());
                                    String[] owners = new String[]{user.getId(), "default"};
                                    RealmQuery<Packs> PacksQuery = realm.where(Packs.class).in("owner_id", owners);
                                    subscriptions.addOrUpdate(Subscription.create("Packs", PacksQuery));
                                }
                        ).allowQueriesOnUiThread(true)
                        .allowWritesOnUiThread(true)
                        .build();

                RealmHelper.realm = Realm.getInstance(config);
            } else {
                Log.e("QUICKSTART", "Failed to log out user: " + result.getError());
            }
        });
    }

    public static boolean isLogged() {
        return !String.valueOf(app.currentUser().getProviderType()).equals("ANONYMOUS");
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
        Log.i("PACK", newPack.getOwner_id());
        Log.i("PACK", newPack.getName());
        Log.i("REALM", realm.toString());
        realm.executeTransactionAsync(transactionRealm -> {
            transactionRealm.insert(newPack);
        });
        return newPack.get_id();
    }

    public static void deletePack(ObjectId pack_id) {
        realm.executeTransaction(transactionRealm -> {
            Packs pack = getPack(pack_id);
            pack.deleteFromRealm();
        });
    }

    public static void addQuestion(ObjectId id, Questions question) {
//        Log.v("PACK", pack.toString());
        Packs pack = getPack(id);
        realm.executeTransaction(transactionRealm -> {
            pack.addQuestion(question);
            transactionRealm.copyToRealmOrUpdate(pack);
        });
    }

    public static void updateName(ObjectId id, String name) {
        realm.executeTransaction(transactionRealm -> {
            Packs pack = getPack(id);
            pack.setName(name);
        });
    }

    public static void updateQuestion(ObjectId pack_id, ObjectId question_id, String question) {
        Packs pack = getPack(pack_id);
        Questions question_obj = pack.getQuestions().where().equalTo("_id", question_id).findFirst();
        Log.i("qOBJ", String.valueOf(question_obj));
        realm.executeTransaction(transactionRealm -> {
            pack.setQuestion(question_obj, question);
            transactionRealm.copyToRealmOrUpdate(pack);
        });
    }

    public static void deleteQuestion(ObjectId pack_id, ObjectId question_id) {
        Packs pack = getPack(pack_id);
        Questions question_obj = pack.getQuestions().where().equalTo("_id", question_id).findFirst();
        realm.executeTransaction(transactionRealm -> {
            pack.deleteQuestion(question_obj);
        });
    }

    public static RealmResults<Packs> getPacks() {
        realm.close();

        SyncConfiguration config = new SyncConfiguration.Builder(user).initialSubscriptions(
                        (realm, subscriptions) -> {
                            Log.w("queryId", user.getId());
                            String[] owners = new String[]{user.getId(), "default"};
                            RealmQuery<Packs> PacksQuery = realm.where(Packs.class).in("owner_id", owners);
                            subscriptions.addOrUpdate(Subscription.create("Packs", PacksQuery));
                        }
                ).allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();

       RealmHelper.realm = Realm.getInstance(config);
       Log.i("realm", realm.toString());
        Log.w("USERID", user.getId());
        Log.w("USERIDAPP", app.currentUser().getId());
        try {
            RealmResults<Packs> packs = realm.where(Packs.class).findAll();
            return packs;

        } catch (Exception e) {
            Log.e("REALM", e.getMessage());
        }
        return null;
    }

//    public static RealmResults<Packs> getPacks(String queryParam, List<ObjectId> queryValue) {
//        try {
//            return realm.where(Packs.class).in(queryParam, ).findAll();
//        } catch (Exception e) {
//            Log.e("REALM", e.getMessage());
//        }
//        return null;
//    }

    public static Packs getPack(ObjectId pack_id) {
        return realm.where(Packs.class).equalTo("_id", pack_id).findFirst();
    }

    public static boolean getLoggedUser() {
        Log.i("GETLogged", String.valueOf(logged));
        return logged;
    }

    public static void setLoggedUser(boolean logged) {
        RealmHelper.logged = logged;
        Log.i("setLogged", String.valueOf(RealmHelper.logged));
    }


    public static List<Packs> getPacks(String queryParam, List<ObjectId> queryValue) {
        try {
            RealmQuery<Packs> query = realm.where(Packs.class);
            for (ObjectId id : queryValue) {
                query.equalTo(queryParam, id);
            }
            return query.findAll();
        } catch (Exception e) {
            Log.e("REALM", e.getMessage());
        }
        return null;
    }

//    public static void refreshRealm() {
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.refresh();
//            }
//        });
//    }
}



