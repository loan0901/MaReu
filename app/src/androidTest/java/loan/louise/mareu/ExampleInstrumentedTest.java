package loan.louise.mareu;

import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.widget.MenuPopupWindow;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.hamcrest.CoreMatchers;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.espresso.Espresso.onData;
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
import static org.hamcrest.core.AllOf.allOf;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import loan.louise.mareu.ui.activity.MainActivity;
import loan.louise.mareu.utils.DeleteViewAction;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private MainActivity mActivity;

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void meetingsList_shouldNotBeEmpty() {
        onView(allOf(withId(R.id.recyclerView), isDisplayed())).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void deleteButton_shouldRemoveItem() {
        onView(allOf(withId(R.id.recyclerView), isDisplayed())).check(matches(hasChildCount(5)));
        onView(allOf(withId(R.id.recyclerView), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(2, new DeleteViewAction()));
        onView(allOf(withId(R.id.recyclerView), isDisplayed())).check(matches(hasChildCount(4)));
    }

    @Test
    public void openAddActivity() {
        onView(allOf(withId(R.id.addActivityButton),isDisplayed())).perform(click());
        onView(allOf(withId(R.id.addMeetingActivity), isDisplayed())).check(matches(isDisplayed()));
    }

    @Test
    public void creatNewMeeting(){

    }

    @Test
    public void filterByDate(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.filterByDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2020, 12, 8));
        onView(withId(android.R.id.button1)).perform(click());
        //definir une date + verifier le nombre de réu (1)
    }

    @Test
    public void filterByRoom(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.filterByRoom)).perform(click());
        onData(CoreMatchers.anything())
                .inRoot(RootMatchers.isPlatformPopup())
                .inAdapterView(CoreMatchers.<View>instanceOf(MenuPopupWindow.MenuDropDownListView.class))
                .atPosition(1)
                .perform(click());

    }
}
