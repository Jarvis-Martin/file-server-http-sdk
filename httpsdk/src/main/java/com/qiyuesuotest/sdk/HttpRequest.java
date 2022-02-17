package com.qiyuesuotest.sdk;

import com.qiyuesuotest.sdk.HttpRequest;
import com.qiyuesuotest.sdk.config.HttpMethod;
import lombok.Builder;

import javax.management.ObjectName;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.function.Function;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/17-8:05
 * @version: 1.0
 */
public class HttpRequest {
    public static final String BOUNDARY = "----WebKitFormBoundaryc8WdKY0ghxT0rwwa";
    public static final String TWO_HYPHENS = "--";
    public static final String LINE_END = "\r\n";

    /**
     * 资源路径，如 /file/upload
     */
    private String path;
    private HttpMethod method;
    /**
     * 基础路径，如 http://localhost:8080
     */
    private String baseURL;
    /**
     * 用于设置 http 请求头的 键值对
     */
    private HashMap<String, String> header;
    /**
     * 用于追加到 url 中的参数
     */
    private HashMap<String, String> params;
    /**
     * http 请求体参数
     */
    private HashMap<String, Object> data;
    /**
     * 连接超时时间
     */
    private Integer connectTimeOut = 15_000;
    /**
     * 读取超时时间
     */
    private Integer readTimeOut = 60_000;


    public Integer connectTimeOut() {
        return connectTimeOut;
    }

    public Integer readTimeOut() {
        return readTimeOut;
    }

    public void connectTimeOut(Integer connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public void readTimeOut(Integer readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public String path() {
        return path;
    }

    public HttpMethod method() {
        return method;
    }

    public String baseURL() {
        return baseURL;
    }

    public HashMap<String, String> header() {
        return header;
    }

    public HashMap<String, String> params() {
        return params;
    }

    public HashMap<String, Object> data() {
        return data;
    }

    public void path(String path) {
        this.path = path;
    }

    public void method(HttpMethod method) {
        this.method = method;
    }

    public void baseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public void header(HashMap<String, String> header) {
        this.header = header;
    }

    public void params(HashMap<String, String> params) {
        this.params = params;
    }

    public void data(HashMap<String, Object> data) {
        this.data = data;
    }

    public static HttpRequestBuilder builder() {
        return new HttpRequestBuilder();
    }

    public static class HttpRequestBuilder {
        @NotNull
        @NotEmpty
        private String path;

        private HttpMethod method = HttpMethod.GET;
        @NotNull
        @NotEmpty
        private String baseURL;

        private HashMap<String, String> header;
        private HashMap<String, String> params;
        private HashMap<String, Object> data;
        @Min(0)
        private Integer connectTimeOut = 15_000;
        @Min(0)
        private Integer readTimeOut = 60_000;

        public HttpRequestBuilder path(String path) {
            this.path = path;
            return this;
        }

        public HttpRequestBuilder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public HttpRequestBuilder baseURL(String baseURL) {
            this.baseURL = baseURL;
            return this;
        }

        public HttpRequestBuilder header(HashMap<String, String> header) {
            this.header = header;
            return this;
        }

        public HttpRequestBuilder params(HashMap<String, String> params) {
            this.params = params;
            return this;
        }

        public HttpRequestBuilder data(HashMap<String, Object> data) {
            this.data = data;
            return this;
        }


        public HttpRequestBuilder connectTimeOut(Integer connectTimeOut) {
            this.connectTimeOut = connectTimeOut;
            return this;
        }

        public HttpRequestBuilder readTimeOut(Integer readTimeOut) {
            this.readTimeOut = readTimeOut;
            return this;
        }
        public HttpRequest build() {
            HttpRequest httpConfig = new HttpRequest();
            httpConfig.path(this.path);
            httpConfig.method(this.method);
            httpConfig.baseURL(this.baseURL);
            httpConfig.header(this.header);
            httpConfig.params(this.params);
            httpConfig.data(this.data);
            return httpConfig;
        }
    }
}
