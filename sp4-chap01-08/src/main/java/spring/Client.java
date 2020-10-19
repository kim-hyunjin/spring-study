package spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Client implements InitializingBean, DisposableBean {

    private String host;

    public void setHost(String host) {
        System.out.println("Client.setHost()");
        this.host = host;
    }

    public void send() {
        System.out.println("Client.send() to " + host);
    }

    public void connect() throws Exception {
        System.out.println("Client.connect()");
    }

    public void close() throws Exception {
        System.out.println("Client.close()");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Client.destroy()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Client.afterPropertiesSet()");
    }
}
