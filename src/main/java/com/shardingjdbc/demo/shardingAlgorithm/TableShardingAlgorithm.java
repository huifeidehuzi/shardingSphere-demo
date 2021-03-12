/*
package com.shardingjdbc.demo.shardingAlgorithm;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;

import java.util.Arrays;
import java.util.Collection;


*/
/**
 * 分表策略
 *
 * @author zhangjianfeng 2021/3/11
 *//*

public class TableShardingAlgorithm implements ComplexKeysShardingAlgorithm {


    // 分表key
    private static String shardingColumn = "id";

    @Override
    public Collection<String> doSharding(Collection<String> collection, Collection<ShardingValue> shardingValues) {
        // 获取分表key对应的ShardingValue
        ShardingValue shardingValue = null;
        for (ShardingValue s : shardingValues) {
            if (s.equals(shardingColumn)) {
                shardingValue = s;
            }
        }
        if (shardingValue == null) {
            throw new RuntimeException("未获取shardingValue");
        }
        PreciseShardingValue<Long> preciseShardingValue = (PreciseShardingValue<Long>) shardingValue;
        for (String s : collection) {
            if (s.endsWith(String.valueOf(preciseShardingValue.getValue() % 2))) {
                return Arrays.asList(s);
            }
        }
        throw new RuntimeException("未获取表");
    }
}
*/
