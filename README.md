# FtpServerTest

java构建的FTP服务器

# MinaServer

接收和发送来自MinaClient的消息，测试用，不做复杂性处理

# MinaClient

## 主要作用：

* 与服务端建立session连接，接收和发送消息
* 根据服务端发送消息，解析，目前只做将本地录音文件上传到FTP服务器中

## 涉及技术要点：

* session断开重连，与FTP断开重连，多次（参数可配）未收到MinaServer回复消息断开重连
* 并发优化处理，利用SpringMVC包对消息队列处理，线程池优化等
* 日志文件与控制台记录，逐日更新
* 录音文件wav转换为mp3，即压缩上传
* 支持jar打包，控制台或javaw调用运行 


工程后续开发已集成到有界面的客户端中，不便公开。

