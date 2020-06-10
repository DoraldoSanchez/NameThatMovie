package com.example.namethatmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> movieURLS = new ArrayList<String>();
    ArrayList<String> movieNames = new ArrayList<String>();
    ImageView imageView;
    int chosenMovie = 0;
    String[] answers = new String[4];
    int locationOfCorrectAnswer = 0;

    Button button0;
    Button button1;
    Button button2;
    Button button3;

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>{


        @Override
        protected Bitmap doInBackground(String... urls) {
            try {

                URL url = new URL (urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {

            String content = "";
            URL url;
            HttpURLConnection connection = null;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    stringBuilder.append(current);
                    data = reader.read();
                }
                content = stringBuilder.toString();
                return content;
            } catch (Exception e) {
                e.printStackTrace();
                return null;

            /*String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try{
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                int data = reader.read();

                while (data !=-1){

                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

            return null;

            }catch (Exception e) {
                e.printStackTrace();
                return null;
            }*/
            }

        }

    }

    public void buttonPress1 (View view0) {

        button0.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_button_bg_2));
        Log.i("Info","Button Pressed");
        if (view0.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();

    } else {

            Toast.makeText(getApplicationContext(), "Incorrect- the answer is  " + movieNames.get(chosenMovie), Toast.LENGTH_SHORT).show();
        }

        newQuestion();

    }

    public void buttonPress2 (View view1) {

        button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_button_bg_2));
       Log.i("Info","Button 2 Pressed");

        if (view1.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(getApplicationContext(), "Incorrect- the answer is  " + movieNames.get(chosenMovie), Toast.LENGTH_SHORT).show();
        }

        newQuestion();
    }

    public void buttonPress3 (View view2) {

        button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_button_bg_2));
       Log.i("Info","Button 3 Pressed");

        if (view2.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(getApplicationContext(), "Incorrect- the answer is  " + movieNames.get(chosenMovie), Toast.LENGTH_SHORT).show();
        }

        newQuestion();
    }

  public void buttonPress4 (View view3) {

        button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_button_bg_2));
      Log.i("Info","Button 4 Pressed");

      if (view3.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
          Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();

      } else {

          Toast.makeText(getApplicationContext(), "Incorrect- the answer is  " + movieNames.get(chosenMovie), Toast.LENGTH_SHORT).show();
      }

      newQuestion();
    }


    public void newQuestion() {

        try {

            Random rand = new Random();
            chosenMovie = rand.nextInt(movieURLS.size());
            ImageDownloader imageTask = new ImageDownloader();

            Bitmap movieImage = imageTask.execute(movieURLS.get(chosenMovie)).get();
            imageView.setImageBitmap(movieImage);
            locationOfCorrectAnswer = rand.nextInt(4);
            int incorrectAnswerLocation;

            for (int i = 0; i < 4; i++) {
                if (i == locationOfCorrectAnswer) {
                    answers[i] = movieNames.get(chosenMovie);

                } else {

                    incorrectAnswerLocation = rand.nextInt(movieURLS.size());

                    while (incorrectAnswerLocation == chosenMovie) {
                        incorrectAnswerLocation = rand.nextInt(movieURLS.size());


                    }

                    answers[i] = movieNames.get(incorrectAnswerLocation);
                }

            }

            button0.setText("A) '" + answers[0]);
            button1.setText("B) '" + answers[1]);
            button2.setText("C) '" + answers[2]);
            button3.setText("D) '" + answers[3]);
            button0.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_button_bg));
            button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_button_bg));
            button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_button_bg));
            button3.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_button_bg));

        } catch (Exception e) {
            e.printStackTrace();

        }

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        DownloadTask task = new DownloadTask();
        String result = null;

        try {
            result = task.execute("https://www.nytimes.com/interactive/2020/arts/television/best-movies-on-netflix.html").get();

            //System.out.println(result);


            String[] splitResult = result.split("<!-- special includes for video features, opinion section -->");


            Pattern p = Pattern.compile("data-src=\"(.*?)\">");

            Matcher m = p.matcher(splitResult[0]);

            while (m.find()) {
                //System.out.println(m.group(1));
                movieURLS.add(m.group(1));

                //}catch (Exception e) {
                //e.printStackTrace();
                }

                p = Pattern.compile("\">‘(.*?)</span>");

                m = p.matcher(splitResult[0]);


                while (m.find()) {

                    //System.out.println(m.group(1));

                    movieNames.add(m.group(1));
                }

               newQuestion();

                } catch(Exception e){
                    e.printStackTrace();
                }

            }


        }

