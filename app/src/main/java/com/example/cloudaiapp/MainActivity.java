package com.example.cloudaiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.paralleldots.paralleldots.App;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MainActivity extends AppCompatActivity {

    EditText inputField;
    Button button;
    TextView resultField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.input_string);
        button = findViewById(R.id.button);
        resultField = findViewById(R.id.result_string);
    }

    public String analyze(String input) throws Exception{
        JSONParser parser = new JSONParser();
        App parallelDots = new App("----");

        String sentiment = parallelDots.sentiment(input);
        return sentiment;
    }

    public void click(View view){
        String input = inputField.getText().toString();
        try {
            String result = analyze(input);
            resultField.setText(readableResult(result));
        }
        catch (Exception e){
            e.printStackTrace();
            resultField.setText("Error");
        }
    }

    public String readableResult(String input) throws Exception {
        String result = "";
        JSONParser parser = new JSONParser();

        JSONObject o1 = (JSONObject) new JSONParser().parse(input);
        JSONObject jsonObj = (JSONObject) new JSONParser().parse(o1.get("sentiment").toString());

        result += "negative: " + jsonObj.get("negative") + '\n';
        result += "neutral: " + jsonObj.get("neutral") + '\n';
        result += "positive: " + jsonObj.get("positive");


        return result;
    }
}