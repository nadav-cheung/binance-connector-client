package cn.com.nadav.binance.client.instruct.spot.properties;

import lombok.Data;

@Data
public class CaffeineCacheProperty {


    private static final int UNSET_INT = -1;

    private int maximumSize = UNSET_INT;

    private int maximumWeight = UNSET_INT;

    private int initialCapacity = UNSET_INT;

    private int expireAfterWriteSecond = UNSET_INT;

    private int expireAfterAccessSecond = UNSET_INT;

    private int refreshAfterWriteSecond = UNSET_INT;

}
