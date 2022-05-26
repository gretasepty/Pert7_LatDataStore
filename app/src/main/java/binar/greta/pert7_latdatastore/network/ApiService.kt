package binar.greta.pert7_latdatastore.network

import binar.greta.pert7_latdatastore.model.GetAllUser
import binar.greta.pert7_latdatastore.model.GetAllUserItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("user")
    fun getAllUser(
        @Query ("username") username : String
    ) : Call<List<GetAllUserItem>>

    @POST("user")
    @FormUrlEncoded
    fun loginUser(
        @Field("username") username : String,
        @Field("password") password : String
    ) : Call<GetAllUserItem>


}