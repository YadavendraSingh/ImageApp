package com.yadu.imageapp.view.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.yadu.imageapp.R
import kotlinx.android.synthetic.main.main_activity.view.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun getImageRepository() {
    }

    @Test
    fun setImageRepository() {
    }

    @Test
    fun onCreate() {
    }

    @Test
    fun myScrollTest(){
        var rec:RecyclerView = activityTestRule.activity.findViewById(R.id.rv_image_list)
        var count:Int = rec.adapter!!.itemCount
        Espresso.onView(ViewMatchers.withId(R.id.rv_image_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(count-1))
    }

}