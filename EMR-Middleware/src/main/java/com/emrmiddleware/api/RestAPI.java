package com.emrmiddleware.api;

import java.util.List;

import com.emrmiddleware.api.dto.IDGenAPIDTO;
import com.emrmiddleware.api.dto.PatientAPIDTO;
import com.emrmiddleware.api.dto.PersonAPIDTO;
import com.emrmiddleware.dto.PatientDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestAPI {
	
	@POST("patients")
	Call<PatientDTO> addPatient(@Body PatientDTO patientdto);
	
  // @POST("person")
   //Call<ResponseBody> addPerson(@Header("Authorization") String credentials,@Body PersonDTO persondto);
   
   @POST("person")
   Call<ResponseBody> addPerson(@Body PersonAPIDTO persondto);
   
   @POST("person/{uuid}")
   Call<ResponseBody> editPerson(@Path("uuid") String uuid,@Body PersonAPIDTO persondto);
   
   @POST("patient")
   Call<ResponseBody> addPatient(@Body PatientAPIDTO patientapidto);
   
   @POST("person/{uuid}")
   Call<ResponseBody> editPerson(@Path("uuid") String uuid,@Body PatientAPIDTO patientapidto);
   
   @GET("generateIdentifier.form")
   Call<ResponseBody> getOpenMrsId(@Query("source") String source,@Query("username") String username,@Query("password") String password);

}