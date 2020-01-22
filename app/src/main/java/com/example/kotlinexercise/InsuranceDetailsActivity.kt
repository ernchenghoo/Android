package com.example.kotlinexercise

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinexercise.Model.ActivePolicy
import com.example.kotlinexercise.Model.Business
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.insurance_details_activity.*

class InsuranceDetailsActivity : AppCompatActivity() {

    val database = Realm.getDefaultInstance()
    val manager = supportFragmentManager
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
        }
        else if (insuranceType.equals("policy")) {
            insuranceDetails = database.where<ActivePolicy>().equalTo("ic", ic).findFirst()!!
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
        showCaseDetailsFragment()
    }

    override fun onResume() {
        super.onResume()
        updateTextAndImage()
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

    private fun showCaseDetailsFragment() {
        val transaction = manager.beginTransaction()
        val fragment = CaseDetailsFragment(insuranceDetails, this)
        transaction.replace(R.id.fragment_frame, fragment)
        transaction.commit()
    }

    private fun updateTextAndImage() {
        policy_holder.setText(insuranceDetails.name)
        policy_holder_no.setText(insuranceDetails.policyNo)
        when (insuranceDetails.typeID) {
            0 -> insurance_icon.setImageResource(R.drawable.image0)
            1 -> insurance_icon.setImageResource(R.drawable.image1)
            2 -> insurance_icon.setImageResource(R.drawable.image2)
        }
    }

}