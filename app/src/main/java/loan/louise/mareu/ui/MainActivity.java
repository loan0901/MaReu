package loan.louise.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import loan.louise.mareu.DI.DI;
import loan.louise.mareu.Event.DeleteMeetingEvent;
import loan.louise.mareu.R;
import loan.louise.mareu.service.ApiService;
import loan.louise.mareu.service.MeetingApiService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = DI.getMeetingApiService();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        MeetingAdapter adapter = new MeetingAdapter(apiService.getMeeting());
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

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        ApiService.deleteMeeting(event.meeting);
    }
}