package animation.meni.context.retrofit2tut;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by CrossoOverNepal on 4/6/2016.
 */
//synchronous
public interface IApiMethods {

    @GET("/users/{login}")
    Call<Github> getCurators(
            @Path("login") String login
    );
}
