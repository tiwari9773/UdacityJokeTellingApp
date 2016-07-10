package in.udacity.joketellingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onEntertainMe(View v) {
        Toast.makeText(MainActivity.this, "Paid Version", Toast.LENGTH_SHORT).show();
        Intent in = new Intent(this,JokeTellingActivity.class);
        startActivity(in);
    }

}
