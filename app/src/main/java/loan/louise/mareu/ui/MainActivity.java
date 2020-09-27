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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ApiService apiService;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = DI.getMeetingApiService();

        recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton createButton = findViewById(R.id.addActivityButton);
        createButton.setOnClickListener(this);

        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initList() {
        MeetingAdapter adapter = new MeetingAdapter(apiService.getMeeting());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
        startActivity(intent);
    }

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        apiService.deleteMeeting(event.meeting);
        initList();
    }
}