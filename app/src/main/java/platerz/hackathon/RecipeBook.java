package platerz.hackathon;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Branden on 2018-02-16.
 */

public class RecipeBook {

    HashMap<String, HashSet<Recipe>> recipeBook = new HashMap<>();

    public RecipeBook() {
        Recipe spicyChicken = new Recipe("http://allrecipes.com/recipe/257938/spicy-thai-basil-chicken-pad-krapow-gai/");
        Recipe casserole = new Recipe("http://allrecipes.com/recipe/241106/breakfast-casserole-in-a-slow-cooker/");
        Recipe porkChop = new Recipe("http://allrecipes.com/recipe/235432/creamy-herbed-pork-chops/");
        Recipe porkChopAmazing = new Recipe("http://allrecipes.com/recipe/240542/amazing-fried-pork-chops/");
        Recipe flatBread = new Recipe("http://allrecipes.com/recipe/262715/farinata-italian-flatbread/");


    }

    HashSet<Recipe> search(ArrayList<String> tags) {
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
        return matches;
    }
}
