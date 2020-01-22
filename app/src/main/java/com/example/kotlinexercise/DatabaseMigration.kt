package com.example.kotlinexercise

import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration

// Example migration adding a new class
class DatabaseMigration : RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {

        var oldVersion = oldVersion

        // DynamicRealm exposes an editable schema
        val schema = realm.schema

        // Migrate to version 1: Add a new class.
        // Example:
        // open class Person(
        //     var name: String = "",
        //     var age: Int = 0,
        // ): RealmObject()
        if (oldVersion == 0L) {
            schema.create("Business")
                .addField("ic", Long::class.java)
                .addField("name", String::class.java)
                .addField("policyNo", String::class.java)
                .addField("typeID", Int::class.java)
            schema.create("ActivePolicy")
                .addField("ic", Long::class.java)
                .addField("name", String::class.java)
                .addField("policyNo", String::class.java)
                .addField("process", String::class.java)
                .addField("typeID", Int::class.java)
            oldVersion++
        }

        // Migrate to version 2: Add a primary key + object references
        // Example:
        // open class Person(
        //     var name: String = "",
        //     var age: Int = 0,
        //     @PrimaryKey
        //     var id: Int = 0,
        //     var favoriteDog: Dog? = null,
        //     var dogs: RealmList<Dog> = RealmList()
        // ): RealmObject()

        if (oldVersion == 1L) {
            schema.get("Business")!!
                .addField("ic", Long::class.javaPrimitiveType, FieldAttribute.PRIMARY_KEY)
            schema.get("ActivePolicy")!!
                .addField("ic", Long::class.javaPrimitiveType, FieldAttribute.PRIMARY_KEY)
            oldVersion++
        }
    }
}