package loan.louise.mareu.service;

import java.util.List;

import loan.louise.mareu.model.Meeting;

public interface ApiService {

    List<Meeting> getMeeting();

    void deleteMeeting(Meeting meeting);

    void creatMeeting(Meeting meeting);
}
