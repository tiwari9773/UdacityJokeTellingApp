package in.udacity.joketellingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import in.udacity.jokedisplaylibrary.JokeDisplayActivity;
import in.udacity.joketellingapp.free.EndpointsAsyncTask;
import in.udacity.joketellingapp.free.InterfaceDeliverJoke;


public class MainActivity extends AppCompatActivity {

    InterstitialAd mInterstitialAd;
    String myDeviceId = "a2d4e48351680159";
    Handler handler;
    String strJoke = "default-joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        new EndpointsAsyncTask(onDeliverJoke).execute(new Pair<Context, String>(this, "Manfred"));

        /*Initialization of App Advertisement*/
        MobileAds.initialize(getApplicationContext(), getString(R.string.app_id));
        requestNewInterstitial();
    }

    public void onEntertainMe(View v) {

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(myDeviceId).build();
        mAdView.loadAd(adRequest);

        assert mAdView != null;
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdOpened() {
                Toast.makeText(MainActivity.this, "onAdOpened" + "", Toast.LENGTH_SHORT).show();
                // Save app state before going to the ad overlay.
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                Toast.makeText(MainActivity.this, "onAdLeftApplication" + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                switch (i) {
                    case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                        Toast.makeText(MainActivity.this, i + "ERROR_CODE_INTERNAL_ERROR", Toast.LENGTH_SHORT).show();
                        break;

                    case AdRequest.ERROR_CODE_INVALID_REQUEST:
                        Toast.makeText(MainActivity.this, i + "ERROR_CODE_INVALID_REQUEST", Toast.LENGTH_SHORT).show();
                        break;

                    case AdRequest.ERROR_CODE_NETWORK_ERROR:
                        Toast.makeText(MainActivity.this, i + "ERROR_CODE_NETWORK_ERROR", Toast.LENGTH_SHORT).show();
                        break;

                    case AdRequest.ERROR_CODE_NO_FILL:
                        Toast.makeText(MainActivity.this, i + "ERROR_CODE_NO_FILL", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Toast.makeText(MainActivity.this, i + "Failed", Toast.LENGTH_SHORT).show();
                        break;
                }

                super.onAdFailedToLoad(i);
            }
        });
    }

    public void onDoubleEntertainMe(View v) {
        new EndpointsAsyncTask(onDeliverJoke).execute(new Pair<Context, String>(this, "Manfred"));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {

                Intent in = new Intent(MainActivity.this, JokeDisplayActivity.class);
                in.putExtra("joke", strJoke);
                startActivity(in);
            }
        });

        handler.post(runnable);
    }

    InterfaceDeliverJoke onDeliverJoke = new InterfaceDeliverJoke() {
        @Override
        public void onDeliver(String joke) {
            Toast.makeText(MainActivity.this, "Got Joke"+joke, Toast.LENGTH_SHORT).show();
            strJoke = joke;
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                handler.postDelayed(runnable, 5000);
                Toast.makeText(MainActivity.this, "Delay", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void requestNewInterstitial() {

        if (mInterstitialAd == null) {
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        }

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(myDeviceId)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
}
