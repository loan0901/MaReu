package loan.louise.mareu.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import loan.louise.mareu.model.Meeting;

public  class MeetingList {

    public static List<Meeting> MEETINGLIST = Arrays.asList(
            new Meeting(1, "Salle A", "16H00",new Date(2020,1,1), "mario", "Lolo@gmail.com"),
            new Meeting(2, "Salle B", "16H00",new Date(2020,2,1) , "mario", "Lolo@gmail.com"),
            new Meeting(3, "Salle C", "16H00",new Date(2020,3,1) , "mario", "Lolo@gmail.com"),
            new Meeting(4, "Salle D", "16H00",new Date(2020,4,1) , "mario", "Lolo@gmail.com"),
            new Meeting(5, "Salle E", "16H00",new Date(2020,5,1) , "mario", "Lolo@gmail.com")
    );

    public static List<Meeting> generateMeeting() {
        return new ArrayList<>(MEETINGLIST);
    }
}