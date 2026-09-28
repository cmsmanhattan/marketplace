package com.cbsinc.cms.faceds;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Optional Redis-backed cache of uploaded files, with a file-system fallback.
 */
public class RedisUtils {

	final private static Logger log = Logger.getLogger(RedisUtils.class);
	final ResourceBundle setupResources = PropertyResourceBundle.getBundle("appconfig");

	JedisPool jedisPool;

	static RedisUtils redisUtils = new RedisUtils();

	/**
	 * @return the singleton
	 */
	static public RedisUtils getInstance() {
		return redisUtils;
	}

	private JedisPoolConfig buildPoolConfig() {

		String redisMaxTotal = setupResources.getString("redis_max_total");
		redisMaxTotal = redisMaxTotal == null ? "128" : redisMaxTotal;

		String redisMaxIdle = setupResources.getString("redis_max_idle");
		redisMaxIdle = redisMaxIdle == null ? "128" : redisMaxIdle;

		String redisMinIdle = setupResources.getString("redis_min_idle");
		redisMinIdle = redisMinIdle == null ? "16" : redisMinIdle;

		final JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(Integer.parseInt(redisMaxTotal));
		poolConfig.setMaxIdle(Integer.parseInt(redisMaxIdle));
		poolConfig.setMinIdle(Integer.parseInt(redisMaxIdle));
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);
		poolConfig.setTestWhileIdle(true);
		poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
		poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
		poolConfig.setNumTestsPerEvictionRun(3);
		poolConfig.setBlockWhenExhausted(true);
		return poolConfig;
	}

	private RedisUtils() {
		String redisHost = setupResources.getString("redis_host");
		redisHost = redisHost == null ? "localhost" : redisHost;
		String redisPost = setupResources.getString("redis_post");
		redisPost = redisPost == null ? "6379" : redisPost;

		final JedisPoolConfig poolConfig = buildPoolConfig();
		jedisPool = new JedisPool(poolConfig, redisHost, Integer.parseInt(redisPost));
	}

	/**
	 * Builds the cache key of a file.
	 * @return the key bytes
	 */
	public byte[] getKey(String filePath, String fileName) {
		byte[] key = (filePath + fileName).getBytes();
		return key;

	}

	/**
	 * Builds the cache key of a file.
	 * @return the key bytes
	 */
	public byte[] getKey(String fileNameAsStoreKey) {
		byte[] key = fileNameAsStoreKey.getBytes();
		return key;

	}

	/**
	 *
	 * @param fileURL
	 * @param fileNameAsStoreKey
	 */
	public void writeFileInRedis(String fileURL, String fileNameAsStoreKey) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] bytes = Files.readAllBytes(new File(fileURL).toPath());
			log.info("Setting bytes of length:" + bytes.length + " got from file " + fileURL + "in redis.");
			byte[] key = getKey(fileNameAsStoreKey);
			if (bytes == null || bytes.length == 0 || key == null || key.length == 0) {
				Exception ex = new Exception("Setting bytes of length:" + bytes.length + " got from file " + fileURL
						+ "in redis. Key = " + new String(key));
				log.error(ex.getMessage(), ex);
				throw ex;

			}
			jedis.set(key, bytes);

		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	/**
	 * Reads the file from Redis and writes it to the file system.
	 * @return the file bytes
	 */
	public byte[] readAndWriteFileInFS(String fileURL, byte[] key) {
		byte[] retrievedBytes = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			retrievedBytes = jedis.get(key);
			System.out.println("Saving data in file " + fileURL);
			Files.write(Paths.get(fileURL), retrievedBytes);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return retrievedBytes;
	}

	/*
	 * public byte[] readAndWriteFileInFS(String filePath, String newFileName,
	 * byte[] key) { byte[] retrievedBytes = null; Jedis jedis = null; try { jedis =
	 * jedisPool.getResource(); retrievedBytes = jedis.get(key);
	 * System.out.println("Saving data in file " + filePath + newFileName);
	 * Files.write(Paths.get(filePath + newFileName), retrievedBytes); } catch
	 * (Exception ex) { log.error(ex.getMessage(), ex); } finally { if (jedis !=
	 * null) jedis.close(); } return retrievedBytes; }
	 *
	 *
	 * public void writeFileInFS(String filePath, String newFileName, byte[] key) {
	 * byte[] retrievedBytes = null; Jedis jedis = null; try { jedis =
	 * jedisPool.getResource(); System.out.println("Saving data in file " + filePath
	 * + newFileName); Files.write(Paths.get(filePath + newFileName),
	 * retrievedBytes); } catch (Exception ex) { log.error(ex.getMessage(), ex); }
	 * finally { if (jedis != null) jedis.close(); } }
	 *
	 */

	public void writeFileInFS(String fileURL, byte[] key) {
		byte[] retrievedBytes = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			retrievedBytes = jedis.get(key);
			if (retrievedBytes == null || retrievedBytes.length == 0 || key == null || key.length == 0) {
				Exception ex = new Exception("Retrieved bytes of length:" + retrievedBytes.length + " got from file "
						+ fileURL + "in redis. Key = " + new String(key));
				log.error(ex.getMessage(), ex);
				throw ex;

			}

			System.out.println("Saving data in file " + fileURL + " key = " + new String(key));
			Files.write(Paths.get(fileURL), retrievedBytes);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	/**
	 * @return the bytes stored under the key, or null
	 */
	public byte[] readeFileFromRedis(byte[] key) {

		byte[] retrievedBytes = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			retrievedBytes = jedis.get(key);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		} finally {
			if (jedis != null)
				jedis.close();
		}
		return retrievedBytes;
	}

}
