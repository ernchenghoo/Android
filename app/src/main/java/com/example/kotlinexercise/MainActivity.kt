package com.example.kotlinexercise

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinexercise.Model.ActivePolicy
import com.example.kotlinexercise.Model.Business
import io.realm.Realm
import io.realm.kotlin.*
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    var isBusiness = true //is it business page

    lateinit var rvAdapter: RecyclerViewAdapter //adapter instance
    lateinit var database: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //Realm section
        database = Realm.getDefaultInstance()

//        database.executeTransaction {database -> database.deleteAll()} //empty database every run
        //End database section

//        createBusinessDataset(database) //generate business data
//        createPolicyDataset(database) //generate policy data

        initRecyclerView() //initialize recycle view
        rvAdapter.refreshRecyclerViewContent()

        updateNumbers()

        policyButton.setOnClickListener{
            if (isBusiness)
                loadPolicy()
        }

        businessButton.setOnClickListener{
            if (!isBusiness)
                loadBusiness()
        }

        add_button.setOnClickListener{
            intent = Intent(this, NewRecordActivity::class.java)
            intent.putExtra("form", "new")
            if (isBusiness)
                intent.putExtra("type", "business")
            else
                intent.putExtra("type", "policy")
            startActivity(intent)
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(word: String?): Boolean { //whenever a word is inserted or deleted in search view
                search(word)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        rvAdapter.refreshRecyclerViewContent()
        updateNumbers()
        searchView.setQuery("",false)
        if (isBusiness) {
            rvAdapter.populateBusinessData()
        }
        else {
            rvAdapter.populatePolicyData()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        System.exit(0)
    }

    private fun updateNumbers() {
        business_number.setText("%d".format(rvAdapter.businessData.size))
        policy_number.setText("%d".format(rvAdapter.policyData.size))
        pendingCaseText.setText("Showing %d pending case".format(rvAdapter.itemCount))
    }

    private fun loadBusiness() {
        policy_number.setTextColor(Color.parseColor("#F7D8D8"))//set to light red
        business_number.setTextColor(Color.parseColor("#D62C2C"))//set to red
        rvAdapter.populateBusinessData()
        isBusiness = true
        search(searchView.query.toString())
    }

    private fun loadPolicy() {
        business_number.setTextColor(Color.parseColor("#F7D8D8"))//set to light red
        policy_number.setTextColor(Color.parseColor("#D62C2C"))//set to red
        rvAdapter.populatePolicyData()
        isBusiness = false
        search(searchView.query.toString())
    }

    private fun initRecyclerView() {
        policyRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            val topSpacingDeco = EdocRecyclerViewDeco(30)
            addItemDecoration(topSpacingDeco)
            rvAdapter = RecyclerViewAdapter(this@MainActivity)
            adapter = rvAdapter
        }
    }

    //filter based on text and update adapter, refresh recyclerview
    private fun search(newText: String?) {
        if (!newText.isNullOrEmpty()) {
            rvAdapter.filter(newText)
            pendingCaseText.setText("Showing %d pending case".format(rvAdapter.itemCount))
        }
        else {
            if (isBusiness) {//if its business page, pass business list to adapter
                rvAdapter.populateBusinessData()
                pendingCaseText.setText("Showing %d pending case".format(rvAdapter.itemCount))
            }
            else {
                // else pass policy list to adapter
                rvAdapter.populatePolicyData()
                pendingCaseText.setText("Showing %d pending case".format(rvAdapter.itemCount))
            }
        }
    }

    companion object {

        fun createBusinessDataset(realm: Realm) {
            @Suppress("NAME_SHADOWING")
            realm.executeTransaction { realm ->
                val newBusiness = realm.createObject<Business>(920344372839)
                newBusiness.name = "John Doe"
                newBusiness.policyNo = "TL293847643"
                newBusiness.typeID = 0
            }
            @Suppress("NAME_SHADOWING")
            realm.executeTransaction { realm ->
                val newBusiness = realm.createObject<Business>(800918203928)
                newBusiness.name = "Dwayne Johnson"
                newBusiness.policyNo = "TL938273623"
                newBusiness.typeID = 1
            }
            @Suppress("NAME_SHADOWING")
            realm.executeTransaction { realm ->
                val newBusiness = realm.createObject<Business>(830112009283)
                newBusiness.name = "Gerard Way"
                newBusiness.policyNo = "TL119283732"
                newBusiness.typeID = 2
            }
            @Suppress("NAME_SHADOWING")
            realm.executeTransaction { realm ->
                val newBusiness = realm.createObject<Business>(920331109283)
                newBusiness.name = "Ozzy Osbourne"
                newBusiness.policyNo = "TL44483723"
                newBusiness.typeID = 0
            }
            @Suppress("NAME_SHADOWING")
            realm.executeTransaction { realm ->
                val newBusiness = realm.createObject<Business>(900101090080)
                //newBusiness.ic = 980227438293
                newBusiness.name = "Tre Cool"
                newBusiness.policyNo = "TL229382743"
                newBusiness.typeID = 0
            }
            @Suppress("NAME_SHADOWING")
            realm.executeTransaction { realm ->
                val newBusiness = realm.createObject<Business>(938473628323)
                //newBusiness.ic = 980227438293
                newBusiness.name = "Brian May"
                newBusiness.policyNo = "TL293820373"
                newBusiness.typeID = 0
            }
        }

        fun createPolicyDataset(realm: Realm) {
            @Suppress("NAME_SHADOWING")
            realm.executeTransaction { realm ->
                val newActivePolicy = realm.createObject<ActivePolicy>(749484358493)
                newActivePolicy.name = "Freddie Mercury"
                newActivePolicy.policyNo = "TL049384643"
                newActivePolicy.process = "Deposit Withdrawal"
                newActivePolicy.typeID = 0
            }
            @Suppress("NAME_SHADOWING")
            realm.executeTransaction { realm ->
                val newActivePolicy = realm.createObject<ActivePolicy>(100000000000)
                newActivePolicy.name = "Geralt of Rivia"
                newActivePolicy.policyNo = "TL454567142"
                newActivePolicy.process = "Assignment"
                newActivePolicy.typeID = 1
            }
            @Suppress("NAME_SHADOWING")
            realm.executeTransaction { realm ->
                val newActivePolicy = realm.createObject<ActivePolicy>(650919293847)
                newActivePolicy.name = "Ed Sheeran"
                newActivePolicy.policyNo = "TL938273093"
                newActivePolicy.process = "Assignment"
                newActivePolicy.typeID = 2
            }
            @Suppress("NAME_SHADOWING")
            realm.executeTransaction { realm ->
                val newActivePolicy = realm.createObject<ActivePolicy>(700524098273)
                newActivePolicy.name = "Bruno Mars"
                newActivePolicy.policyNo = "TL049384643"
                newActivePolicy.process = "Deposit Withdrawal"
                newActivePolicy.typeID = 0
            }
            @Suppress("NAME_SHADOWING")
            realm.executeTransaction { realm ->
                val newActivePolicy = realm.createObject<ActivePolicy>(938374829384)
                newActivePolicy.name = "Frank Sinatra"
                newActivePolicy.policyNo = "TL049384643"
                newActivePolicy.process = "Deposit Withdrawal"
                newActivePolicy.typeID = 1
            }
            @Suppress("NAME_SHADOWING")
            realm.executeTransaction { realm ->
                val newActivePolicy = realm.createObject<ActivePolicy>(938473625362)
                newActivePolicy.name = "Donnie Yen"
                newActivePolicy.policyNo = "TL049384643"
                newActivePolicy.process = "Assignment"
                newActivePolicy.typeID = 2
            }

        }
    }
}
