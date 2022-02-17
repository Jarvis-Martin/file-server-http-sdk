import com.qiyuesuotest.sdk.HttpClient;
import com.qiyuesuotest.sdk.HttpRequest;
import com.qiyuesuotest.sdk.config.HttpMethod;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/16-16:10
 * @version: 1.0
 */
public class TestHttpClient1 {

    /**
     * 测试：文件上传接口
     */
    @Test
    public void testUploadFile() {
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "multipart/form-data; boundary=" + HttpRequest.BOUNDARY);

        HashMap<String, Object> data = new HashMap<>();
        data.put("file", new File("d:/files/downloadfile.png"));

        HttpRequest request = HttpRequest.builder()
                .baseURL("http://localhost:8080")
                .path("/file/upload")
                .method(HttpMethod.POST)
                .header(header)
                .data(data)
                .build();
        new HttpClient().query(
                request,
                httpResponse -> {
                    byte[] body = httpResponse.body();
                    System.out.println("success:" + new String(body));
                    Assert.assertNotNull(body);
                },
                httpResponse -> {
                    System.out.println("error:" + httpResponse.exception().getMessage());
                }
        );

    }

    /**
     * 测试：获取文件元信息接口
     */
    @Test
    public void testGetFileMeta() throws IOException {
        HttpRequest request = HttpRequest.builder()
                .baseURL("http://localhost:8080")
                .path("/file/meta/12e2c051-143c-49df-84a9-45b120295ec2")
                .method(HttpMethod.GET)
                .build();
        new HttpClient().query(
                request,
                httpResponse -> {
                    byte[] body = httpResponse.body();
                    System.out.println("success:" + new String(body));
                    Assert.assertNotNull(body);
                },
                httpResponse -> {
                    System.out.println("error:" + httpResponse.exception().getMessage());
                }
        );
    }

    /**
     * 测试：获取文件下载接口
     */
    @Test
    public void testDownloadFile() throws IOException {
        HttpRequest request = HttpRequest.builder()
                .baseURL("http://localhost:8080")
                .path("/file/download/12e2c051-143c-49df-84a9-45b120295ec2")
                .method(HttpMethod.GET)
                .build();
        new HttpClient().query(
                request,
                httpResponse -> {
                    byte[] body = httpResponse.body();
                    System.out.println("success:" + new String(body, StandardCharsets.UTF_8));
                    Assert.assertNotNull(body);
                    UUID downloadFileName = UUID.randomUUID();
                    try {
                        File file = new File("d:/files/" + downloadFileName);
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(body);
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                },
                httpResponse -> {
                    System.out.println("error:" + httpResponse.exception().getMessage());
                }
        );
    }
}
