package loan.louise.mareu.ui.activity;

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
import androidx.fragment.app.DialogFragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.Calendar;

import loan.louise.mareu.di.DI;
import loan.louise.mareu.R;
import loan.louise.mareu.databinding.AddMeetingBinding;
import loan.louise.mareu.model.Meeting;
import loan.louise.mareu.service.ApiService;
import loan.louise.mareu.ui.fragment.DatePickerFragment;
import loan.louise.mareu.ui.fragment.TimePickerFragment;

public class AddMeetingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private AddMeetingBinding binding;
    private ApiService apiService;
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

    private void getMeetingRoom() {
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(this, R.layout.spinner_resource, getResources().getStringArray(R.array.meeting_room));
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerRoom.setAdapter(roomAdapter);
    }

    private void getMeetingDate() {
        binding.datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        String date = dayOfMonth + "/" + month + "/" + year;
        binding.datePicker.setText(date);

    }

    private void getMeetingHour() {
        binding.timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, hourOfDay, minute);
        binding.timePicker.setText(DateFormat.format("hh:mm", calendar));
    }

    private void chipItem(String emailAddress) {
        LayoutInflater inflater = LayoutInflater.from(AddMeetingActivity.this);
        Chip chip = (Chip) inflater.inflate(R.layout.chip_item, null, false);
        chip.setText(emailAddress);
        binding.emailsGroup.addView(chip);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.emailsGroup.removeView(v);
            }
        });
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

    private String getEmailFromChipGroup() {
        ChipGroup chipGroup = binding.emailsGroup;
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            if (emailResult == null) {
                emailResult = chip.getText().toString() + ", ";
            } else {
                emailResult = emailResult + chip.getText().toString() + ", ";
            }
        }
        return emailResult;
    }

    private void createMeeting() {
        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.datePicker.getText().toString().isEmpty() ||
                        binding.timePicker.getText().toString().isEmpty() ||
                        binding.emailsGroup.getChildCount() < 1 ||
                        binding.editTextSubject.getText().toString().isEmpty()) {
                    Toast.makeText(AddMeetingActivity.this, "réunion incomplète", Toast.LENGTH_LONG).show();
                } else {
                    Meeting meeting = new Meeting(
                            System.currentTimeMillis(),
                            binding.spinnerRoom.getSelectedItem().toString(),
                            binding.timePicker.getText().toString(),
                            binding.datePicker.getText().toString(),
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