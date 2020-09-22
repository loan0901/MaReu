package loan.louise.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import loan.louise.mareu.R;
import loan.louise.mareu.model.Meeting;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        List<Meeting> meetingList = new ArrayList<Meeting>();
        meetingList.add(new Meeting(1,"A", "16H00", "Mario", "mario@gmail.com", 10));
        meetingList.add(new Meeting(2,"B", "16H00", "Mario", "mario@gmail.com", 10));
        meetingList.add(new Meeting(3,"C", "16H00", "Mario", "mario@gmail.com", 10));
        meetingList.add(new Meeting(4,"D", "16H00", "Mario", "mario@gmail.com", 10));
        meetingList.add(new Meeting(5,"E", "16H00", "Mario", "mario@gmail.com", 10));

        MeetingAdapter adapter = new MeetingAdapter(meetingList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FloatingActionButton creatButton = findViewById(R.id.addActivityButton);
        creatButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
        startActivity(intent);
    }
}