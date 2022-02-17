# file-server-http-sdk
## server包
基于 spring boot 实现的文件服务器(文件上传、文件下载、文件元数据获取)，本来想用 netty 实现的，但是发现对 netty 中 http 方面类知识掌握不够深入，故没有采用 netty。
### FailedVersion包
基于 netty 实现一点点的失败版本。

## httpsdk包
基于 HttpURLCononection 封装的 http sdk，主要类包括 HttpRequest、HttpResponse、HttpClient。

## commons包
公共类所在地。
