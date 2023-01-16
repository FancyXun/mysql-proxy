import dao.SQLBuffer;
import dao.SQLInfo;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;


/**
 * @author zhangxun
 * @date 2022/7/29
 */
public class MysqlProxyServer {
    private static final Logger logger = LoggerFactory.getLogger(MysqlProxyServer.class);
    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new MysqlProxyServerVerticle());
    }
    public static class MysqlProxyServerVerticle extends AbstractVerticle {
        private final int port = 43306;
        private final int mysql_port = 3306;
        private final String mysqlHost = "192.168.51.158";
        @Override
        public void start() throws Exception {
            NetServer netServer = vertx.createNetServer();//创建代理服务器
            NetClient netClient = vertx.createNetClient();//创建连接mysql客户端
            netServer.connectHandler(socket -> netClient.connect(mysql_port, mysqlHost, result -> {
                //响应来自客户端的连接请求，成功之后，在建立一个与目标mysql服务器的连接
                if (result.succeeded()) {
                    //与目标mysql服务器成功连接连接之后，创造一个MysqlProxyConnection对象,并执行代理方法
                    new MysqlProxyConnection(socket, result.result()).proxy();
                } else {
                    logger.error(result.cause().getMessage(), result.cause());
                    socket.close();
                }
            })).listen(port, listenResult -> {//代理服务器的监听端口
                if (listenResult.succeeded()) {
                    //成功启动代理服务器
                    logger.info("Mysql proxy server start up.");
                } else {
                    //启动代理服务器失败
                    logger.error("Mysql proxy exit. because: " + listenResult.cause().getMessage(), listenResult.cause());
                    System.exit(1);
                }
            });
        }
    }
    public static class MysqlProxyConnection {
        private final NetSocket clientSocket;
        private final NetSocket serverSocket;
        public String queryId;
        SQLInfo sqlInfo = new SQLInfo();
        public MysqlProxyConnection(NetSocket clientSocket, NetSocket serverSocket) {
            this.clientSocket = clientSocket;
            this.serverSocket = serverSocket;
        }
        private void proxy() {
            //当代理与mysql服务器连接关闭时，关闭client与代理的连接
            serverSocket.closeHandler(v -> clientSocket.close());
            //反之亦然
            clientSocket.closeHandler(v -> serverSocket.close());
            //不管那端的连接出现异常时，关闭两端的连接
            serverSocket.exceptionHandler(e -> {
                logger.error(e.getMessage(), e);
                close();
            });
            clientSocket.exceptionHandler(e -> {
                logger.error(e.getMessage(), e);
                close();
            });
            //当收到来自客户端的数据包时，转发给mysql目标服务器
            clientSocket.handler(buffer ->{
                SQLBuffer buffer1 = ReadBuffer.readFromBuffer(sqlInfo, buffer.copy());
                this.queryId=buffer1.queryId;
                System.out.println(this.queryId);
                    serverSocket.write(buffer1.buffer);
                    });
            System.out.println(clientSocket);
            //当收到来自mysql目标服务器的数据包时，转发给客户端
            serverSocket.handler(buffer ->{
                System.out.println(this.queryId);
                clientSocket.write(WriteBuffer.reWrite(buffer.copy(),this.queryId));
                    }
            );
        }
        private void close() {
            clientSocket.close();
            serverSocket.close();
        }
    }
}

