package com.example.kotlinexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.kotlinexercise.Model.ActivePolicy
import com.example.kotlinexercise.Model.Business
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_new_record.*

class NewRecordActivity : AppCompatActivity() {

    lateinit var database: Realm
    var insuranceType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_record)

        database = Realm.getDefaultInstance()

        val formType = intent.getStringExtra("form")
        insuranceType = intent.getStringExtra("type")

        loadForm(formType)
    }

    private fun loadForm(type: String) {

        if (type.equals("new")) {
            submit_button.setText("Create")
            if (intent.getStringExtra("type").equals("business")) {
                title_text.setText("New Business")
            }
            else if (intent.getStringExtra("type").equals("policy")) {
                title_text.setText("New Active Policy")
            }
        }
        else if (type.equals("edit")) {
            submit_button.setText("Update")
            name_field.setText(intent.getStringExtra("name"))
            ic_field.setText(intent.getLongExtra("IC", 0).toString())
            policy_field.setText(intent.getStringExtra("policyNo"))
            type_field.setText(intent.getIntExtra("typeID", 0).toString())
            if (intent.getStringExtra("type").equals("business")) {
                title_text.setText("Update Business")
            }
            else if (intent.getStringExtra("type").equals("policy")) {
                title_text.setText("Update Policy")
            }
        }

        if (intent.getStringExtra("type").equals("policy")) {
            process_field.visibility = View.VISIBLE
            process_title.visibility = View.VISIBLE
            process_field.setText(intent.getStringExtra("process"))
        }
        else if (intent.getStringExtra("type").equals("business")){
            process_field.visibility = View.INVISIBLE
            process_title.visibility = View.INVISIBLE
        }

        submit_button.setOnClickListener {

            val ic: Long = ic_field.text.toString().toLong()
            val name: String = name_field.text.toString()
            val policyNo: String = policy_field.text.toString()
            val type: Int = type_field.text.toString().toInt()
            val process: String = process_field.text.toString()

            if (intent.getStringExtra("type").equals("business")) {
                populateDB(ic, name, policyNo, type, null)
            }
            else if (intent.getStringExtra("type").equals("policy")) {
                populateDB(ic, name, policyNo, type, process)
            }
            finish()
        }

        cancel_button.setOnClickListener{
            finish()
        }
    }

    private fun populateDB (newIC: Long, newName: String, newPolicyNo: String, newType: Int, newProcess: String?) {
        if (intent.getStringExtra("type").equals("business")) {
            val newBusiness = Business(newIC, newName, newPolicyNo, newType)
            database.executeTransaction {
                database.insertOrUpdate(newBusiness)
            }
        }
        else if (intent.getStringExtra("type").equals("policy")){
            val newPolicy = ActivePolicy(newIC, newName, newPolicyNo, newProcess!!, newType)
            database.executeTransaction {
                database.insertOrUpdate(newPolicy)
            }
        }
    }
}
