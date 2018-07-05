# socket5
websocket
熟悉socket通信的同学，对于socket模拟server与client，实现相互通信，
或者使用websocket与java模拟的websocket服务器通信（比如一个聊天室），对于这些都比较熟悉了。但是可能会有下面这种情况，
java项目（比如storm流式处理）会在处理数据时候产生一些信息，比如监控某车间、风场，发电厂的机器数据，一旦出现异常需要即时推送异常信息到UI端/Web端，实时显示出来。这时候以上两种较为熟知的通信就会不太方便。我也是查了好多资料，得到下面这种方法：

web项目启动一个websocket的的server和一个web端的client。然后另外也用websocket（这是很少人采用的一种方式）写一个java端的client。我们把这个client放在产生实际项目中产生数据的地方，通过client与server的连接，把异常消息推送到server，由server广播给web端实时显示。

### 环境：
tomcat 7

maven项目

IDE是IDEA2017

### 项目功能描述：
启动项目，会启动一个web端的websocket-client和一个java模拟的websocket-server。

项目中另外还有一个Main类，单独启动，会模拟启动一个java端的websocket-client。

java-client可以发消息，通过server即时将消息推送到web端。
