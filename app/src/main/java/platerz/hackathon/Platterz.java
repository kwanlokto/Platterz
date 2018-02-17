package platerz.hackathon;

import android.gesture.GestureOverlayView;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.support.v7.widget.*;
import android.view.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.ImageButton;
import java.io.InputStream;
import java.net.URL;
import android.os.*;
import android.util.Log;


import java.util.ArrayList;

public class Platterz extends AppCompatActivity implements View.OnClickListener{

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    public static ArrayList<Recipe> userBook;
    public static User user;
    public static RecipeBook cookBook; // NEED TO ADD ALL RECIPES
    public static int count = 0;
    public static boolean navigating = true;

    private Toolbar myToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening_page);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //Add all actionlisteners to buttons
        Button login = findViewById(R.id.Login);
        login.setOnClickListener(this);

        Button sUp = findViewById(R.id.signUp);
        sUp.setOnClickListener(this);


        cookBook = new RecipeBook();
        //Toolbar stuff

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
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

            //NEED TO STORE USER NAMES
            if (found) {
                userBook = cookBook.getUserBook(user);
                setContentView(R.layout.swipe_recipes);

            }

        }
        //If we are signing up
        else if (view.getId() == R.id.signUp) {
            String name = ((TextView)findViewById(R.id.UserName)).getText().toString();
            String password = ((TextView)findViewById(R.id.Password)).getText().toString();
            user = new User(name, password);
            userBook = cookBook.getUserBook(user);
            System.out.println("Users: "+user);
            setContentView(R.layout.register_exp);
            Button beginner = findViewById(R.id.Beginner);
            beginner.setOnClickListener(this);

            Button average = findViewById(R.id.Average);
            average.setOnClickListener(this);

            Button gourmet = findViewById(R.id.Gourmet);
            gourmet.setOnClickListener(this);
        }
        else if (view.getId() == R.id.Beginner) {
            user.setExp("Beginner");
            createSwipe();
        }
        else if (view.getId() == R.id.Average) {
            user.setExp("Average");
            createSwipe();
        }
        else if (view.getId() == R.id.Gourmet) {
            user.setExp("Gourmet");
            createSwipe();
        }
        else if (view.getId() == R.id.dislike) {
            count++;
            if (count == userBook.size()) count = 0;
            updateRecipe(R.id.recipeImage);
            ((TextView)findViewById(R.id.foodName)).setText(userBook.get(count).getName());
            //user.addRecipe();
        }

        else if(view.getId() == R.id.search){
            setContentView(R.layout.recipe_info);
            navigating = false;
            updateRecipe(R.id.recipePic);
            ((TextView)findViewById(R.id.recipeName)).setText(userBook.get(count).getName());
            ((TextView)findViewById(R.id.Ingredients)).setText("Ingredients\n" + userBook.get(count).getIngredients());
            ((TextView)findViewById(R.id.Instructions)).setText("Instructions\n" + userBook.get(count).getInstructions());
            ((TextView)findViewById(R.id.time)).setText("Time: "+ Integer.toString(userBook.get(count).getTime())+"min");
        }
    }

    private void createSwipe(){
        setContentView(R.layout.swipe_recipes);
        updateRecipe(R.id.recipeImage);
        findViewById(R.id.recipeImage).setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                //Toast.makeText(MyActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                user.addRecipe(userBook.get(count));
                count++;
                if (count >= userBook.size()) count = 0;
                updateRecipe(R.id.recipeImage);
                ((TextView)findViewById(R.id.foodName)).setText(userBook.get(count).getName());
            }
            public void onSwipeRight() {
                user.addRecipe(userBook.get(count));
                count--;
                if (count <= 0) count = userBook.size()-1;
                updateRecipe(R.id.recipeImage);
                ((TextView)findViewById(R.id.foodName)).setText(userBook.get(count).getName());
            }
            public void onSwipeBottom() {
                //Toast.makeText(MyActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
        ((TextView)findViewById(R.id.foodName)).setText(userBook.get(count).getName());
        ImageButton dislike = findViewById(R.id.dislike);
        dislike.setOnClickListener(this);
        ImageButton search = findViewById(R.id.search);
        search.setOnClickListener(this);

    }

    public void updateRecipe(int i) {
        URL url = userBook.get(count).getImage();
        new DownloadImageTask((ImageView) findViewById(i))
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

    @Override
    public void onBackPressed() {
        if (!navigating) {
            setContentView(R.layout.swipe_recipes);
            updateRecipe(R.id.recipeImage);
            ((TextView)findViewById(R.id.foodName)).setText(userBook.get(count).getName());
            navigating = true;
        }
    }


}
