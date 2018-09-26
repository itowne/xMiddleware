package com.open.item.config.db;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

//@Configuration
//@EnableJpaRepositories("com.open.item.dao.*")
//@EnableTransactionManagement(proxyTargetClass = true)
public class JpaDataConfig {

    private static String PACKAGE_ENTITY_SCAN = "com.open.item.entity.*";
    private static String PROPERTIES_NAME_STRATEGY = "hibernate.ejb.naming_strategy";
    private static String PROPERTIES_SHOW_SQL = "hibernate.show_sql";
    private static String PROPERTIES_FORMAT_SQL = "hibernate.format_sql";
    private static String PROPERTIES_HBM2DLL_AUTO = "hibernate.hbm2ddl.auto";
    private static String PROPERTIES_DIALECT = "hibernate.dialect";
    private static String PROPERTIES_JDBC_BATCH = "hibernate.jdbc.batch_size";

    // @Autowired
    private DataSource dataSource;

    // @Value("${jpa.show.sql}")
    private boolean showSQL;

    // @Value("${jpa.format.sql}")
    private boolean formatSQL;

    // @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(PACKAGE_ENTITY_SCAN);
        factory.setDataSource(dataSource);
        factory.setJpaPropertyMap(jpaProperties());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    private Map<String, Object> jpaProperties() {
        Map<String, Object> propertiesMap = new HashMap<String, Object>();
        propertiesMap.put(PROPERTIES_NAME_STRATEGY, "org.hibernate.cfg.ImprovedNamingStrategy");
        propertiesMap.put(PROPERTIES_SHOW_SQL, showSQL);
        propertiesMap.put(PROPERTIES_FORMAT_SQL, formatSQL);
        propertiesMap.put(PROPERTIES_DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        propertiesMap.put(PROPERTIES_JDBC_BATCH, 50);
        propertiesMap.put(PROPERTIES_HBM2DLL_AUTO, "update");
        return propertiesMap;
    }

    // @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }
}
