package six.com.crawler.redis;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.HostAndPort;
import six.com.crawler.dao.EnhanceJedisCluster;

/**
 * @author 作者
 * @E-mail: 359852326@qq.com
 * @date 创建时间：2017年5月12日 上午10:28:45
 */
public class RedisUtils {

	public static EnhanceJedisCluster newJedis(String connectionStr) {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxIdle(10);
		config.setBlockWhenExhausted(true);
		// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted), 如果超时就抛异常, 小于零: 阻塞不确定的时间
		config.setMaxWaitMillis(-1);
		// 连接耗尽时是否阻塞, false报异常, ture阻塞直到超时, 默认true
		config.setBlockWhenExhausted(true);
		// 设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
		config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
		// 是否启用pool的jmx管理功能
		config.setJmxEnabled(false);
		// 是否启用后进先出
		config.setLifo(true);
		// 最大空闲连接数
		config.setMaxIdle(8);
		// 最大连接数
		config.setMaxTotal(8);
		// 逐出连接的最小空闲时间 默认1800 000 毫秒(30分钟)
		config.setMinEvictableIdleTimeMillis(1800000);
		// 最小空闲连接数
		config.setMinIdle(0);
		// 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		config.setNumTestsPerEvictionRun(3);
		// 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数
		// 时直接逐出,不再根据MinEvictableIdleTimeMillis判断 (默认逐出策略)
		config.setSoftMinEvictableIdleTimeMillis(1800000);
		// 在获取连接的时候检查有效性
		config.setTestOnBorrow(true);
		// 在空闲时检查有效性
		config.setTestWhileIdle(true);
		// 逐出扫描的时间间隔(毫秒) 如果为负数, 则不运行逐出线程
		config.setTimeBetweenEvictionRunsMillis(-1);
		String[] hosts = connectionStr.split(";");
		String[] temp = null;
		Set<HostAndPort> set = new HashSet<>();
		for (String host : hosts) {
			temp = host.split(":");
			set.add(new HostAndPort(temp[0], Integer.valueOf(temp[1])));
		}
		EnhanceJedisCluster jedisCluster = new EnhanceJedisCluster(set, 6000, config);
		return jedisCluster;
	}
}
