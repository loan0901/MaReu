package loan.louise.mareu.event;

import loan.louise.mareu.model.Meeting;

public class DeleteMeetingEvent {

    public DeleteMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }

    public Meeting meeting;
}
