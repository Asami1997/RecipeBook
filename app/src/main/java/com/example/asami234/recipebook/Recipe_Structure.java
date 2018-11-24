package com.example.asami234.recipebook;

public class Recipe_Structure {

    private int recipe_id;
    private String recipe_title;
    private String recipe_content;


    //  main constructor used to set  all the contents
    public Recipe_Structure(int recipe_id, String recipe_title, String recipe_content) {
        this.recipe_id = recipe_id;
        this.recipe_title = recipe_title;
        this.recipe_content = recipe_content;
    }


     // set recipe id only

      private void setRecipe_id(int id){
        this.recipe_id = id;
      }


      // set recipe content only
      private void setRecipe_content(String content){
        this.recipe_content = content;
      }


      // set recipe title only
      private void setRecipe_title(String title){
        this.recipe_title = title;
      }

      // retrieve recipe ID
      private int getRecipe_id(){
        return  recipe_id;
      }

      // retrieve recipe content
      private String getRecipe_content(){
        return recipe_content;
      }

      // retrieve recipe title
      private String getRecipe_title(){
        return recipe_title;
      }
}
