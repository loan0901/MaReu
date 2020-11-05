package loan.louise.mareu.service;

import java.util.ArrayList;
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

    @Override
    public void creatMeeting(Meeting meeting) {
        meetingList.add(meeting);
    }

    //@Override
    //public List<Meeting> getMeetingByRoom() {
    //    List<Meeting> meetingByRoom = new ArrayList<>();
    //    for(int i = 0; i < meetingList.size(); i++) {
    //        if (meetingList.get(i).getMeetingRoom().toString()==binding.spinnerSelectRoom.getSelectedItem().toString()){
    //            meetingByRoom.add(meetingList.get(i));
    //        }
    //    }
    //    return meetingByRoom;
    //}


}
