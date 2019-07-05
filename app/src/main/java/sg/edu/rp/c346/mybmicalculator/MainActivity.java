package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
EditText etWeight, etHeight;
Button calcBtn, resetBtn;
TextView tvDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        calcBtn = findViewById(R.id.buttonCalc);
        resetBtn = findViewById(R.id.buttonReset);
        tvDisplay = findViewById(R.id.textViewDisplay);

        calcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());
                float bmi = weight/(height*height);
                Calendar calendar = Calendar.getInstance();
                String datetime = calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                        (calendar.get(Calendar.MONTH)+1) + "/" +
                        calendar.get(Calendar.YEAR) + " " +
                        calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                        calendar.get(Calendar.MINUTE);
                String display = "Last Calculated Date: "+datetime+"\n"+"Last Calculated BMI: "+bmi;
                tvDisplay.setText(display);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefsEdit = prefs.edit();
                prefsEdit.putFloat("bmi",bmi);
                prefsEdit.putString("dateTime",datetime);
                prefsEdit.commit();
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etHeight.setText(null);
                etWeight.setText(null);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefsEdit = prefs.edit();
                prefsEdit.clear();
                prefsEdit.commit();
                tvDisplay.setText("Last Calculated Date: \n Last Calculated BMI: ");
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String date_time = prefs.getString("dateTime","");
        float bmi_value = prefs.getFloat("bmi", 0);
        String output = "Last Calculated Date: "+date_time+"\n"+"Last Calculated BMI: "+bmi_value;
        tvDisplay.setText(output);
    }
}
