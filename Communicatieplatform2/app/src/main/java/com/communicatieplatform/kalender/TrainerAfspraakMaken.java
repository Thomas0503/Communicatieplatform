package com.communicatieplatform.kalender;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.communicatieplatform.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class TrainerAfspraakMaken extends AppCompatActivity {
    private EditText afspraak;
    private EditText datum;
    private EditText locatie;
    private EditText opmerkingen, beginTijd, eindTijd;
    private Button opslaanAfspraak;
    private Button kiesBegintijd, kiesEindtijd, kiesDatum;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afspraak);

        datum = findViewById(R.id.datum);
        beginTijd = findViewById(R.id.begintijdText);
        eindTijd = findViewById(R.id.eindtijdText);
        datum.setEnabled(false);
        beginTijd.setEnabled(false);
        eindTijd.setEnabled(false);
        opslaanAfspraak = findViewById(R.id.opslaanAfspr);
        opslaanAfspraak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    voegAfspraakToe();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        kiesBegintijd = findViewById(R.id.kiesBegintijd);
        kiesBegintijd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });
        kiesEindtijd = findViewById(R.id.kiesEindtijd);
        kiesEindtijd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialogEind(v);
            }
        });
        kiesDatum = findViewById(R.id.kiesDatum);
        kiesDatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
    }
    public void voegAfspraakToe() throws ParseException {
        afspraak = findViewById(R.id.naamafspraak);
        datum = findViewById(R.id.datum);
        beginTijd = findViewById(R.id.begintijdText);
        eindTijd = findViewById(R.id.eindtijdText);
        locatie = findViewById(R.id.locatie);
        opmerkingen = findViewById(R.id.opmerkingen);
        int variableIsNull = 1;
        if (afspraak.getText().toString().equals("")) {
            Toast.makeText(this, "Benoem de afspraak", Toast.LENGTH_SHORT).show();
            variableIsNull = 0;
        } else if (datum.getText().toString().equals("")) {
            Toast.makeText(TrainerAfspraakMaken.this, "Kies een datum", Toast.LENGTH_SHORT).show();
            variableIsNull = 0;
        } else if (beginTijd.getText().toString().equals("")) {
            Toast.makeText(TrainerAfspraakMaken.this, "Kies een starttijd", Toast.LENGTH_SHORT).show();
            variableIsNull = 0;
        } else if (eindTijd.getText().toString().equals("")) {
            Toast.makeText(TrainerAfspraakMaken.this, "Kies een eindtijd", Toast.LENGTH_SHORT).show();
            variableIsNull = 0;}
        if (variableIsNull == 1) {
            LocalTime begintijd = LocalTime.parse(beginTijd.getText().toString());
            LocalTime eindtijd = LocalTime.parse(eindTijd.getText().toString());
            LocalDate datumAfspraak = LocalDate.parse(datum.getText().toString());
            LocalDateTime startAfspraak = LocalDateTime.of(datumAfspraak, begintijd);
            LocalDateTime eindAfspraak = LocalDateTime.of(datumAfspraak, eindtijd);
            Date startAfspraakDate = Date
                    .from(startAfspraak.atZone(ZoneId.systemDefault())
                            .toInstant());
            Date eindAfspraakDate = Date
                    .from(eindAfspraak.atZone(ZoneId.systemDefault())
                            .toInstant());
            Timestamp startTimestamp = new Timestamp(startAfspraakDate);
            Timestamp eindTimestamp = new Timestamp(eindAfspraakDate);
            HashMap<String, Object> data = new HashMap<>();
            data.put("title", afspraak.getText().toString());
            data.put("start", startTimestamp);
            data.put("end", eindTimestamp);
            data.put("locatie", locatie.getText().toString());
            data.put("opmerkingen", opmerkingen.getText().toString());
            db = FirebaseFirestore.getInstance();
            String gezin = getIntent().getStringExtra("pleeggezin");
            db.collection("calenderT").document("calenderT").collection(gezin).document().set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(TrainerAfspraakMaken.this, "Afspraak toegevoegd", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("TAG", "Error adding document", e);
                }
            });
        }
    }


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        LocalTime tijd;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            EditText begintijd = getActivity().findViewById(R.id.begintijdText);
            LocalTime time = LocalTime.of(hourOfDay, minute);

            begintijd.setText(time.toString());
        }
    }


    public static class TimePickerFragmentEind extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
            LocalTime tijd;
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                // Use the current time as the default values for the picker
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // Create a new instance of TimePickerDialog and return it
                return new TimePickerDialog(getActivity(), this, hour, minute,
                        DateFormat.is24HourFormat(getActivity()));
            }

            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                EditText begintijd = getActivity().findViewById(R.id.eindtijdText);
                LocalTime time = LocalTime.of(hourOfDay, minute);
                begintijd.setText(time.toString());
            }
        }


        public void showTimePickerDialog(View v) {
            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(getSupportFragmentManager(), "timePicker");
        }
        public void showTimePickerDialogEind(View v) {
            DialogFragment newFragment = new TimePickerFragmentEind();
            newFragment.show(getSupportFragmentManager(), "timePicker");
        }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        LocalDate datum;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            EditText begintijd = getActivity().findViewById(R.id.datum);
            LocalDate date = LocalDate.of(year, month+1, day);
            begintijd.setText(date.toString());
        }
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}


