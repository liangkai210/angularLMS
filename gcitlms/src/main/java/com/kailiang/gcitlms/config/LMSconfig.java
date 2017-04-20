package com.kailiang.gcitlms.config;

import com.kailiang.gcitlms.bean.Publisher;
import com.kailiang.gcitlms.dao.*;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class LMSconfig {

    private String driverName = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost/library";
    private String uname = "root";
    private String pwd = "1234";

    public BasicDataSource dataSource() {

        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driverName);
        ds.setUrl(url);
        ds.setUsername(uname);
        ds.setPassword(pwd);

        return ds;
    }

    @Bean
    public JdbcTemplate template() {
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource());

        return template;
    }

    @Bean
    public PlatformTransactionManager txManager() {
        DataSourceTransactionManager txManager = new DataSourceTransactionManager();
        txManager.setDataSource(dataSource());

        return txManager;
    }

    @Bean
    public AuthorDao authorDao() {
        return new AuthorDao();
    }

    @Bean
    public BookDao bookDao() {
        return new BookDao();
    }

    @Bean
    public GenreDao genreDao() {
        return new GenreDao();
    }

    @Bean
    public PubDao pubDao() {
        return new PubDao();
    }

    @Bean
    public BranchDao branchDao() {
        return new BranchDao();
    }

    @Bean
    public BookCopyDao bookCopyDao() {
        return new BookCopyDao();
    }

    @Bean
    public BookLoanDao bookLoanDao() {
        return new BookLoanDao();
    }

    @Bean
    public BorrowerDao borrowerDao() {
        return new BorrowerDao();
    }


}
