package loan.louise.mareu.model;

public class Meeting {

    private int id;
    private String meetingRoom;
    private String meetingHour;
    private String meetingSubject;
    private String mail;
    private int meetingDate;

    public Meeting(int id, String meetingRoom, String meetingHour, String meetingSubject, String mail, int meetingDate) {

        this.id = id;
        this.meetingRoom = meetingRoom;
        this.meetingHour = meetingHour;
        this.meetingSubject = meetingSubject;
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(String meetingRoom) {
        this.meetingRoom = meetingRoom;
    }

    public String getMeetingHour() {
        return meetingHour;
    }

    public void setMeetingHour(String meetingHour) {
        this.meetingHour = meetingHour;
    }

    public String getMeetingSubject() {
        return meetingSubject;
    }

    public void setMeetingSubject(String meetingSubject) {
        this.meetingSubject = meetingSubject;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(int meetingDate) {
        this.meetingDate = meetingDate;
    }

}
