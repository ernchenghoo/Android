package com.example.kotlinexercise

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinexercise.Model.ActivePolicy
import com.example.kotlinexercise.Model.Business
import io.realm.Realm
import io.realm.kotlin.*
import kotlinx.android.synthetic.main.business_cardview.view.*

class RecyclerViewAdapter(var mainActivityRef: MainActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var database = Realm.getDefaultInstance()
    var businessData : ArrayList<InsuranceDetailsInterface> = arrayListOf()
    var policyData : ArrayList<InsuranceDetailsInterface> = arrayListOf()

    var businessFullData : ArrayList<InsuranceDetailsInterface> = arrayListOf()

//    fun submitList(businessList: ArrayList<InsuranceDetails>) {
//        val oldList = businessItems
//        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
//            InsuranceDetailsItemDiffCallback(oldList, businessList)
//        )
//        businessItems = businessList
//        businessFullData = businessItems
//        diffResult.dispatchUpdatesTo(this)
//    }

    //get list from main activity
    fun refreshRecyclerViewContent() {
        businessData.clear()
        policyData.clear()
        for (business in database.where<Business>().findAll()) {
            businessData.add(business)
        }
        for (policy in database.where<ActivePolicy>().findAll()) {
            policyData.add(policy)
        }
        businessFullData = businessData

    }

    fun populateBusinessData() {
        businessFullData = businessData
        notifyDataSetChanged()
    }

    fun populatePolicyData() {
        businessFullData = policyData
        notifyDataSetChanged()
    }

//    fun updateData() {
//        val oldList = businessFullData
//        initializeRecyclerView() //update businessFullData to new list based on database
//        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
//            InsuranceDetailsItemDiffCallback(oldList, businessFullData)
//        )
//        diffResult.dispatchUpdatesTo(this)
//    }

    //get updated list from activity when search
    fun searchRecyclerView(searchedList: ArrayList<InsuranceDetailsInterface>) {
        businessFullData = searchedList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return InsuranceDetailsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.business_cardview, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return businessFullData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is InsuranceDetailsViewHolder -> {
                holder.bind(businessFullData.get(position),  mainActivityRef)
            }
        }
    }

    //what to do for each holder in recycler view when binding
    class InsuranceDetailsViewHolder constructor(businessView: View) : RecyclerView.ViewHolder (businessView) {

        val businessIC = itemView.nricText
        val businessName = itemView.nameText
        val businessPolicyNo = itemView.policyText
        val businessProcessTitle = itemView.process_card
        val businessProcess = itemView.process_box
        val businessIcon = itemView.business_icon
        val cardHolder = itemView.card

        fun bind(business: InsuranceDetailsInterface, ref: MainActivity){
            businessIC.setText(business.ic.toString())
            businessName.setText(business.name)
            businessPolicyNo.setText(business.policyNo)
            if (business is ActivePolicy) {
                businessProcessTitle.visibility = View.VISIBLE
                businessProcess.setText(business.process)
                businessProcess.visibility = View.VISIBLE
            }
            else {
                businessProcessTitle.visibility = View.GONE
                businessProcess.visibility = View.GONE
            }


            cardHolder.setOnClickListener {
                val intent = Intent(ref, InsuranceDetailsActivity::class.java)
                intent.putExtra("form", "edit")
                if (business is Business) {
                    intent.putExtra("type", "business")
                    intent.putExtra("IC", business.ic)
                }
                if (business is ActivePolicy) {
                    intent.putExtra("type", "policy")
                    intent.putExtra("IC", business.ic)
                }
                ref.startActivity(intent)
            }
            when (business.typeID) {
                0 -> businessIcon.setImageResource(R.drawable.image0)
                1 -> businessIcon.setImageResource(R.drawable.image1)
                2 -> businessIcon.setImageResource(R.drawable.image2)
            }
        }
    }

    class InsuranceDetailsItemDiffCallback(var oldList: ArrayList<InsuranceDetailsInterface>,
                                           var newList: ArrayList<InsuranceDetailsInterface>): DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (oldList.get(oldItemPosition).ic == newList.get(oldItemPosition).ic)
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition))
        }
    }

    fun filter (searchText: String) {

        val filterList: ArrayList<InsuranceDetailsInterface> = arrayListOf()

        for (b: InsuranceDetailsInterface in businessFullData) {
            if (b.ic.toString().toLowerCase().contains(searchText.toLowerCase()) || b.name.toLowerCase().contains(searchText.toLowerCase()) ||
                b.policyNo.toLowerCase().contains(searchText.toLowerCase())) {
                filterList.add(b)
            }
        }
        searchRecyclerView((filterList))
    }
}