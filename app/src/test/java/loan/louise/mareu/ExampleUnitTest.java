package loan.louise.mareu;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import loan.louise.mareu.DI.DI;
import loan.louise.mareu.model.Meeting;
import loan.louise.mareu.service.ApiService;
import loan.louise.mareu.service.MeetingList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private ApiService service;

    @Before
    public void setup() {
        service = DI.getMeetingApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Meeting> meetings = service.getMeeting();
        List<Meeting> expectedMeetings = MeetingList.MEETINGLIST;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Meeting meetingToDelete = service.getMeeting().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeeting().contains(meetingToDelete));
    }


}