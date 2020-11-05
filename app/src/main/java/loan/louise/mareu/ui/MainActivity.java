package loan.louise.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import loan.louise.mareu.DI.DI;
import loan.louise.mareu.Event.DeleteMeetingEvent;
import loan.louise.mareu.R;
import loan.louise.mareu.databinding.ActivityMainBinding;
import loan.louise.mareu.model.Meeting;
import loan.louise.mareu.service.ApiService;
import loan.louise.mareu.service.MeetingList;

public class MainActivity extends AppCompatActivity {

    private ApiService apiService;
    private ActivityMainBinding binding;

    DatePickerDialog.OnDateSetListener setListenerDate;
    private Date meetingDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = DI.getMeetingApiService();

        initBinding();
        initList();
        selectDate();
        addMeetingActivityButton();
        showSelectedMeetingButton();
        selectMeetingRoom();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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

    private void selectMeetingRoom() {
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(this, R.layout.spinner_resource, getResources().getStringArray(R.array.meeting_room));
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSelectRoom.setAdapter(roomAdapter);
    }

    // a revoir:
    public List<Meeting> getMeetingByRoom() {
        List<Meeting> meetingList = MeetingList.generateMeeting();
        List<Meeting> meetingByRoom = new ArrayList<>();
        for(int i = 0; i < meetingList.size(); i++) {
            if (meetingList.get(i).getMeetingRoom().equals(binding.spinnerSelectRoom.getSelectedItem().toString())){
                meetingByRoom.add(meetingList.get(i));
            }
        }
        return meetingByRoom;
    }

    private void showSelectedMeetingButton(){
        binding.buttonTri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeetingAdapter adapter = new MeetingAdapter(getMeetingByDate());
                binding.recyclerView.setAdapter(adapter);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
        });
    }

    private void selectDate() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        binding.textViewSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, setListenerDate, year, month, day);
                datePickerDialog.show();
            }
        });
        setListenerDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                binding.textViewSelectDate.setText(date);
                meetingDate = getSelectedDate(date);
            }
        };
    }

    private Date getSelectedDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public List<Meeting> getMeetingByDate() {
        List<Meeting> meetingList = MeetingList.generateMeeting();
        List<Meeting> meetingByDate = new ArrayList<>();
        for(int i = 0; i < meetingList.size(); i++) {
            // modifier le ".equals"
            if (meetingList.get(i).getMeetingDate().compareTo(meetingDate)==0){
                meetingByDate.add(meetingList.get(i));
            }
        }
        return meetingByDate;
    }

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        apiService.deleteMeeting(event.meeting);
        initList();
    }

    private void initBinding(){
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}