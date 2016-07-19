package in.udacity.joketellingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import in.udacity.jokedisplaylibrary.JokeDisplayActivity;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onEntertainMe(View v) {
        dialog = ProgressDialog.show(this, getString(R.string.mg_while_fetching), "Few Seconds More", true);
        new EndpointsAsyncTask(onDeliverJoke).execute();
    }

    InterfaceDeliverJoke onDeliverJoke = new InterfaceDeliverJoke() {
        @Override
        public void onDeliver(String joke) {
            if (dialog != null) {
                dialog.dismiss();
            }

            Intent in = new Intent(MainActivity.this, JokeDisplayActivity.class);
            in.putExtra("joke", joke);
            startActivity(in);
        }
    };

}
