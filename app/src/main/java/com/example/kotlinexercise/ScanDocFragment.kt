package com.example.kotlinexercise

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.fragment_scan_doc.*


class ScanDocFragment : Fragment() {

    var la1Open: Boolean = false
    var la2Open: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan_doc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        life_assured_1_content.visibility = View.GONE
        life_assured_2_content.visibility = View.GONE
        button1.setImageResource(R.drawable.ic_keyboard_arrow_down_24px)
        button2.setImageResource(R.drawable.ic_keyboard_arrow_down_24px)

        button1.setOnClickListener {
            if (!la1Open){
                life_assured_1_content.visibility = View.VISIBLE
                button1.setImageResource(R.drawable.ic_keyboard_arrow_up_24px)
                la1Open = true
            }
            else {
                life_assured_1_content.visibility = View.GONE
                button1.setImageResource(R.drawable.ic_keyboard_arrow_down_24px)
                la1Open = false
            }
        }
        button2.setOnClickListener {
            if (!la2Open){
                life_assured_2_content.visibility = View.VISIBLE
                button2.setImageResource(R.drawable.ic_keyboard_arrow_up_24px)
                la2Open = true
            }
            else {
                life_assured_2_content.visibility = View.GONE
                button2.setImageResource(R.drawable.ic_keyboard_arrow_down_24px)
                la2Open = false
            }
        }
    }
}
