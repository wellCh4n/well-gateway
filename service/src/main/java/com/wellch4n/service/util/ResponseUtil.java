package com.wellch4n.service.util;


import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/08 21:08
 * 下周我就努力工作
 */

public class ResponseUtil {

    public static FullHttpResponse buildResponse(Object data, String message, HttpResponseStatus status) {
        Result result = new Result();
        result.setMessage(message);
        result.setCode(status.code());
        result.setSuccess(status == HttpResponseStatus.OK);
        result.setData(data);

        ByteBuf content = Unpooled.copiedBuffer(JSON.toJSONString(result), CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, content);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
        return response;
    }

    public static FullHttpResponse build200Response(Object data) {
        return buildResponse(data, "执行成功", HttpResponseStatus.OK);
    }

    public static FullHttpResponse build200Response(Object data, String message) {
        return buildResponse(data, message, HttpResponseStatus.OK);
    }

    public static FullHttpResponse build403Response(String message) {
        return buildResponse(null, message, HttpResponseStatus.FORBIDDEN);
    }

    public static FullHttpResponse build404Response() {
        return buildResponse(null, "资源不存在", HttpResponseStatus.NOT_FOUND);
    }
}
