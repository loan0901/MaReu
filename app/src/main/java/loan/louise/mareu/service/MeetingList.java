package loan.louise.mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import loan.louise.mareu.model.Meeting;

public  class MeetingList {

    public static List<Meeting> MEETINGLIST = Arrays.asList(
            new Meeting(1, "Salle A", "09H00","10/12/2020", "comptabilité", "tom2754@gmail.com, lucas76@gmail.com"),
            new Meeting(2, "Salle B", "11H00","11/12/2020", "approvisionnement", "christopheH@gmail.com, julTulot@gmail.com"),
            new Meeting(3, "Salle C", "14H00","12/12/2020", "marketing", "thomas60@gmail.com, PaulHulite@gmail.com"),
            new Meeting(4, "Salle D", "18H00","13/12/2020", "communication", "théoDupres@gmail.com, lucas76@gmail.com"),
            new Meeting(5, "Salle E", "19H00","14/12/2020", "planning", "GaelFlate@gmail.com, thomas60@gmail.com")
    );

    static List<Meeting> generateMeeting() {
        return new ArrayList<>(MEETINGLIST);
    }
}