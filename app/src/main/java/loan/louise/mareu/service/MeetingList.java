package loan.louise.mareu.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import loan.louise.mareu.model.Meeting;

public  class MeetingList {

    public static List<Meeting> MEETINGLIST = Arrays.asList(
            new Meeting(1, "A", "16H00",new Date(1930,2,22), "mario", "Lolo@gmail.com"),
            new Meeting(2, "B", "16H00",new Date(1930,2,23) , "mario", "Lolo@gmail.com"),
            new Meeting(3, "C", "16H00",new Date(1930,2,24) , "mario", "Lolo@gmail.com"),
            new Meeting(4, "D", "16H00",new Date(1930,2,25) , "mario", "Lolo@gmail.com"),
            new Meeting(5, "E", "16H00",new Date(1930,2,26) , "mario", "Lolo@gmail.com")
    );

    static List<Meeting> generateMeeting() {
        return new ArrayList<>(MEETINGLIST);
    }
}