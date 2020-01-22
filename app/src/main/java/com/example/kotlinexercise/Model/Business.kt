package com.example.kotlinexercise.Model

import com.example.kotlinexercise.InsuranceDetailsInterface
import io.realm.RealmList
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Business (

    @PrimaryKey
    override var ic: Long = 0,
    override var name: String = "",
    override var policyNo: String = "",
    override var typeID: Int = 0,

    var process: String? = ""
) : RealmObject(), InsuranceDetailsInterface {

    override fun equals(other: Any?): Boolean {

        if (javaClass != other?.javaClass) {
            return false
        }

        other as Business

        if (ic != other.ic)
            return false

        if (name != other.name)
            return false

        if (policyNo != other.policyNo)
            return false

        if (typeID != other.typeID)
            return false

        return true
    }

}