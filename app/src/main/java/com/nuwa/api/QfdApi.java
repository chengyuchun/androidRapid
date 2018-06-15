package com.nuwa.api;
import com.yingu.jr.mobile.protos.IndexProtos;
import com.yingu.jr.mobile.protos.PlanProtos;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


public interface QfdApi{
    @FormUrlEncoded
    @POST("/p/listPlan/")
    Observable<PlanProtos.PlanList> getListPlan(@Field("page") int page);

    @POST("/top_banner/")
    Observable<IndexProtos.TopBannerList> getTopBanner();

}
