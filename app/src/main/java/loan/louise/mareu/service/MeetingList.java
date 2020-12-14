package loan.louise.mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import loan.louise.mareu.model.Meeting;

public  class MeetingList {

    public static List<Meeting> MEETINGLIST = Arrays.asList(
            new Meeting(1, "Salle A", "09:00","27/12/2020", "comptabilité", "tom2754@gmail.com, lucas76@gmail.com"),
            new Meeting(2, "Salle B", "11:00","28/12/2020", "approvisionnement", "christopheH@gmail.com, julTulot@gmail.com"),
            new Meeting(3, "Salle C", "14:00","29/12/2020", "marketing", "thomas60@gmail.com, PaulHulite@gmail.com"),
            new Meeting(4, "Salle D", "18:00","30/12/2020", "communication", "théoDupres@gmail.com, lucas76@gmail.com"),
            new Meeting(5, "Salle E", "19:00","31/12/2020", "planning", "GaelFlate@gmail.com, thomas60@gmail.com")
    );

    static List<Meeting> generateMeeting() {
        return new ArrayList<>(MEETINGLIST);
    }
}