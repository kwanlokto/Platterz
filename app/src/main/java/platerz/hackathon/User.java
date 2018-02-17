package platerz.hackathon;
import java.util.*;
/**
 * Created by os on 2018-02-16.
 */

public class User {
    String name;
    String passWord;
    String experience;
    String demographics;
    ArrayList<String> restrictions = new ArrayList<>();
    ArrayList<Recipe> recipes = new ArrayList<>();

    /**
     * When creating a new user object we must always include the name
     * @param name
     */
    public User(String name, String passWord){
        this.name = name;
        this.passWord = passWord;
    }


    public void setExp(String e) {
        experience = e;
    }
    public void addDemographics(String d) {
        demographics = d;
    }
    public void addRestrictions(ArrayList<String> restrict){
        for(int i = 0; i < restrict.size(); i++) {
            restrictions.add(restrict.get(i));
        }
    }

    /**
     * Determines whether or not the recipe should be displayed to this user
     * @param recipe the recipe we are checking
     * @return Returns whether or not that the recipe does not contain any restrictions
     */
    public boolean displayRecipe(Recipe recipe) {
        for (String tag: recipe.getTags()) {
            if (restrictions.contains(tag)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Add the recipe to the recipes available
     * @param recipe
     */
    public void addRecipe(Recipe recipe) {
        if (displayRecipe(recipe)) {
            recipes.add(recipe);
        }
    }
}

