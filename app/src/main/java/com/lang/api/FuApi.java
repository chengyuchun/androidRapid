package com.lang.api;

import com.lang.xjd.model.BaseModel;
import com.lang.xjd.model.Benefit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


public interface FuApi {
    @GET("api/data/福利/{pageCount}/{pageIndex}")
    Call<BaseModel<ArrayList<Benefit>>> defaultBenefits(
            @Path("pageCount") int pageCount,
            @Path("pageIndex") int pageIndex
    );

    @GET("api/data/福利/{pageCount}/{pageIndex}")
    Observable<BaseModel<ArrayList<Benefit>>> rxBenefits(
            @Path("pageCount") int pageCount,
            @Path("pageIndex") int pageIndex
    );

}
