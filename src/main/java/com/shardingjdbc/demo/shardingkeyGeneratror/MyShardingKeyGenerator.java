package com.shardingjdbc.demo.shardingkeyGeneratror;

import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Random;

public class MyShardingKeyGenerator implements ShardingKeyGenerator {


    public static Long generateOrderNo() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        int offSet = new Random().nextInt();
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        long milli = instant.toEpochMilli();
        String number = String.valueOf(milli ^ offSet);
        return Long.valueOf(now.format(dateTimeFormatter) + number.substring(number.length() - 6));
    }

    @Override
    public Comparable<?> generateKey() {
        return generateOrderNo();
    }

    @Override
    public String getType() {
        /** 此属性是为了在配置
         * shardingRuleConfig.setDefaultKeyGeneratorConfig(new KeyGeneratorConfiguration("ZJF_KEY","id"));
         * 中使用
         **/
        return "My";
    }

    @Override
    public Properties getProperties() {
        return new Properties();
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
