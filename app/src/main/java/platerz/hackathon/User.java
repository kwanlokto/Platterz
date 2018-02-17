package platerz.hackathon;
import java.util.*;
/**
 * Created by os on 2018-02-16.
 */

public class User {
    String name;
    String experience;
    String demographics;
    ArrayList<String> restrictions = new ArrayList<>();
    ArrayList<Recipe> recipes = new ArrayList<>();

    public void addName(String n) {
        this.name = name;
    }
    public void addExperience(String e) {

    }
    public void addDemographics(String d) {

    }
    public void addRestrictions(ArrayList<String> restrict){
        for(int i = 0; i < restrict.size(); i++) {
            restrictions.add(restrict.get(i));
        }
    }

    public void addRecipes(ArrayList<Recipe> recipe) {
        for(int i = 0; i < recipe.size(); i++) {
            recipes.add(recipe.get(i));
        }
    }
}

