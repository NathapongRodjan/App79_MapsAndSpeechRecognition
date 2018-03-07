package com.example.nathapong.app79_mapsandspeechrecognition;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nathapong.app79_mapsandspeechrecognition.Model.CountryDataSource;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int SPEAK_REQUEST = 10;

    TextView txtValue;
    Button btnVoiceIntent;
    public static CountryDataSource countryDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PackageManager packageManager = this.getPackageManager();
        List<ResolveInfo> listOfInformation = packageManager.queryIntentActivities
                (new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);

        txtValue = (TextView)findViewById(R.id.txtValue);
        btnVoiceIntent = (Button)findViewById(R.id.btnVoiceIntent);

        btnVoiceIntent.setOnClickListener(MainActivity.this);


        Hashtable<String, String> countryAndMessages = new Hashtable<>();

        countryAndMessages.put("Canada",        "Welcome to Canada. Happy Visiting.");
        countryAndMessages.put("France",        "Welcome to France. Happy Visiting.");
        countryAndMessages.put("Brazil",        "Welcome to Brazil. Happy Visiting.");
        countryAndMessages.put("United States", "Welcome to United States. Happy Visiting.");
        countryAndMessages.put("Japan",         "Welcome to Japan. Happy Visiting.");
        countryAndMessages.put("China",         "Welcome to China. Happy Visiting.");

        countryDataSource = new CountryDataSource(countryAndMessages);

        if (listOfInformation.size() > 0){
            Toast.makeText(MainActivity.this,
                    "Your device does support speech recognition",Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(MainActivity.this,
                    "Your device does not support speech recognition",Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onClick(View v) {

        listenToTheUserVoice();
    }



    private void listenToTheUserVoice(){

        Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        voiceIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Talk to Me!");

        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        voiceIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);

        startActivityForResult(voiceIntent, SPEAK_REQUEST);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SPEAK_REQUEST && resultCode == RESULT_OK){

            ArrayList<String> voiceWords = data.getStringArrayListExtra
                    (RecognizerIntent.EXTRA_RESULTS);

            float[] cofidLevels = data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);

            /*int index = 0;
            for (String userWord : voiceWords){
                if (cofidLevels != null && index < cofidLevels.length){
                    txtValue.setText(userWord + " - " + cofidLevels[index]);
                }
            }*/


            String countryMatchedWIthUserWord = countryDataSource.matchWithMinimumConfidenceLevelOfUserWords
                    (voiceWords, cofidLevels);

            Intent myMapActivity = new Intent(MainActivity.this, MapsActivity.class);

            myMapActivity.putExtra(CountryDataSource.COUNTRY_KEY, countryMatchedWIthUserWord);

            startActivity(myMapActivity);

        }
    }
}
