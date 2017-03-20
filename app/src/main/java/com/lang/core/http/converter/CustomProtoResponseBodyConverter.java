package com.lang.core.http.converter;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;
import com.yingu.jr.mobile.protos.CommunicationProtos;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by chengyuchun on 2016/8/19.
 */

final class CustomProtoResponseBodyConverter<T extends MessageLite>
        implements Converter<ResponseBody, T> {
    private final Parser<T> parser;
    CustomProtoResponseBodyConverter(Parser<T> parser) {
        this.parser = parser;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            CommunicationProtos.Result result = CommunicationProtos.Result.parseFrom(value.byteStream());
            CommunicationProtos.Result.Status resultStatus = result.getStatus();
            return parser.parseFrom(result.getData());
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e); // Despite extending IOException, this is data mismatch.
        } finally {
            value.close();
        }
    }
}