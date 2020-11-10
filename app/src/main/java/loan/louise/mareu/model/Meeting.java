package loan.louise.mareu.model;

public class Meeting {

    private long id;
    private String meetingRoom;
    private String meetingHour;
    private String meetingSubject;
    private String mail;
    private String meetingDate;

    public Meeting(long id, String meetingRoom, String meetingHour, String meetingDate, String meetingSubject, String mail) {

        this.id = id;
        this.meetingRoom = meetingRoom;
        this.meetingHour = meetingHour;
        this.meetingDate = meetingDate;
        this.meetingSubject = meetingSubject;
        this.mail = mail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

}
