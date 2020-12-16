package loan.louise.mareu;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import loan.louise.mareu.di.DI;
import loan.louise.mareu.model.Meeting;
import loan.louise.mareu.service.ApiService;
import loan.louise.mareu.service.MeetingList;

import static org.junit.Assert.*;

public class UnitTest {

    private ApiService service;

    @Before
    public void setup() {
        service = DI.getMeetingApiService();
    }

    @Test
    public void getMeetingWithSuccess() {
        List<Meeting> meetings = service.getMeeting();
        List<Meeting> expectedMeeting = MeetingList.MEETINGLIST;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeeting.toArray()));
    }

    @Test
    public void getMeetingSubject(){
        Meeting meetingToTest = service.getMeeting().get(0);
        assertEquals("approvisionnement", meetingToTest.getMeetingSubject());
    }

    @Test
    public void filterByRoom(){
        List<Meeting> meetings = service.getMeeting();
        assertNotEquals(meetings, service.getMeetingByRoom("Salle A"));
    }

    @Test
    public void filterByDate(){
        List<Meeting> meetings = service.getMeeting();
        assertNotEquals(meetings, service.getMeetingByDate("30/12/2020"));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Meeting meetingToDelete = service.getMeeting().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeeting().contains(meetingToDelete));
    }
}