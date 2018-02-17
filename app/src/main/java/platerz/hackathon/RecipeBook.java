package platerz.hackathon;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Branden on 2018-02-16.
 */

public class RecipeBook {
    public static void main(String[] args) {
        RecipeBook rb = new RecipeBook();
    }

    public static HashMap<String, ArrayList<Recipe>> recipeBook = new HashMap<>();
    private ArrayList<Recipe> cookBook = null;

    public RecipeBook() {
        Recipe spicyChicken = new Recipe("http://allrecipes.com/recipe/257938/spicy-thai-basil-chicken-pad-krapow-gai/");
        Recipe casserole = new Recipe("http://allrecipes.com/recipe/241106/breakfast-casserole-in-a-slow-cooker/");
        Recipe porkChop = new Recipe("http://allrecipes.com/recipe/235432/creamy-herbed-pork-chops/");
        Recipe porkChopAmazing = new Recipe("http://allrecipes.com/recipe/240542/amazing-fried-pork-chops/");
        Recipe flatBread = new Recipe("http://allrecipes.com/recipe/262715/farinata-italian-flatbread/");

        add(spicyChicken);
        add(casserole);
        add(porkChop);
        add(porkChopAmazing);
        add(flatBread);

    }

    public ArrayList<Recipe> getUserBook(User user) {
        ArrayList<Recipe> cookBook = new ArrayList<>();
        if (user.getRestrictions().size() != 0) {
            cookBook = search(user.getRestrictions());
        }
        else {
            for(ArrayList<Recipe> recipes : recipeBook.values()) {
                for (Recipe recipe: recipes) {
                    if (!cookBook.contains(recipe)) {
                        cookBook.add(recipe);
                    }
                }
            }
        }
        return cookBook;
    }

    public ArrayList<Recipe> search(ArrayList<String> tags) {
        HashSet<Recipe> matches;
        if (tags.isEmpty()) {
            matches = new HashSet<>();
        } else {
            int i = 0;
            matches = new HashSet<>(recipeBook.get(tags.get(i)));
            i++;
            while (i < tags.size()) {
                (recipeBook.get(tags.get(i))).retainAll(matches);
                if (matches.isEmpty()) break;
                i++;
            }
        }
        ArrayList<Recipe> list = new ArrayList<Recipe>(matches);
        return list;
    }

    public void add(Recipe recipe) {
        ArrayList<String> tags = recipe.getTags();
        for (String tag : tags) {
            //If the the key already exists
            if (!recipeBook.containsKey(tag)) {
                ArrayList<Recipe> hs = new ArrayList<>();
                hs.add(recipe);
                recipeBook.put(tag, hs);
            }
            else{
                ArrayList<Recipe> hs = recipeBook.get(tag);
                if (!hs.contains(recipe)) {
                    hs.add(recipe);
                }
            }
        }
        System.out.println("size: "+recipeBook.size());
    }


}
