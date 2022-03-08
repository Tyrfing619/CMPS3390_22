package a5.scarlos.weatherapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements UIBind{
    private Spinner spinnerLocations;
    private TextView txtLatitude, txtLongitude, txtHigh, txtLow, txtCurrent, txtFeelsLIke,
        txtPressure, txtHumidity, txtWeatherDescription, txtWindSpeed;
    private APIBridge apiBridge;
    NetworkImageView imgWeatherIcon;
    ImageView imgWindDir;
    RequestQueue requestQueue;
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.apiBridge = new APIBridge(this, getApplicationContext());
        this.txtLatitude = findViewById(R.id.txtLatitude);
        this.txtLongitude = findViewById(R.id.txtLongitude);
        this.txtHigh = findViewById(R.id.txtHigh);
        this.txtLow = findViewById(R.id.txtLow);
        this.txtCurrent = findViewById(R.id.txtCurrent);
        this.txtFeelsLIke = findViewById(R.id.txtFeelsLike);
        this.txtPressure = findViewById(R.id.txtPressure);
        this.txtHumidity = findViewById(R.id.txtHumidity);
        this.imgWeatherIcon = findViewById(R.id.imgWeatherIcon);
        this.txtWeatherDescription = findViewById(R.id.txtWeatherDescription);
        this.txtWindSpeed = findViewById(R.id.txtWindSpeed);
        this.imgWindDir = findViewById(R.id.imgWindDir);


        this.requestQueue = Volley.newRequestQueue(this);
        this.imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<>(10);

            @Nullable
            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
        });


        this.spinnerLocations = findViewById(R.id.spinLocations);

        spinnerLocations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("LOCATION", spinnerLocations.getSelectedItem().toString());
                apiBridge.GenerateWeatherModel(spinnerLocations.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void mapUI(WeatherModel weatherModel) {
        txtLatitude.setText(String.valueOf(weatherModel.getLat()));
        txtLongitude.setText(String.valueOf(weatherModel.getLon()));
        txtHigh.setText(String.format("%sF", weatherModel.getTempMax()));
        txtLow.setText(String.format("%sF", weatherModel.getTempMin()));
        txtCurrent.setText(String.format("%sF", weatherModel.getTemp()));
        txtFeelsLIke.setText(String.format("%sF", weatherModel.getFeelsLike()));
        txtPressure.setText(String.format("%shPA", weatherModel.getPressure()));
        txtHumidity.setText(String.format("%d%%", weatherModel.getHumidity()));
        imgWeatherIcon.setImageUrl(weatherModel.getWeatherIcon(), this.imageLoader);
        txtWeatherDescription.setText(weatherModel.getWeatherDescription());
        txtWindSpeed.setText(String.format("%s MPH", weatherModel.getWindSpeed()));
        imgWindDir.setRotation(weatherModel.getWindDirection());
    }
    //Time Stamp: 1:18:35
}