package loan.louise.mareu.service;

import java.util.List;

import loan.louise.mareu.model.Meeting;

public class MeetingApiService implements ApiService {

    private List<Meeting> meetingList = MeetingList.generateMeeting();

    @Override
    public List<Meeting> getMeeting() {
        return meetingList;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetingList.remove(meeting);
    }


}
