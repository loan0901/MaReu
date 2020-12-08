package loan.louise.mareu.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import loan.louise.mareu.di.DI;
import loan.louise.mareu.event.DeleteMeetingEvent;
import loan.louise.mareu.R;
import loan.louise.mareu.databinding.ActivityMainBinding;
import loan.louise.mareu.model.Meeting;
import loan.louise.mareu.service.ApiService;
import loan.louise.mareu.ui.adapter.MeetingAdapter;
import loan.louise.mareu.ui.fragment.DatePickerFragment;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ApiService apiService;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = DI.getMeetingApiService();

        initBinding();
        addMeetingActivityButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initList(apiService.getMeeting());
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

    private void initList(List<Meeting> meetingList) {
        MeetingAdapter adapter = new MeetingAdapter(meetingList);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filterRoom:
                // Filter by room :
                filterByRoom();
                return true;
            case R.id.filterDate:
                // filter by Date :
                dateClickListener();
                return true;
            case R.id.cancel:
                // show full list :
                initList(apiService.getMeeting());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void filterByRoom() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View v = getLayoutInflater().inflate(R.layout.spinner_item, null);
        mBuilder.setTitle(getString(R.string.filterLabel));
        final Spinner mSpinner = (Spinner) v.findViewById(R.id.spinnerItem);
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(this, R.layout.spinner_resource, getResources().getStringArray(R.array.meeting_room));
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(roomAdapter);

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initList(apiService.getMeetingByRoom(mSpinner.getSelectedItem().toString()));
            }
        });
        mBuilder.setView(v);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void dateClickListener() {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        String date = dayOfMonth + "/" + month + "/" + year;
        initList(apiService.getMeetingByDate(date));
    }

    private void addMeetingActivityButton() {
        binding.addActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        apiService.deleteMeeting(event.meeting);
        initList(apiService.getMeeting());
    }

    private void initBinding() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}