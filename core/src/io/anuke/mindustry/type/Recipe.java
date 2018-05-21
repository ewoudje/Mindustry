package io.anuke.mindustry.type;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import io.anuke.mindustry.world.Block;

public class Recipe {
    private static int lastid;
    private static Array<Recipe> allRecipes = new Array<>();
    private static ObjectMap<Block, Recipe> recipeMap = new ObjectMap<>();

    public final int id;
    public final Block result;
    public final io.anuke.mindustry.type.ItemStack[] requirements;
    public final io.anuke.mindustry.type.Section section;
    public final float cost;

    public boolean desktopOnly = false, debugOnly = false;

    public Recipe(io.anuke.mindustry.type.Section section, Block result, io.anuke.mindustry.type.ItemStack... requirements){
        this.id = lastid ++;
        this.result = result;
        this.requirements = requirements;
        this.section = section;

        float timeToPlace = 0f;
        for(io.anuke.mindustry.type.ItemStack stack : requirements){
            timeToPlace += stack.amount * stack.item.cost;
        }

        this.cost = timeToPlace;

        allRecipes.add(this);
        recipeMap.put(result, this);
    }

    public Recipe setDesktop(){
        desktopOnly = true;
        return this;
    }

    public Recipe setDebug(){
        debugOnly = true;
        return this;
    }

    public static Array<Recipe> getBySection(io.anuke.mindustry.type.Section section, Array<Recipe> r){
        for(Recipe recipe : allRecipes){
            if(recipe.section == section ) {
                r.add(recipe);
            }
        }

        return r;
    }

    public static Array<Recipe> getAllRecipes(){
        return allRecipes;
    }

    public static Recipe getByResult(Block block){
        return recipeMap.get(block);
    }

    public static Recipe getByID(int id){
        if(id < 0 || id >= allRecipes.size){
            return null;
        }else{
            return allRecipes.get(id);
        }
    }
}