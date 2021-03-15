
package com.shardingjdbc.demo.shardingAlgorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 分表策略
 *
 * @author zhangjianfeng 2021/3/11
 */

public class TableShardingAlgorithm implements PreciseShardingAlgorithm<Long> {


    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        int index = (int) (shardingValue.getValue() % 2);
        for (String table : availableTargetNames) {

            if (table.endsWith(String.valueOf(index))) {
                return table;
            }
        }
        throw new RuntimeException("无效的table shard");
    }
}

