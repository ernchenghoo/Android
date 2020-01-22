package com.example.kotlinexercise

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinexercise.Model.ActivePolicy
import com.example.kotlinexercise.Model.Business
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_case_details.*

class CaseDetailsFragment (var insuranceDetails: InsuranceDetailsInterface, val ref: InsuranceDetailsActivity) : Fragment() {

    val database = Realm.getDefaultInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_case_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (insuranceDetails is Business) {
            loadBusinessDetailsCards()
        }
        else if (insuranceDetails is ActivePolicy) {
            loadPolicyDetailsCards()
        }

        edit_button.setOnClickListener{
            val intent = Intent(ref, NewRecordActivity::class.java)
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
            ref.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (insuranceDetails is Business) {
            loadBusinessDetailsCards()
        }
        else if (insuranceDetails is ActivePolicy) {
            loadPolicyDetailsCards()
        }
    }

    fun loadBusinessDetailsCards() {
        name_card.setCardBackgroundColor(Color.parseColor("#F0F0F0"))
        id_card.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        owner_card.setCardBackgroundColor(Color.parseColor("#F0F0F0"))
        owner_id_card.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        populateTexts()
        process_card.visibility = View.GONE
    }

    fun loadPolicyDetailsCards() {
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
        name_text.setText(insuranceDetails.name)
        id_text.setText(insuranceDetails.ic.toString())
        owner_text.setText(insuranceDetails.name)
        owner_id_text.setText(insuranceDetails.ic.toString())
        if (insuranceDetails is ActivePolicy) {
            process_text.setText((insuranceDetails as ActivePolicy).process)
        }
    }
}
