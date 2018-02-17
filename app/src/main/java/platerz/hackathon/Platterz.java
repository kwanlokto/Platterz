package platerz.hackathon;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.support.v7.widget.*;
import android.view.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;

public class Platterz extends AppCompatActivity implements View.OnClickListener{

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    public static ArrayList<Recipe> userBook;
    public static User user;
    public static RecipeBook cookBook = new RecipeBook(); // NEED TO ADD ALL RECIPES
    public static int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening_page);

        //Add all actionlisteners to buttons
        final Button login = findViewById(R.id.Login);
        login.setOnClickListener(this);
        final Button signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(this);
        final Button beginner = findViewById(R.id.Beginner);
        beginner.setOnClickListener(this);
        final Button average = findViewById(R.id.Average);
        average.setOnClickListener(this);
        final Button gourmet = findViewById(R.id.Gourmet);
        gourmet.setOnClickListener(this);

        //Toolbar stuff

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(myToolbar);


        // Example of a call to a native method
        //TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    public void onClick(View view) {
        //If the login Button is clicked
        if (view.getId() == R.id.Login) {
            boolean found = false;
            userBook = cookBook.getUserBook(user);
            //NEED TO STORE USER NAMES
            if (found) {
                setContentView(R.layout.swipe_recipes);
            }
        }
        //If we are signing up
        else if (view.getId() == R.id.signUp) {
            String name = ((TextView)findViewById(R.id.UserName)).getText().toString();
            String password = ((TextView)findViewById(R.id.Password)).getText().toString();
            user = new User(name, password);
            userBook = cookBook.getUserBook(user);
            setContentView(R.layout.register_exp);
        }
        else if (view.getId() == R.id.Beginner) {
            user.setExp("Beginner");
        }
        else if (view.getId() == R.id.Average) {
            user.setExp("Average");
        }
        else if (view.getId() == R.id.Gourmet) {
            user.setExp("Gourmet");
        }
        else if (view.getId() == R.id.Add) {
            //user.addRecipe();
        }
    }

    public void updateRecipe() {
        URL url = userBook.get(count).getImage();
        new DownloadImageTask((ImageView) findViewById(R.id.recipeImage))
                .execute(url.toString());
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
