package com.yohanbernole.lamzone;

import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.yohanbernole.lamzone.di.DI;
import com.yohanbernole.lamzone.service.MeetingApiService;
import com.yohanbernole.lamzone.utils.DeleteViewAction;
import com.yohanbernole.lamzone.ui.CustomDatePicker;
import com.yohanbernole.lamzone.ui.MeetingActivity;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.yohanbernole.lamzone.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class LamzoneMeetingInstrumentedTest {

    private MeetingApiService mApiService;

    @Rule
    public ActivityTestRule<MeetingActivity> mActivityRule =
            new ActivityTestRule<>(MeetingActivity.class);

    @Before
    public void setUp() {
        MeetingActivity mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        mApiService = DI.getMeetingApiService();
    }

    @Test
    public void useAppContext() {
        Context appContext = getInstrumentation().getTargetContext();
        assertEquals("com.yohanbernole.lamzone", appContext.getPackageName());
    }

    @Test
    public void mMeetingList_shouldNotBeEmpty() {
            onView(withId(R.id.container))
                .check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void myMeetingList_deleteAction_shouldRemoveItem() {
        int size = mApiService.getMeetings().size();
        onView(withId(R.id.container)).check(withItemCount(size));
        onView(withId(R.id.container)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        onView(withId(R.id.container)).check(withItemCount(size-1));
    }

    @Test
    public void filterByRoomWithSuccess() {
        onView(withId(R.id.filter_meeting)).perform(click());
        onView(ViewMatchers.withText("Par Salle")).perform(click());
        onView(withText("Salle A2")).check(matches(isNotChecked())).perform(click());
        onView(withText("OK")).perform(click());
    }

    @Test
    public void filterByDateWithSuccess() {
        onView(withId(R.id.filter_meeting)).perform(click());
        onView(ViewMatchers.withText("Par Date")).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1970,1, 19));
        onView(withText("OK")).perform(click());
    }

    @Test
    public void reinitializeFilterWithSuccess() {
        onView(withId(R.id.filter_meeting)).perform(click());
        onView(ViewMatchers.withText("Par Date")).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1970,1, 19));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.filter_meeting)).perform(click());
        onView(withText("Réinitialiser")).perform(click());
    }

    @Test
    public void detailsActivityLaunchedWithSuccess() {
        onView(withId(R.id.container)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.details_meeting_name)).check(matches(isDisplayed()));
    }

    @Test
    public void addNewMeetingWithSuccess() {
        onView(withId(R.id.meeting_activity_add_meeting_button)).perform(click());
        onView(withId(R.id.edit_text_meeting_name)).perform(click()).perform(typeText("Test"));
        onView(withId(R.id.edit_text_subject_meeting)).perform(click()).perform(typeText("TestSubject"));
        onView(withClassName(Matchers.equalTo(CustomDatePicker.class.getName()))).perform(scrollTo(), PickerActions.setDate(2020,5, 19));
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(scrollTo(), PickerActions.setTime(12, 50));
        onView(withId(R.id.edit_text_meeting_duration)).perform(scrollTo(), click()).perform(typeText("120"));
        onView(withId(R.id.edit_text_email_user_meeting)).perform(scrollTo(), click()).perform(typeText("yohan.bernole@gmail.com"));
        onView(withId(R.id.button_add_user)).perform(scrollTo(), click());
        onView(withId(R.id.button_add_meeting)).perform(scrollTo(), click());
        onView(withId(R.id.filter_meeting)).perform(click());
        onView(ViewMatchers.withText("Par Date")).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2020,5, 19));
        onView(withText("OK")).perform(click());
    }

    @Test
    public void addNewUserWithSuccess() {
        onView(withId(R.id.meeting_activity_add_meeting_button)).perform(click());
        onView(withId(R.id.add_new_user)).perform(click());
        onView(withId(R.id.edit_text_username)).perform(click()).perform(typeText("Test"));
        onView(withId(R.id.edit_text_user_email)).perform(click()).perform(typeText("TestSubject@mail.com"));
        onView(withId(R.id.button_submit_new_user)).perform(click());
    }
}

