package com.shardingjdbc.demo.config;

import com.shardingjdbc.demo.shardingAlgorithm.DataBaseShardingAlgorithm;
import com.shardingjdbc.demo.shardingAlgorithm.TableShardingAlgorithm;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class Config {


    /**
     * 配置数据源0，数据源的名称最好要有一定的规则，方便配置分库的计算规则
     *
     * @return
     */
    @Bean(name = "dataSource0")
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.test0")
    public DataSource dataSource0() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 配置数据源1，数据源的名称最好要有一定的规则，方便配置分库的计算规则
     *
     * @return
     */
    @Bean(name = "dataSource1")
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.test1")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(configuration);

        return sessionFactory.getObject();
    }

    @Bean
    public DataSource dataSource() throws SQLException {

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put("test0", dataSource0());
        dataSourceMap.put("test1", dataSource1());


        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("test_order", "test${0..1}.test_order${0..2}");
        // 配置分库 + 分表策略
        // Inline Groovy表达式，只支持简单的表达式配置，满足不了复杂的业务场景
        /*orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "test${id % 2}"));
        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "test_order${id % 2}"));
*/

        // 支持自定义分表分库策略
        // 有N个实现，可以看下ShardingStrategyConfiguration的每个实现类
        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", new DataBaseShardingAlgorithm()));
        orderTableRuleConfig.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", new TableShardingAlgorithm()));

        // 自定义id生成
        orderTableRuleConfig.setKeyGeneratorConfig(new KeyGeneratorConfiguration("My","id"));

        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);



        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager shardTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}
