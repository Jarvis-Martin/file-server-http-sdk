package com.qiyuesuotest.sdk;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/16-13:36
 * @version: 1.0
 */
public class HttpClient {

    private byte[] responseBody;

    /**
     * 根据 request 中的 config配置信息配置 HttpURLConnection 并发送 http 请求
     *
     * @param request
     * @param onSuccess 请求成功时的回调
     * @param onError   请求失败时的回调
     * @throws IOException
     */
    public void query(HttpRequest request, Consumer<HttpResponse> onSuccess, Consumer<HttpResponse> onError) {
        InputStream is = null;
        BufferedInputStream br = null;
        try {
            URL url = new URL(getFullURL(request));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(request.method().method());
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(request.connectTimeOut());
            connection.setReadTimeout(request.readTimeOut());
            // header 不为 null 或 大小不为 0 时，给 connection 设置 http 请求头
            if (null != request.header() && request.header().size() > 0) {
                request.header().forEach(connection::setRequestProperty);
            }
            // 建立连接
            connection.connect();
            if (null != request.data()) {
                switch (request.method()) {
                    // GET 请求不需要 request body，故不做任何处理
                    case GET: {
                        break;
                    }
                    case POST: {
                        String contentType = request.header().get("Content-Type");
                        // 根据 request 中设置的 content type 将 request.data 转换成对应格式
                        if (null != contentType) {
                            // 普通文本类型,转换后格式：param1=value1&param2=value2
                            if (contentType.startsWith("text")) {
                                String textString = dataToTextString(request.data());
                                write2Connection(connection, textString);
                            }
                            // json类型,转换后格式：{param1:value1,param2:value2}
                            if (contentType.startsWith("application/json")) {
                                String jsonString = dataToJsonString(request.data());
                                write2Connection(connection, jsonString);
                            }
                            /**
                             *  form-data格式
                             *  ------WebKitFormBoundaryjGRyOQ2FL0s5BTOM
                             *  Content-Disposition: form-data; name="file"; filename="icon_coupon_center.png"
                             *  Content-Type: image/png
                             *
                             *
                             *  ------WebKitFormBoundaryjGRyOQ2FL0s5BTOM--
                             */
                            if (contentType.startsWith("multipart/form-data")) {
                                writeToConnection(connection, request.data());
                            }
                        } else {
                            // 当 request 没设置 conntent type 时，该 sdk 默认使用 json 格式
                            String jsonString = dataToJsonString(request.data());
                            write2Connection(connection, jsonString);
                        }
                    }
                    // 其他 http 方式暂时不做处理
                    default: {
                        break;
                    }
                }
            }
            // 当响应码大于 200 时，读取响应数据，并执行 onSuccss回调函数
            if (connection.getResponseCode() >= 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedInputStream(is);
                responseBody = new byte[br.available()];
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                br.read(responseBody);
                // 构建 HttpResponse
                HttpResponse response = HttpResponse.builder()
                        .code(connection.getResponseCode())
                        .body(responseBody)
                        .contentLength(connection.getContentLength())
                        .build();
                onSuccess.accept(response);
            } else {
                HttpResponse response = HttpResponse.builder()
                        .code(connection.getResponseCode())
                        .body(responseBody)
                        .contentLength(connection.getContentLength())
                        .build();
                onError.accept(response);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            HttpResponse errorResponse = HttpResponse.builder().exception(e).build();
            onError.accept(errorResponse);
        }
    }

    /**
     * 将 multipart-form-data 格式的参数写入 connection
     *
     * @param connection
     * @param data
     * @throws IOException
     */
    private void writeToConnection(HttpURLConnection connection, HashMap<String, Object> data) throws IOException {
        OutputStream outputStream = connection.getOutputStream();
        DataOutputStream ds = new DataOutputStream(outputStream);
        data.keySet().forEach(
                k -> {
                    StringBuilder multipartString = new StringBuilder();
                    // form data 中的 key - string_value对
                    if (data.get(k) instanceof String) {
                        String value = ((String) data.get(k));
                        multipartString.append(HttpRequest.TWO_HYPHENS + HttpRequest.BOUNDARY + HttpRequest.LINE_END);
                        multipartString.append("Content-Disposition: form-data; name=\"" + k + "\"" + HttpRequest.LINE_END);
                        multipartString.append("Content-Type: " + "text/plain" + HttpRequest.LINE_END);
                        //multipartString.append("Content-Lenght: " + value.length() + HttpRequest.LINE_END);
                        multipartString.append(HttpRequest.LINE_END);
                        multipartString.append(value + HttpRequest.LINE_END);
                        try {
                            ds.writeBytes(multipartString.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    // form data 中的 key - file
                    if (data.get(k) instanceof File) {
                        File file = ((File) data.get(k));
                        multipartString.append(HttpRequest.TWO_HYPHENS + HttpRequest.BOUNDARY + HttpRequest.LINE_END);
                        multipartString.append("Content-Disposition: form-data; name=\"" + k + "\"; filename=\"" + file.getName() + "\"" + HttpRequest.LINE_END);
                        multipartString.append("Content-Type: " + "image/png" + HttpRequest.LINE_END);
                        //multipartString.append("Content-Length: " + file.length() + HttpRequest.LINE_END);
                        multipartString.append(HttpRequest.LINE_END);
                        try {
                            ds.writeBytes(multipartString.toString());
                            //outputStream.flush();
                            FileInputStream fileInputStream = new FileInputStream(file);
                            byte[] buffer = new byte[1024];
                            int length = -1;
                            while ((length = fileInputStream.read(buffer)) != -1) {
                                ds.write(buffer, 0, length);
                            }
                            ds.writeBytes(HttpRequest.LINE_END);
                            fileInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        // 在所有 form data kv对写入 connection 后，最后在加上 "--{boundary}--\r\n"
        String endData = HttpRequest.TWO_HYPHENS + HttpRequest.BOUNDARY + HttpRequest.TWO_HYPHENS + HttpRequest.LINE_END;
        try {
            ds.writeBytes(endData);
            ds.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将 data 转为 json字符串
     *
     * @param data
     * @return
     */
    private String dataToJsonString(HashMap<String, Object> data) {
        StringBuilder dataString = new StringBuilder("{");
        // 构造 requestbody 字符串
        ArrayList<String> dataPair = data.keySet()
                .stream().collect(
                        ArrayList<String>::new,
                        (con, k) -> con.add(k + ":" + data.get(k)),
                        ArrayList<String>::addAll);
        Iterator<String> iterator = dataPair.iterator();
        for (; ; ) {
            String e = iterator.next();
            dataString.append(e);
            if (!iterator.hasNext()) {
                break;
            }
            dataString.append(',');
        }
        dataString.append('}');
        return dataString.toString();
    }

    /**
     * 将 data 转为 http参数字符串形式，格式：param1=value1&param2=value2
     *
     * @param data
     * @return
     */
    private String dataToTextString(HashMap<String, Object> data) {
        StringBuilder dataString = new StringBuilder();
        // 构造 requestbody 字符串
        ArrayList<String> dataPair = data.keySet()
                .stream().collect(
                        ArrayList<String>::new,
                        (con, k) -> con.add(k + "=" + data.get(k)),
                        ArrayList<String>::addAll);
        Iterator<String> iterator = dataPair.iterator();
        for (; ; ) {
            String e = iterator.next();
            dataString.append(e);
            if (!iterator.hasNext()) {
                break;
            }
            dataString.append('&');
        }
        return dataString.toString();
    }

    /**
     * 向 connection 中写入 request.data参数     * 向 connection 中写入 request.data参数
     *
     * @param connection
     * @param requestBody
     * @throws IOException
     */
    private void write2Connection(HttpURLConnection connection, String requestBody) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
        bw.write(requestBody);
        bw.close();
    }

    /**
     * 根据 config 中 baseURL、path、params 生成 queryString
     *
     * @param request
     * @return
     */
    private static String getFullURL(HttpRequest request) {
        String queryString = null;
        if (null != request.params()) {
            // map格式：(k,v) -> list格式：(k=v)
            ArrayList<String> paramsPair = request.params().keySet()
                    .stream().collect(
                            ArrayList<String>::new,
                            (con, k) -> con.add(k + "=" + request.params().get(k)),
                            ArrayList<String>::addAll);
            StringBuilder tmp = new StringBuilder("?");
            Iterator<String> iterator = paramsPair.iterator();
            for (; ; ) {
                String e = iterator.next();
                tmp.append(e);
                if (!iterator.hasNext()) {
                    break;
                }
                tmp.append('&');
            }
            queryString = tmp.toString();
        }
        return request.baseURL() + request.path() + Optional.ofNullable(queryString).orElseGet(() -> "");
    }

}
