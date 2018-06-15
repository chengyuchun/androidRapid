package com.nuwa.core.http.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by chengyuchun on 2017/4/6.
 */
final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
  private final Gson gson;
  private final TypeAdapter<T> adapter;

  CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
    this.gson = gson;
    this.adapter = adapter;
  }

  @Override public T convert(ResponseBody value) throws IOException {
    JsonReader jsonReader = gson.newJsonReader(value.charStream());
    try {
      return adapter.read(jsonReader);
//      if(t!=null){
//        BaseModel re = (BaseModel)t;
//        //根据实际业务情况抛出异常
//        if (re.isError()) {
//          throw new ApiException(re.getCode(), re.getMessage());
//        }
//      }
    } finally {
      value.close();
    }
  }
}
