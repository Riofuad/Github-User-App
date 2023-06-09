package com.example.githubuserapp

import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.githubuserapp.di.EspressoIdling
import com.example.githubuserapp.ui.activities.MainActivity
import org.hamcrest.Matcher
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4ClassRunner::class)
class ApplicationTest {
    private val userInput = "riofuad"

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdling.idlingResource)
    }

    @Test
    fun favoriteUser() {
        onView(withId(R.id.menu_search)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText(userInput),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )
        Thread.sleep(1000)
        onView(withId(R.id.rv_user)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        val storeName = getText(onView(withId(R.id.tv_detail_username)))
        onView(withId(R.id.fab_favorite)).perform(click())
        pressBack()
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withId(R.id.rv_favorite_user)).check(matches(hasDescendant(withText(storeName))))
            .perform(click())
        onView(withId(R.id.rv_favorite_user)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.fab_favorite)).perform(click())
        pressBack()
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdling.idlingResource)
    }

    private fun getText(matcher: ViewInteraction): String {
        var text = String()
        matcher.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "Text of the view"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                text = tv.text.toString()
            }
        })
        return text
    }
}