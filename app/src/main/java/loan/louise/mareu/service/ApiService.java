package loan.louise.mareu.service;

import java.util.Date;
import java.util.List;

import loan.louise.mareu.model.Meeting;

public interface ApiService {

    List<Meeting> getMeeting();

    void deleteMeeting(Meeting meeting);

    void creatMeeting(Meeting meeting);

    Date formatDate(String dateString);

    List<Meeting> getMeetingByRoom(String meetingRoom);

    List<Meeting> getMeetingByDate(Date meetingDate);
}
