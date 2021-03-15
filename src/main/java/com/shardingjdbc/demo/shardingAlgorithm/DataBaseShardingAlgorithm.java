
package com.shardingjdbc.demo.shardingAlgorithm;


import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;


/**
 * 分库策略
 *
 * @author zhangjianfeng 2021/3/11
 */

public class DataBaseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {


    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        int index = (int) (preciseShardingValue.getValue() % 2);
        for (String database : collection) {

            if (database.endsWith(String.valueOf(index))) {
                return database;
            }
        }
        throw new RuntimeException("无效的database shard");
    }
}

