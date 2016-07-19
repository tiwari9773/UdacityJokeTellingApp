package in.udacity.jokedisplaylibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        if (getIntent().getStringExtra("joke") != null) {
            TextView textView = (TextView) findViewById(R.id.tv_joke_display);
            textView.setText(getIntent().getStringExtra("joke"));
        }
    }
}
