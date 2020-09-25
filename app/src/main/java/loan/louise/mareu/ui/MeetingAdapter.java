package loan.louise.mareu.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Random;

import loan.louise.mareu.Event.DeleteMeetingEvent;
import loan.louise.mareu.R;
import loan.louise.mareu.model.Meeting;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder>{

    private List<Meeting> meetingList;

    public MeetingAdapter(List<Meeting> meetingList) {
        this.meetingList = meetingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.display(meetingList.get(position));

        //DeleteEvent
        final Meeting meeting = meetingList.get(position);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView colorImage;
        TextView meetingRoom;
        TextView meetingHour;
        TextView meetingSubject;
        TextView mail;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            colorImage = itemView.findViewById(R.id.imageViewColor);
            meetingRoom = itemView.findViewById(R.id.textViewRoom);
            meetingHour = itemView.findViewById(R.id.textViewHour);
            meetingSubject = itemView.findViewById(R.id.textViewSubject);
            mail = itemView.findViewById(R.id.textViewMail);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        void display(Meeting meeting){

            meetingRoom.setText("Réunion " + meeting.getMeetingRoom());
            meetingHour.setText(meeting.getMeetingHour());
            meetingSubject.setText(meeting.getMeetingSubject());
            mail.setText(meeting.getMail());

            //generate random color:
            Random random = new Random();
            int color = Color.argb(135, random.nextInt(256),random.nextInt(256),random.nextInt(256));
            colorImage.setBackgroundColor(color);
        }
    }
}