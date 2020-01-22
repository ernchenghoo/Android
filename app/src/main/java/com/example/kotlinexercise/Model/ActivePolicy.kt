package com.example.kotlinexercise.Model

import com.example.kotlinexercise.InsuranceDetailsInterface
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class ActivePolicy (

    @PrimaryKey
    override var ic: Long = 0,
    override var name: String = "",
    override var policyNo: String = "",
    var process: String = "",
    override var typeID: Int = 0
) : RealmObject(), InsuranceDetailsInterface {

    override fun equals(other: Any?): Boolean {

        if (javaClass != other?.javaClass) {
            return false
        }

        other as ActivePolicy

        if (ic != other.ic)
            return false

        if (name != other.name)
            return false

        if (policyNo != other.policyNo)
            return false

        if (typeID != other.typeID)
            return false

//        if (process != other.process)
//            return false

        return true
    }

}