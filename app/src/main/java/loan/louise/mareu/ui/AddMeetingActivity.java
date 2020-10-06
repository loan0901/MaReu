package loan.louise.mareu.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import loan.louise.mareu.DI.DI;
import loan.louise.mareu.R;
import loan.louise.mareu.model.Meeting;
import loan.louise.mareu.service.ApiService;

public class AddMeetingActivity extends AppCompatActivity {

    private Spinner spinnerRoom;
    private Button createButton;
    private TextView textViewHour, textViewDate;
    private EditText editTextSubject, editTextMail;
    private int meetingHour, meetingMinute;
    private Date meetingDate;
    DatePickerDialog.OnDateSetListener setListenerDate;
    TimePickerDialog.OnTimeSetListener setListenerTime;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meeting);
        apiService = DI.getMeetingApiService();

        spinnerRoom = findViewById(R.id.spinnerRoom);
        createButton = findViewById(R.id.createButton);
        textViewHour = findViewById(R.id.timePicker);
        textViewDate = findViewById(R.id.datePicker);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextMail = findViewById(R.id.editTextMail);

        getMeetingDate();
        getMeetingRoom();
        getMeetingHour();
        createMeeting();
    }

    private void getMeetingDate() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddMeetingActivity.this, setListenerDate, year, month, day);
                datePickerDialog.show();
            }
        });
        setListenerDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth+"/"+month+"/"+year;
                textViewDate.setText(date);
                meetingDate = new Date(year,month,dayOfMonth);
            }
        };
    }

    private void getMeetingHour() {

        textViewHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddMeetingActivity.this, setListenerTime, 12, 0, false);
                timePickerDialog.updateTime(meetingHour,meetingMinute);
                timePickerDialog.show();
            }
        });
        setListenerTime = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                meetingHour = hourOfDay;
                meetingMinute = minute;
                Calendar calendar = Calendar.getInstance();
                calendar.set(0,0,0, meetingHour, meetingMinute);
                textViewHour.setText(DateFormat.format("hh:mm aa", calendar));
            }
        };
    }

    private void getMeetingRoom() {
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.meeting_room));
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoom.setAdapter(roomAdapter);
    }

    private void createMeeting() {
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textViewDate.getText().toString().isEmpty() ||
                            textViewHour.getText().toString().isEmpty() ||
                            editTextMail.getText().toString().isEmpty() ||
                            editTextSubject.getText().toString().isEmpty()) {
                        Toast.makeText(AddMeetingActivity.this, "non", Toast.LENGTH_LONG).show();
                    } else {
                        Meeting meeting = new Meeting(
                                System.currentTimeMillis(),
                                spinnerRoom.getSelectedItem().toString(),
                                textViewHour.getText().toString(),
                                meetingDate,
                                editTextSubject.getText().toString(),
                                editTextMail.getText().toString()
                        );
                        apiService.creatMeeting(meeting);
                        finish();
                    }
                }
            });
    }
}