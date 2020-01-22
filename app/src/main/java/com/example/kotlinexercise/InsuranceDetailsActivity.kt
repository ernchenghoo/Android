package com.example.kotlinexercise

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinexercise.Model.ActivePolicy
import com.example.kotlinexercise.Model.Business
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.insurance_details_activity.*

class InsuranceDetailsActivity : AppCompatActivity() {

    val database = Realm.getDefaultInstance()
    var ic: Long = 0
    lateinit var insuranceType: String
    lateinit var insuranceDetails: InsuranceDetailsInterface


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insurance_details_activity)

        ic = intent.getLongExtra("IC", 0)
        insuranceType = intent.getStringExtra("type")

        if (insuranceType.equals("business")) {
            insuranceDetails = database.where<Business>().equalTo("ic", ic).findFirst()!!
            loadBusinessDetailsCards()
        }
        else if (insuranceType.equals("policy")) {
            insuranceDetails = database.where<ActivePolicy>().equalTo("ic", ic).findFirst()!!
            process_text.setText((insuranceDetails as ActivePolicy).process)
            loadPolicyDetailsCards()
        }

        case_details_button.setOnClickListener {
            detailsButtonDown()
        }
        scan_doc_button.setOnClickListener {
            scanDocButtonDown()
        }
        info_button.setOnClickListener {
            infoButtonDown()
        }
        back_button.setOnClickListener {
            finish()
        }
        edit_button.setOnClickListener{
            intent = Intent(this, NewRecordActivity::class.java)
            intent.putExtra("form", "edit")
            intent.putExtra("IC", insuranceDetails.ic)
            intent.putExtra("name", insuranceDetails.name)
            intent.putExtra("policyNo", insuranceDetails.policyNo)
            intent.putExtra("typeID", insuranceDetails.typeID)
            if (insuranceDetails is Business) {
                intent.putExtra("type", "business")
            }
            else if (insuranceDetails is ActivePolicy) {
                intent.putExtra("process", (insuranceDetails as ActivePolicy).process)
                intent.putExtra("type", "policy")
            }
            startActivity(intent)
        }
        delete_button.setOnClickListener {
            database.executeTransaction {
                if (insuranceDetails is Business) {
                    (insuranceDetails as Business).deleteFromRealm()
                }
                else if (insuranceDetails is ActivePolicy)
                    (insuranceDetails as ActivePolicy).deleteFromRealm()
            }
            finish()
        }

        when (insuranceDetails.typeID) {
            0 -> insurance_icon.setImageResource(R.drawable.image0)
            1 -> insurance_icon.setImageResource(R.drawable.image1)
            2 -> insurance_icon.setImageResource(R.drawable.image2)
        }
    }

    override fun onResume() {
        super.onResume()
        if (insuranceType.equals("business")) {
            loadBusinessDetailsCards()
        }
        else if (insuranceType.equals("policy")) {
            loadPolicyDetailsCards()
        }
    }

    private fun detailsButtonDown() {
        case_details_button.setBackgroundResource(R.drawable.button_pressed)
        case_details_button.setTextColor(Color.WHITE)
        scan_doc_button.setBackgroundColor(Color.TRANSPARENT)
        scan_doc_button.setTextColor(Color.BLACK)
        info_button.setBackgroundColor(Color.TRANSPARENT)
        info_button.setTextColor(Color.BLACK)
    }
    private fun scanDocButtonDown() {
        case_details_button.setBackgroundColor(Color.TRANSPARENT)
        case_details_button.setTextColor(Color.BLACK)
        scan_doc_button.setBackgroundResource(R.drawable.button_pressed)
        scan_doc_button.setTextColor(Color.WHITE)
        info_button.setBackgroundColor(Color.TRANSPARENT)
        info_button.setTextColor(Color.BLACK)
    }
    private fun infoButtonDown() {
        case_details_button.setBackgroundColor(Color.TRANSPARENT)
        case_details_button.setTextColor(Color.BLACK)
        scan_doc_button.setBackgroundColor(Color.TRANSPARENT)
        scan_doc_button.setTextColor(Color.BLACK)
        info_button.setBackgroundResource(R.drawable.button_pressed)
        info_button.setTextColor(Color.WHITE)
    }
    private fun loadBusinessDetailsCards() {
        name_card.setCardBackgroundColor(Color.parseColor("#F0F0F0"))
        id_card.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        owner_card.setCardBackgroundColor(Color.parseColor("#F0F0F0"))
        owner_id_card.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        populateTexts()
        process_card.visibility = View.GONE
    }

    private fun loadPolicyDetailsCards() {
        process_card.setCardBackgroundColor(Color.parseColor("#F0F0F0"))
        name_card.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        id_card.setCardBackgroundColor(Color.parseColor("#F0F0F0"))
        owner_card.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        owner_id_card.setCardBackgroundColor(Color.parseColor("#F0F0F0"))
        populateTexts()
        process_card.visibility = View.VISIBLE
    }

    private fun populateTexts() {
        //set text fields
        policy_holder.setText(insuranceDetails.name)
        name_text.setText(insuranceDetails.name)
        id_text.setText(insuranceDetails.ic.toString())
        owner_text.setText(insuranceDetails.name)
        owner_text.setText(insuranceDetails.ic.toString())
    }
}