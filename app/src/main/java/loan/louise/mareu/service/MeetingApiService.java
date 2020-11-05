package loan.louise.mareu.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    @Override
    public Date formatDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }


    @Override
    public List<Meeting> getMeetingByRoom(String meetingRoom) {
        List<Meeting> meetingList = MeetingList.generateMeeting();
        List<Meeting> meetingByRoom = new ArrayList<>();
        for(int i = 0; i < meetingList.size(); i++) {
            if (meetingList.get(i).getMeetingRoom().equals(meetingRoom)){
                meetingByRoom.add(meetingList.get(i));
            }
        }
        return meetingByRoom;
    }

    @Override
    public List<Meeting> getMeetingByDate(Date meetingDate) {
        List<Meeting> meetingList = MeetingList.generateMeeting();
        List<Meeting> meetingByDate = new ArrayList<>();
        for(int i = 0; i < meetingList.size(); i++) {
            // modifier le ".equals"
            if (meetingList.get(i).getMeetingDate().compareTo(meetingDate)==0){
                meetingByDate.add(meetingList.get(i));
            }
        }
        return meetingByDate;
    }


}
