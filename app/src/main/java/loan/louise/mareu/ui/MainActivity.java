package loan.louise.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import loan.louise.mareu.DI.DI;
import loan.louise.mareu.Event.DeleteMeetingEvent;
import loan.louise.mareu.R;
import loan.louise.mareu.databinding.ActivityMainBinding;
import loan.louise.mareu.service.ApiService;

public class MainActivity extends AppCompatActivity {

    private ApiService apiService;
    private ActivityMainBinding binding;

    DatePickerDialog.OnDateSetListener setListenerDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = DI.getMeetingApiService();

        initBinding();
        initList();
        addMeetingActivityButton();
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
                /** fitrer par salle */
                filterByRoom();
                return true;
            case R.id.filterDate:
                /** fitrer par Date */
                filterByDate();
                return true;
            case R.id.cancel:
                /** afficher la list complete */
                initList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void filterByRoom() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View v = getLayoutInflater().inflate(R.layout.spinner_item, null);
        mBuilder.setTitle("sélectionnez une salle:");
        final Spinner mSpinner = (Spinner) v.findViewById(R.id.spinnerItem);
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(this, R.layout.spinner_resource, getResources().getStringArray(R.array.meeting_room));
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(roomAdapter);

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MeetingAdapter adapter = new MeetingAdapter(apiService.getMeetingByRoom(mSpinner.getSelectedItem().toString()));
                binding.recyclerView.setAdapter(adapter);
            }
        });
        mBuilder.setView(v);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void filterByDate(){
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                MainActivity.this, setListenerDate, year, month, day);
        datePickerDialog.show();
        setListenerDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                Log.d("date","select date: " + date);
                MeetingAdapter adapter = new MeetingAdapter(apiService.getMeetingByDate(date));
                binding.recyclerView.setAdapter(adapter);
            }
        };
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
        initList();
    }

    private void initBinding(){
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}