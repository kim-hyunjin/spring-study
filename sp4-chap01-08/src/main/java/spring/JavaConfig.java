package spring;

import aspect.ExeTimeAspect;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement
public class JavaConfig {

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost/spring4fs?characterEncoding=utf8");
        dataSource.setUser("spring4");
        dataSource.setPassword("spring4");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource);
        return tm;
    }

    @Bean
    public MemberDao memberDao(DataSource dataSource) {
        return new MemberDao(dataSource);
    }

    @Bean
    public MemberRegisterService memberRegisterService(MemberDao memberDao) {
        return new MemberRegisterService(memberDao);
    }

    @Bean
    public ChangePasswordService changePasswordService(MemberDao memberDao) {
        return new ChangePasswordService(memberDao);
    }

    @Bean
    public MemberPrinter memberPrinter() {
        return new MemberPrinter();
    }

    @Bean
    public MemberListPrinter memberListPrinter(MemberDao memberDao, MemberPrinter memberPrinter) {
        return new MemberListPrinter(memberDao, memberPrinter);
    }

    @Bean(initMethod = "connect", destroyMethod = "close")
    public Client client() {
        Client client = new Client();
        client.setHost("서버");
        return client;
    }

    @Bean
    public ExeTimeAspect exeTimeAspect() {
        return new ExeTimeAspect();
    }
}
