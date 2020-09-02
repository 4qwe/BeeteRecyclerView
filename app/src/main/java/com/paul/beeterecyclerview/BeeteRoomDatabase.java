package com.paul.beeterecyclerview;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Beet.class}, version = 2, exportSchema = false)
public abstract class BeeteRoomDatabase extends RoomDatabase {

    public abstract BeetDoa beetDoa();

    private static volatile BeeteRoomDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static BeeteRoomDatabase getDatabase(final Context context) { //code für "singleton"
        if (INSTANCE == null) {
            synchronized (BeeteRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), BeeteRoomDatabase.class,
                            "beet_database").addCallback(sRoomDatabaseCallback).fallbackToDestructiveMigration().build();
                    /*This code uses Room's database builder to create a RoomDatabase
                     object named "word_database" in the application context from the WordRoomDatabase class.
                     */
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                BeetDoa dao = INSTANCE.beetDoa();
                dao.deleteAll();

                Beet mBeet = new Beet("Potatoes");
                dao.insert(mBeet);

                mBeet = new Beet("Gurken");
                dao.insert(mBeet);

                mBeet = new Beet("Eine Melone");
                dao.insert(mBeet);

                mBeet = new Beet("Steckrüben");
                dao.insert(mBeet);

                mBeet = new Beet("Wasabi");
                dao.insert(mBeet);

                mBeet = new Beet("Champignons");
                dao.insert(mBeet);

                mBeet = new Beet("Ruccola");
                dao.insert(mBeet);

                mBeet = new Beet("Blumenkohl");
                dao.insert(mBeet);

                mBeet = new Beet("Bananen");
                dao.insert(mBeet);

                mBeet = new Beet("Goldregen");
                dao.insert(mBeet);


            });

        }
    };
}


