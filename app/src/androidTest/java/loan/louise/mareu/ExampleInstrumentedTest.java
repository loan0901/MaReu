package loan.louise.mareu;

import android.widget.DatePicker;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.AllOf.allOf;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import loan.louise.mareu.ui.activity.MainActivity;
import loan.louise.mareu.utils.DeleteViewAction;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExampleInstrumentedTest {

    private MainActivity mActivity;

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule<>(MainActivity.class);



    @Test
    public void testA_meetingsList_shouldNotBeEmpty() {
        onView(withId(R.id.recyclerView)).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void testB_deleteButton_shouldRemoveItem() {
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(5)));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(2, new DeleteViewAction()));
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(4)));
    }

    @Test
    public void testC_openAddActivity() {
        onView(withId(R.id.addActivityButton)).perform(click());
        onView(withId(R.id.addMeetingActivity)).check(matches(isDisplayed()));
    }

    @Test
    public void testD_createNewMeeting(){
        onView(withId(R.id.addActivityButton)).perform(click());
        onView(withId(R.id.timePicker)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.datePicker)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2020, 12, 30));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.editTextSubject)).perform(click(), typeText("test"));
        pressBack();
        onView(withId(R.id.editTextMail)).perform(click(), typeText("test@gmail.com"), pressImeActionButton());
        pressBack();
        onView(withId(R.id.createButton)).perform(click());
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(5)));
    }

    @Test
    public void testE_filterByDate(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.filterByDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2020, 12, 31));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(1)));
    }

    @Test
    public void testF_filterByRoom(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.filterByRoom)).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(2)));
    }
}
