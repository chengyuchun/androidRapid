package com.nuwa.core.http.converter;

import com.google.protobuf.MessageLite;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

final class ProtoRequestBodyConverter<T extends MessageLite> implements Converter<T, RequestBody> {
  private static final MediaType MEDIA_TYPE = MediaType.parse("application/x-protobuf");

  @Override public RequestBody convert(T value) throws IOException {
    byte[] bytes = value.toByteArray();
    return RequestBody.create(MEDIA_TYPE, bytes);
  }
}