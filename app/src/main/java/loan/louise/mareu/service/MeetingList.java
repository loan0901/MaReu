package loan.louise.mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import loan.louise.mareu.model.Meeting;

public  class MeetingList {

    public static List<Meeting> MEETINGLIST = Arrays.asList(
            new Meeting(1, "A", "16H00", "Mario", "mario@gmail.com", 10),
            new Meeting(2, "B", "16H00", "Mario", "mario@gmail.com", 10),
            new Meeting(3, "C", "16H00", "Mario", "mario@gmail.com", 10),
            new Meeting(4, "D", "16H00", "Mario", "mario@gmail.com", 10),
            new Meeting(5, "E", "16H00", "Mario", "mario@gmail.com", 10)
    );

    static List<Meeting> generateMeeting() {
        return new ArrayList<>(MEETINGLIST);
    }
}