package platerz.hackathon;
import android.media.Image;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import java.util.*;
import java.io.*;

/**
 * Created by os on 2018-02-16.
 */

public class Recipe {
    static final int Short = 40;
    static final int Medium = 90;
    String meat[] = {"beef", "chicken", "pork", "ham", "bacon", "sheppard", "sausage"};
    Image image;
    String name;
    ArrayList<String> tags = new ArrayList<>();
    String instructions = "";
    String ingredients = "";
    boolean vegan = true;

    public static void main(String[] args) {
        Recipe recipe = new Recipe();
        //TEST
        recipe.readFile("http://allrecipes.com/recipe/235432/creamy-herbed-pork-chops/?internalSource=rotd&referringId=1947&referringContentType=recipe%20hub");
        Recipe r = new Recipe();
        r.readFile("http://allrecipes.com/recipe/241106/breakfast-casserole-in-a-slow-cooker/?internalSource=streams&referringId=253&referringContentType=recipe%20hub&clickId=st_recipes_mades");
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

}
