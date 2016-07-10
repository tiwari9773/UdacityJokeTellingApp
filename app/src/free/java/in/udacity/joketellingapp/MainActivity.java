package in.udacity.joketellingapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    InterstitialAd mInterstitialAd;
    String myDeviceId = "a2d4e48351680159";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        /*Initialization of App Advertisement*/
        MobileAds.initialize(getApplicationContext(), getString(R.string.app_id));
    }

    public void onEntertainMe(View v) {
        Toast.makeText(MainActivity.this, "Free Version", Toast.LENGTH_SHORT).show();
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
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Toast.makeText(MainActivity.this, "Continue Joke Version", Toast.LENGTH_SHORT).show();
                requestNewInterstitial();
            }
        });

        requestNewInterstitial();

        handler.postDelayed(runnable, 5000);

    }

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
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(myDeviceId)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
}
