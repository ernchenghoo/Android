package com.example.kotlinexercise

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this) //initialize Realm database
        // Initialize Realm. Should only be done once when the application starts.
        val config = RealmConfiguration.Builder().name("kotlinApp.realm")
            .deleteRealmIfMigrationNeeded()
            .migration(DatabaseMigration()) // Migration to run instead of throwing an exception
            .build()
        Realm.setDefaultConfiguration(config)

    }
}