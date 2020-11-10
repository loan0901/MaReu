package loan.louise.mareu.ui;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

import loan.louise.mareu.R;
import loan.louise.mareu.databinding.MeetingItemBinding;
import loan.louise.mareu.model.Meeting;

    public class MeetingViewHolder extends RecyclerView.ViewHolder {

        ImageView colorImage;
        TextView meetingRoomTextView;
        TextView mailTextView;
        ImageView deleteButton;

        public MeetingViewHolder(@NonNull View itemView) {
            super(itemView);

            colorImage = itemView.findViewById(R.id.imageViewColor);
            meetingRoomTextView = itemView.findViewById(R.id.textViewRoom);
            mailTextView = itemView.findViewById(R.id.textViewMail);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public void display(Meeting meeting) {
            meetingRoomTextView.setText(itemView.getContext().getString(R.string.meeting_label,
                    meeting.getMeetingRoom(), meeting.getMeetingHour(), meeting.getMeetingSubject()));
            mailTextView.setText(meeting.getMail());

            //Generate random color:
            Random random = new Random();
            int color = Color.argb(135, random.nextInt(256), random.nextInt(256), random.nextInt(256));
            colorImage.setBackgroundColor(color);
        }
    }