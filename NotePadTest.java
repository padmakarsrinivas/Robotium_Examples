package com.example.android.notepad;
/**
 * Created by srinivasv on 02-02-2017.
 */

import com.robotium.solo.Solo;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class NotePadTest {

    @Rule
    public ActivityTestRule<NotesList> activityTestRule =
            new ActivityTestRule<>(NotesList.class);

    private Solo solo;

    @Before
    //setUp() is run before a test cases are started.
    public void setUp() throws Exception {
        //This is where the solo object is created.
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                activityTestRule.getActivity());
    }

    @After
    //tearDown() is run after a tests are completed.
    public void tearDown() throws Exception {
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    @Test
    public void testAddNote() throws Exception {
        int addnote = 7;
        // Adding mentioned number of Notes
        for (int i = 0; i < addnote; i++) {
            // Clicking on New Notes.
            clickNewNote();
            // Assert Activity after waiting for timeout.
            assertTrue(solo.waitForActivity(NoteEditor.class, 1000));
            // Asserting if we are in write activity.
            solo.assertCurrentActivity("Expected NoteEditor Activity", NoteEditor.class);
            // Waiting for View with id, number of matches, timeout.
            solo.waitForView(R.id.note, 0, 1000);
            // Entering text into the edit text view with index.
            solo.enterText(0, "Test" + i);
            // Clicking on Save button to Notes.
            saveNote();
            // Assert for activity with time out
            assertTrue(solo.waitForActivity("NotesList", 2000));
            assertTrue(solo.searchText("Test" + i));
        }
        // Asserting there are same text views as mentioned at the starting.
        assertEquals(addnote, getNoOfTextViews());
        //Takes a screenshot and saves it in "/sdcard/Robotium-Screenshots/".
        solo.takeScreenshot();
    }

    @Test
    public void testDeleteNote() throws Exception {

        int count = getNoOfTextViews();
        // Checking if count is greater than 0.
        if (count > 0) {
            // Iterating count times
            for (int i = 0; i < count; i++)
                // Checking if count is even or odd.
                if (i % 2 == 0) {
                    solo.clickInList(0);
                    // Asserting if we are in editor activity.
                    assertTrue(solo.waitForActivity("NoteEditor", 1000));
                    // Deleting notes by clicking on Delete in the Editor View.
                    deleteNote();
                    // Assert for activity with time out
                    assertTrue(solo.waitForActivity("NotesList", 1000));
                } else {
                    // Clicking Long on the 1st item in the Notes List.
                    solo.clickLongInList(0);
                    // Clicking on Delete String.
                    solo.clickOnText(solo.getString(R.string.menu_delete));
                    // Assert for activity with time out
                    assertTrue(solo.waitForActivity("NotesList", 1000));
                }
        }
        count = getNoOfTextViews();
        // Asserting all the text views are deleted.
        assertEquals(0, count);
    }

    @Test
    public void testEditNoteTitle() throws Exception {

        int count = getNoOfTextViews();
        for (int i = 0; i < count; i++){
            solo.clickOnText("Test"+i);
            solo.clickOnMenuItem("Edit title");
            // Clear Edit Box with index
            solo.clearEditText(1);
            // Enter text into Edit Box with index
            solo.enterText(1, "Test0"+i);
            // Click on Button OK
            solo.clickOnButton("OK");
            // Click on Save button
            saveNote();
            // Assert for activity with time out
            assertTrue(solo.waitForActivity("NotesList", 1000));
            // Assert if the changes title is present in the list or not
            assertTrue("New title is not present", solo.searchText("Test0"+i));
        }
    }

    public int getNoOfTextViews() {
        // Wait for lit view with name, index and timeout
        solo.waitForView(ListView.class, 0, 1000);
        // Getting the count of text views in the activity which are visible.
        ArrayList<TextView> textView = solo.getCurrentViews(TextView.class,
                solo.getCurrentViews(ListView.class).get(0));
        return textView.size();
    }

    public void clickNewNote() {
        solo.clickOnView(solo.getView(R.id.menu_add));
    }

    public void saveNote() {
        solo.clickOnView(solo.getView(R.id.menu_save));
    }

    public void deleteNote() {
        solo.clickOnView(solo.getView(R.id.menu_delete));
    }
}