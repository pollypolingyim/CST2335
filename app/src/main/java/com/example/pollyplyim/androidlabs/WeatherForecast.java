package com.example.pollyplyim.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by pollyplyim on 2018-03-19.
 */

public class WeatherForecast extends Activity {
    private ProgressBar progressBar;
    private TextView currentTemp;
    private TextView minimumTemp;
    private TextView maximumTemp;
    private TextView windSpeed;
    private ImageView weatherImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_forecast);
        ProgressBar bar = (ProgressBar)findViewById(R.id.progressBar2);
        bar.setVisibility(View.VISIBLE);

        currentTemp = (TextView) findViewById(R.id.current_temp_text);
        minimumTemp = (TextView) findViewById(R.id.min_temp_text);
        maximumTemp = (TextView) findViewById(R.id.max_temp_text);
        windSpeed =(TextView)findViewById(R.id.wind_speed_text);
        weatherImage = (ImageView) findViewById(R.id.weather_pic);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";

        new ForecastQuery().execute(urlString);
    }

    class ForecastQuery extends AsyncTask<String, Integer, String> {
        String windSp;
        String minTemp;
        String maxTemp;
        String currTemp;
        Bitmap weatherPic;

        protected void onProgressUpdate(Integer... value) {
            Log.i("Weather Forecast", "In onProgressUpdate");
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        protected void onPostExecute(String result) {
            String degree = Character.toString((char) 0x00B0);
            currentTemp.setText("current temp: "+currTemp+degree+"C");
            minimumTemp.setText("minimum temp: "+minTemp+degree+"C");
            maximumTemp.setText("maximum temp: "+maxTemp+degree+"C");
            windSpeed.setText("wind speed: " + windSp);
            weatherImage.setImageBitmap(weatherPic);
            progressBar.setVisibility(View.INVISIBLE);
        }

        protected String doInBackground(String... args) {
            try {
                URL url = new URL(args[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                parse(conn.getInputStream());
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        private void parse(InputStream in) throws XmlPullParserException, IOException {
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);
                parser.nextTag();
                readFeed(parser);
            } finally {
                in.close();
            }
        }

        private void readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
            int eventType;
            while ((eventType = parser.getEventType()) != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (parser.getName().equalsIgnoreCase("temperature")) {
                        currTemp = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        minTemp = parser.getAttributeValue(null, "min");
                        publishProgress(50);
                        maxTemp = parser.getAttributeValue(null, "max");
                        publishProgress(75);
                    }
                    if (parser.getName().equals("speed")) {
                        windSp = parser.getAttributeValue(null, "value");
                    }
                    if (parser.getName().equals("weather")) {
                        String iconName = parser.getAttributeValue(null, "icon");
                        String iconFile = iconName + ".png";
                        if (fileExistance(iconFile)) {
                            FileInputStream fis = null;
                            try {
                                fis = openFileInput(iconFile);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            weatherPic = BitmapFactory.decodeStream(fis);
                            Log.i("Weather Forecast", "found the image locally: " + iconFile);
                        } else {
                            String imageURL = "http://openweathermap.org/img/w/" + iconName + ".png";
                            Bitmap weatherPic = getImage(imageURL);
                            FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);

                            weatherPic.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();
                            Log.i("Weather Forecast", "need to download the image: " + iconName);
                        }
                        publishProgress(100);
                    }
                }
                else if (eventType == XmlPullParser.END_TAG) {

                }
                parser.next();
            }

        }

        private boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        private  Bitmap getImage(String urlString) {
            try {
                return getImage(new URL(urlString));
            } catch (MalformedURLException e) {
                return null;
            }
        }

        private Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

    }
}
