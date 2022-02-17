package com.qiyuesuotest.sdk.config;

import lombok.Builder;

import javax.validation.constraints.*;
import java.util.HashMap;
import java.util.function.Function;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/16-14:23
 * @version: 1.0
 */

/**
 * HttpRequest 配置类，额，不用该类了
 */
@Deprecated
public class HttpConfig {
    @NotNull
    @NotEmpty
    private String path;

    private HttpMethod method;
    @NotNull
    @NotEmpty
    private String baseURL;

    private HashMap<String, String> header;
    private HashMap<String, String> params;
    private HashMap<String, String> data;

    @Min(0)
    private Integer connectTimeOut;

    @Min(0)
    private Integer readTimeOut;

    @NotNull
    @NotEmpty
    private String contentType;

    @NotNull
    @NotEmpty
    private String responseEncoding;

    @NotNull
    private Function<Integer, Boolean> validateStatus;

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

    public HashMap<String, String> data() {
        return data;
    }

    public Integer connectTimeOut() {
        return connectTimeOut;
    }

    public Integer readTimeOut() {
        return readTimeOut;
    }

    public String contentType() {
        return contentType;
    }

    public String responseEncoding() {
        return responseEncoding;
    }

    public Function<Integer, Boolean> validateStatus() {
        return validateStatus;
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

    public void data(HashMap<String, String> data) {
        this.data = data;
    }

    public void connectTimeOut(Integer connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public void readTimeOut(Integer readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public void contentType(String contentType) {
        this.contentType = contentType;
    }

    public void responseEncoding(String responseEncoding) {
        this.responseEncoding = responseEncoding;
    }

    public void validateStatus(Function<Integer, Boolean> validateStatus) {
        this.validateStatus = validateStatus;
    }

    public static HttpConfigBuilder builder() {
        return new HttpConfigBuilder();
    }

    public static class HttpConfigBuilder {
        @NotNull
        @NotEmpty
        private String path;

        private HttpMethod method = HttpMethod.GET;
        @NotNull
        @NotEmpty
        private String baseURL;

        private HashMap<String, String> header;
        private HashMap<String, String> params;
        private HashMap<String, String> data;

        @Min(0)
        private Integer connectTimeOut = 15_000;

        @Min(0)
        private Integer readTimeOut = 60_000;

        @NotNull
        @NotEmpty
        private String contentType = "application/json";

        @NotNull
        @NotEmpty
        private String responseEncoding = "UTF-8";

        @NotNull
        private Function<Integer, Boolean> validateStatus = new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer status) {
                return status >= 200 && status < 300;
            }
        };

        public HttpConfigBuilder path(String path) {
            this.path = path;
            return this;
        }

        public HttpConfigBuilder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public HttpConfigBuilder baseURL(String baseURL) {
            this.baseURL = baseURL;
            return this;
        }

        public HttpConfigBuilder header(HashMap<String, String> header) {
            this.header = header;
            return this;
        }

        public HttpConfigBuilder params(HashMap<String, String> params) {
            this.params = params;
            return this;
        }

        public HttpConfigBuilder data(HashMap<String, String> data) {
            this.data = data;
            return this;
        }

        public HttpConfigBuilder connectTimeOut(Integer connectTimeOut) {
            this.connectTimeOut = connectTimeOut;
            return this;
        }

        public HttpConfigBuilder readTimeOut(Integer readTimeOut) {
            this.readTimeOut = readTimeOut;
            return this;
        }

        public HttpConfigBuilder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public HttpConfigBuilder responseEncoding(String responseEncoding) {
            this.responseEncoding = responseEncoding;
            return this;
        }

        public HttpConfigBuilder validateStatus(Function<Integer, Boolean> validateStatus) {
            this.validateStatus = validateStatus;
            return this;
        }

        public HttpConfig build() {
            HttpConfig httpConfig = new HttpConfig();
            httpConfig.path(this.path);
            httpConfig.method(this.method);
            httpConfig.baseURL(this.baseURL);
            httpConfig.header(this.header);
            httpConfig.params(this.params);
            httpConfig.data(this.data);
            httpConfig.connectTimeOut(this.connectTimeOut);
            httpConfig.readTimeOut(this.readTimeOut);
            httpConfig.contentType(this.contentType);
            httpConfig.responseEncoding(this.responseEncoding);
            httpConfig.validateStatus(this.validateStatus);
            return httpConfig;
        }
    }
}
