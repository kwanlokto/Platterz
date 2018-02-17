package platerz.hackathon;
import android.media.Image;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.*;
import java.io.*;

/**
 * Created by os on 2018-02-16.
 */

public class Recipe {
    static final int Short = 40;
    static final int Medium = 90;
    static final String meat[] = {"beef", "chicken", "pork", "ham", "bacon", "sheppard", "sausage"};
    private int time;
    private URL image;
    private String name;
    private ArrayList<String> tags = new ArrayList<>();
    private String instructions = "";
    private String ingredients = "";
    private boolean vegan = true;

    public static void main(String[] args) {
        Recipe recipe1 = new Recipe("http://allrecipes.com/recipe/235432/creamy-herbed-pork-chops/?internalSource=rotd&referringId=1947&referringContentType=recipe%20hub");
        Recipe recipe2 = new Recipe("http://allrecipes.com/recipe/241106/breakfast-casserole-in-a-slow-cooker/?internalSource=streams&referringId=253&referringContentType=recipe%20hub&clickId=st_recipes_mades");
    }

    public Recipe(String url){
        readFile(url);
    }

    public void readFile(String url){
        try {
            Document doc = Jsoup.connect(url).get();
            name = doc.title();
            System.out.println(name);
            Elements ingredientsE = doc.select("span.recipe-ingred_txt"); //Get the ingredients
            Elements instructionsE = doc.select("span.recipe-directions__list--item"); //Get the instructions
            Element img = doc.select("img[src~=.jpg]").first(); //get the picture
            String imgUrl = img.absUrl("src");
            image = new URL(imgUrl);
            System.out.println(imgUrl);
            for (Element e: ingredientsE) {
                if (!e.text().equals("Add all ingredients to list")) {
                    ingredients += e.text() + "\n";
                }
            }
            System.out.println(ingredients);
            tags.add("Vegan");
            for (String m : meat) {
                if (ingredients.contains(m)) {
                    tags.remove("Vegan"); //No longer vegan
                }
            }
            for (Element e: instructionsE) {
                instructions += e.text() + "\n";
            }
            System.out.println(instructions);
            determineTime(doc);
        }
        catch(IOException e) {
            System.out.print("File not found");
            e.printStackTrace();
        }
    }

    /**
     * Determines the time required to make the meal
     * @param document
     */
    public void determineTime(Document document) {
        Element readyTime = document.select("time").last();
        String timeInfo [] = readyTime.text().split(" ");
        System.out.println(readyTime.text());
        int tempTime = 0;
        for (int i = 0; i < timeInfo.length; i++) {
            if (timeInfo[i].matches("[\\d]+")) {
                if (i + 1 < timeInfo.length && timeInfo[i + 1].equals("h")) {
                    tempTime += 60 * Integer.parseInt(timeInfo[i]);
                }
                else if (i + 1 < timeInfo.length && timeInfo[i + 1].equals("m")) {
                    tempTime += Integer.parseInt(timeInfo[i]);
                }
            }
        }
        time = tempTime;
        System.out.println(tempTime);
        if (tempTime < Short) {
            tags.add("Quick");
        }
        else if (tempTime < Medium) {
            tags.add("Medium");
        }
        else {
            tags.add("Long");
        }
    }

    /**
     * Determine the healthiness of the meal based on the nutritional value
     * @param document
     */
    public void getHealth(Document document){
        Element calorieS = document.select("span.calories").last(); //gets the calorie count
        Element fatS = document.select("span.fatContent").last();
        Element carbsS = document.select("span.carbohydrateContent").last();
        Element proteinS = document.select("span.proteinContent").last();

        int calorie = Integer.parseInt(calorieS.text());
        int fat = Integer.parseInt(fatS.text());
        int carbs = Integer.parseInt(carbsS.text());
        int protein = Integer.parseInt(proteinS.text());

        int count = 0;
        if (calorie < 1000 && calorie > 500) {
            count++;
        }
        if (fat < 12 && fat > 7) {
            count++;
        }
        if (carbs < 50 && carbs > 30) {
            count++;
        }
        if (protein < 20 && protein > 10) {
            count++;
        }
        if (count >= 3) {
            tags.add("Healthy");
        }
        else if (count >= 1) {
            tags.add("Moderately Healthy");
        }
        else {
            tags.add("Unhealthy");
        }
    }

    public String getInstructions(){
        return instructions;
    }

    public String getIngredients(){
        return ingredients;
    }

    public ArrayList<String> getTags() {
        return (ArrayList<String>)tags.clone();
    }

    public URL getImage(){
        return image;
    }

    public String getName(){
        return name;
    }

    public int getTime() {
        return time;
    }

    public boolean equals(Recipe r2) {
        return this.getInstructions().equals(r2.getInstructions()) && this.getIngredients().equals(r2.getIngredients());
    }
}
