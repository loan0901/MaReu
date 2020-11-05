package loan.louise.mareu.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import loan.louise.mareu.DI.DI;
import loan.louise.mareu.R;
import loan.louise.mareu.databinding.AddMeetingBinding;
import loan.louise.mareu.model.Meeting;
import loan.louise.mareu.service.ApiService;

public class AddMeetingActivity extends AppCompatActivity {

    private AddMeetingBinding binding;
    private ApiService apiService;
    private int meetingHour, meetingMinute;
    private Date meetingDate;
    DatePickerDialog.OnDateSetListener setListenerDate;
    TimePickerDialog.OnTimeSetListener setListenerTime;
    private String emailResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = DI.getMeetingApiService();

        initBinding();
        addShip();
        getMeetingDate();
        getMeetingRoom();
        getMeetingHour();
        createMeeting();
    }

    private void addShip() {
        binding.editTextMail.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    chipItem(view.getText().toString());
                    view.setText("");
                    return true;
                }
                return false;
            }
        });
    }

    private void chipItem(String emailAddress) {
        LayoutInflater inflater = LayoutInflater.from(AddMeetingActivity.this);
        Chip chip = (Chip) inflater.inflate(R.layout.chip_item,null,false);
        chip.setText(emailAddress);
        binding.emailsGroup.addView(chip);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.emailsGroup.removeView(v);
            }
        });
    }

    private void getMeetingDate() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        binding.datePicker.setOnClickListener(new View.OnClickListener() {
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
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                binding.datePicker.setText(date);
                meetingDate = apiService.formatDate(date);
            }
        };
    }

    private void getMeetingHour() {

        binding.timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddMeetingActivity.this, setListenerTime, 12, 0, false);
                timePickerDialog.updateTime(meetingHour, meetingMinute);
                timePickerDialog.show();
            }
        });
        setListenerTime = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                meetingHour = hourOfDay;
                meetingMinute = minute;
                Calendar calendar = Calendar.getInstance();
                calendar.set(0, 0, 0, meetingHour, meetingMinute);
                binding.timePicker.setText(DateFormat.format("hh:mm aa", calendar));
            }
        };
    }

    private void getMeetingRoom() {
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(this, R.layout.spinner_resource, getResources().getStringArray(R.array.meeting_room));
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerRoom.setAdapter(roomAdapter);
    }

    public String getEmailFromChipGroup() {
        ChipGroup chipGroup = binding.emailsGroup;
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            emailResult = emailResult + chip.getText().toString() + ", ";
        }
        return emailResult;
    }

    private void createMeeting() {
        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.datePicker.getText().toString().isEmpty() ||
                        binding.timePicker.getText().toString().isEmpty() ||
                        binding.emailsGroup.getChildCount()<1 ||
                        binding.editTextSubject.getText().toString().isEmpty()) {
                    Toast.makeText(AddMeetingActivity.this, "non", Toast.LENGTH_LONG).show();
                } else {
                    Meeting meeting = new Meeting(
                            System.currentTimeMillis(),
                            binding.spinnerRoom.getSelectedItem().toString(),
                            binding.timePicker.getText().toString(),
                            meetingDate,
                            binding.editTextSubject.getText().toString(),
                            getEmailFromChipGroup()
                    );
                    apiService.creatMeeting(meeting);
                    finish();
                }
            }
        });
    }

    private void initBinding() {
        binding = AddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}