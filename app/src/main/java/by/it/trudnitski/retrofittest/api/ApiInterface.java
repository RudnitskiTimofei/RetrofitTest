package by.it.trudnitski.retrofittest.api;

import by.it.trudnitski.retrofittest.model.News;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/v2/everything")
    Call<News> getNews(
            @Query("q") String source,
            @Query("from") String from,
            @Query("sortBy") String sort,
            @Query("apiKey") String apiKey );

}
