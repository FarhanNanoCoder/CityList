package com.example.citylist;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.Espresso.onView;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.is;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import static java.lang.Thread.sleep;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testAppName() {
        onView(withText("CityList")).check(matches(isDisplayed())); //Check the name on the screen
    }

    @Test
    public void testAddCity(){
        onView(withId(R.id.button_add)).perform(click()); //Click add button to add a city to the list
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton")); //Type a city name
        onView(withId(R.id.button_confirm)).perform(click()); //Confirm the city name and add to the list
        onView(withText("Edmonton")).check(matches(isDisplayed())); //Check the name on the screen
    }

    @Test
    public void testClearCity(){
        onView(withId(R.id.button_add)).perform(click()); //Click add button to add a city to the list
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton")); //Type a city name
        onView(withId(R.id.button_confirm)).perform(click()); //Confirm the city name and add to the list
        //Add another city to the list
        onView(withId(R.id.button_add)).perform(click()); //Click add button to add a city to the list
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Toronto")); //Type a city name
        onView(withId(R.id.button_confirm)).perform(click()); //Confirm the city name and add to the list
        //Clear the list
        onView(withId(R.id.button_clear)).perform(click());
        onView(withText("Edmonton")).check(doesNotExist());

    }
    @Test
    public void testListView(){
        onView(withId(R.id.button_add)).perform(click()); //Click add button to add a city to the list
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton")); //Type a city name
        onView(withId(R.id.button_confirm)).perform(click()); //Confirm the city name and add to the list

        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).
                check(matches((withText("Edmonton")))); //Check the content on the list - no content in this case
    }

    @Test
    public void testListViewClickAndBack(){
        onView(withId(R.id.button_add)).perform(click()); //Click add button to add a city to the list
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton")); //Type a city name
        onView(withId(R.id.button_confirm)).perform(click()); //Confirm the city name and add to the list


        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click()); //Check the content on the list - no content in this case
        Espresso.pressBack(); //Back button
    }

    @Test
    public void testIntentOpen(){
        Intents.init();
        onView(withId(R.id.main_activity)).check(matches(isDisplayed()));//checking if the xml layout of MainActivity is displayed or not
        onView(withId(R.id.button_add)).perform(click()); //Click add button to add a city to the list
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton")); //Type a city name
        onView(withId(R.id.button_confirm)).perform(click()); //Confirm the city name and add to the list

        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0)
                .check(matches((withText("Edmonton")))); //Check the content on the list

        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());//performing item click in adaptor

        Intents.intended(hasComponent(ShowActivity.class.getName()));//checking if the ShowActivity intent is opened
        onView(withId(R.id.show_details_activity)).check(matches(isDisplayed())); //checking if the xml layout of ShowActivity is displayed or not
        onView(withId(R.id.city_details_name)).check(matches(withText("Edmonton")));//checking if the right data has been plotted
        onView(withId(R.id.city_details_back_button)).perform(click());// performing back action

        onView(withId(R.id.main_activity)).check(matches(isDisplayed()));//checking if the xml layout of MainActivity is displayed or not
        onView(withId(R.id.button_add)).perform(click()); //Click add button to add a city to the list
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Dhaka")); //Type a city name
        onView(withId(R.id.button_confirm)).perform(click()); //Confirm the city name and add to the list
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(1)
                .check(matches((withText("Dhaka"))));//checking if we are back in the MainActivity or not

        Intents.release(); //releasing Intent
    }

}
