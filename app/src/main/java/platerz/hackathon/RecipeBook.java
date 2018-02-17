package platerz.hackathon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Branden on 2018-02-16.
 */

public class RecipeBook {

    HashMap<String, HashSet<Recipe>> recipeBook = new HashMap<>();

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
