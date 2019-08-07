package com.git.byron.sitecount.site.core;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Author: byron
 * @ProjectName: boot-demo
 * @Package: com.git.byron.sitecount.site.core
 * @ClassName: VisitService
 * @Description:
 * @Date: 2019/8/6 15:01
 * @Version: 1.0
 */
@Component
public class VisitService {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 获取pv
     *
     * pv存储结果为hash，一个应用一个key，field 为 uri；value 为 pv
     *
     * @return null 表示首次有人访问；这个时候需要 + 1
     * */
    public Long getPv(String key,String uri){
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] ans = connection.hGet(key.getBytes(),uri.getBytes());
                if(ans == null || ans.length == 0){
                    return null;
                }
                return Long.parseLong(new String(ans));
            }
        });
    }


    /**
     * 获取 uri 对应的 uv，以及当前访问 ip 的历史访问排名
     * 使用 zset 来存储，key 为 uri 唯一标识； value 为 ip； score 为访问的排名
     *
     * @param  key: 由 app 与 uri 来生成，即一个 uri 维护一个 uv 集
     * @param  ip : 访问者 IP
     * @return 返回 uv/rank 如果对应的值为 0 ，表示没有访问过
     * */
    public ImmutablePair</** uv */Long,/** rank */Long> getUv(String key,String ip){
        //获取总 uv 数  ， 也就是最大的 score
        Long uv = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Set<RedisZSetCommands.Tuple> set =  connection.zRangeWithScores(key.getBytes(),-1,-1);
                if(CollectionUtils.isEmpty(set)){
                    return 0L;
                }
                Double score = set.stream().findFirst().get().getScore();
                return score.longValue();
            }
        });

        if(uv == null || uv == 0L){
            //表示还没有人访问过
            return ImmutablePair.of(0L,0L);
        }
        //获取 ip 对应的访问排名
        Long rank = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Double score = connection.zScore(key.getBytes(),ip.getBytes());
                return score == null? 0L : score.longValue();
            }
        });
        return ImmutablePair.of(uv,rank);
    }


    /**
     * 判断 IP 今天是否访问过
     * 采用 bitset 来判断 IP 是否有访问。key由app 与 uri唯一确定
     *@return true 表示 今天有访问过   false 表示今天没有访问过
     * */
    public boolean visitToday(String key,String ip){
       //IP 地址进行分段 127.0.0.1
        String[] segments = StringUtils.split(ip,".");
        for(int i = 0; i < segments.length; i++){
            if(!contain(key + "_" + i,Integer.valueOf(segments[i]))){
                return false;
            }
        }
        return true;
    }


    private boolean contain(String key ,Integer val){
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.getBit(key.getBytes(),val);
            }
        });
    }

    /**
     * pv 次数+1
     *
     * @param key
     * @param uri
     */
    public void addPv(String key, String uri) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection connection) throws DataAccessException {
                connection.hIncrBy(key.getBytes(), uri.getBytes(), 1);
                return null;
            }
        });
    }

    /**
     * uv +1
     *
     * @param key
     * @param ip
     * @param rank
     */
    public void addUv(String key, String ip, Long rank) {
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection connection) throws DataAccessException {
                connection.zAdd(key.getBytes(), rank, ip.getBytes());
                return null;
            }
        });
    }

    /**
     * 标记ip访问过这个key
     *
     * @param key
     * @param ip
     */
    public void tagVisit(String key, String ip) {
        String[] segments = StringUtils.split(ip, ".");
        for (int i = 0; i < segments.length; i++) {
            int finalI = i;
            redisTemplate.execute(new RedisCallback<Void>() {
                @Override
                public Void doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.setBit((key + "_" + finalI).getBytes(), Integer.valueOf(segments[finalI]), true);
                    return null;
                }
            });

        }
    }

    /**
     * 热度，每访问一次，计数都+1
     *
     * @param key
     * @param uri
     * @return
     */
    public Long addHot(String key, String uri) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hIncrBy(key.getBytes(), uri.getBytes(), 1);
            }
        });
    }

}
