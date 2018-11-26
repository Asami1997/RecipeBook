package com.example.asami234.recipebook;
                                               // Structure of each recipe stored in the SqLite database
public class Recipe {

    // declaring variables

    private int recipe_id;
    private String recipe_title;
    private String recipe_content;

    // default constructor when we don't want to pass anything when creating and object of this class
    public Recipe() {

    }


    // constructor for only the title and the content
    public Recipe(String recipe_title, String recipe_content) {
        this.recipe_title = recipe_title;
        this.recipe_content = recipe_content;
    }

    //  main constructor used to set  all the contents
    public Recipe(int recipe_id, String recipe_title, String recipe_content) {
        this.recipe_id = recipe_id;
        this.recipe_title = recipe_title;
        this.recipe_content = recipe_content;
     }


     // set recipe id only
      public void setRecipe_id(int id){
        this.recipe_id = id;
      }


      // set recipe content only
      public void setRecipe_content(String content){
        this.recipe_content = content;
      }


      // set recipe title only
      public void setRecipe_title(String title){
        this.recipe_title = title;
      }

      // retrieve recipe ID
      public int getRecipe_id(){
        return  recipe_id;
      }

      // retrieve recipe content
      public String getRecipe_content(){
        return recipe_content;
      }

      // retrieve recipe title
      public String getRecipe_title(){
        return recipe_title;
      }
}
