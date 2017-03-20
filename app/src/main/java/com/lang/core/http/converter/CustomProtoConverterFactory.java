package com.lang.core.http.converter;

import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by chengyuchun on 2016/8/19.
 */
public final class CustomProtoConverterFactory extends Converter.Factory{
    public static CustomProtoConverterFactory create() {
        return new CustomProtoConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        if (!(type instanceof Class<?>)) {
            return null;
        }
        Class<?> c = (Class<?>) type;
        if (!MessageLite.class.isAssignableFrom(c)) {
            return null;
        }

        Parser<MessageLite> parser;
        try {
            Field field = c.getDeclaredField("PARSER");
            //noinspection unchecked
            parser = (Parser<MessageLite>) field.get(null);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException(
                    "Found a protobuf message but " + c.getName() + " had no PARSER field.");
        } catch(IllegalAccessException e){
            throw new IllegalArgumentException(
                    "Found a protobuf message but " + c.getName() + " had no PARSER field.");
        }
        return new CustomProtoResponseBodyConverter<>(parser);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (!(type instanceof Class<?>)) {
            return null;
        }
        if (!MessageLite.class.isAssignableFrom((Class<?>) type)) {
            return null;
        }
        return new ProtoRequestBodyConverter<>();
    }
}
