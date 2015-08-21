package mx.comunidadcodigo.comunidadcodigo.api;


import mx.comunidadcodigo.comunidadcodigo.api.models.CategoriesModel;
import mx.comunidadcodigo.comunidadcodigo.api.models.PostsModel;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by kentverger on 16/07/15.
 */
public interface Wordpress {
    @GET("/categories")
    CategoriesModel listCategories();

    @GET("/posts")
    PostsModel listPosts(@Query("category") String catId);


    @GET("/posts")
    PostsModel listPosts();
}
