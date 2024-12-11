package com.florend.restapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.florend.restapi.dto.CategoryDto;
import com.florend.restapi.dto.PostDto;
import com.florend.restapi.payload.PostsResponse;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;

import static com.florend.restapi.config.CacheConstants.*;
import static java.time.Duration.ofHours;
import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;
import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Bean
    public RedisCacheManagerBuilderCustomizer redisBuilderCustomizer(ObjectMapper objectMapper, RedisConnectionFactory redisConnectionFactory) {
        // Flush db to remove all caches when restarting the app
        redisConnectionFactory.getConnection().serverCommands().flushDb();

        final String CACHE_PREFIX = this.getClass().getPackageName() + ".";
        ObjectMapper cacheObjectMapper = objectMapper.copy();
        cacheObjectMapper.activateDefaultTyping(cacheObjectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                PROPERTY);
        GenericJackson2JsonRedisSerializer defaultSerializer = new GenericJackson2JsonRedisSerializer(cacheObjectMapper);

        // Create Jackson2JsonRedisSerializer for each cached model
        Jackson2JsonRedisSerializer<List<PostDto>> listPostDtoJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(cacheObjectMapper, TypeFactory.defaultInstance().constructCollectionType(List.class, PostDto.class));
        Jackson2JsonRedisSerializer<PostDto> postDtoJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(cacheObjectMapper, PostDto.class);
        Jackson2JsonRedisSerializer<PostsResponse> postsResponseDtoJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(cacheObjectMapper, PostsResponse.class);
        Jackson2JsonRedisSerializer<List<CategoryDto>> listCategoryDtoJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(cacheObjectMapper, TypeFactory.defaultInstance().constructCollectionType(List.class, CategoryDto.class));

        return builder -> builder.cacheDefaults(defaultCacheConfig()
                        .prefixCacheNameWith(CACHE_PREFIX)
//                        .disableCachingNullValues()
                        .serializeValuesWith(fromSerializer(defaultSerializer))
                        .entryTtl(ofHours(1)))
                .withCacheConfiguration(POSTS_CACHE, defaultCacheConfig()
                        .prefixCacheNameWith(CACHE_PREFIX)
                        .serializeValuesWith(fromSerializer(listPostDtoJackson2JsonRedisSerializer))
                        .entryTtl(ofHours(2)))
                .withCacheConfiguration(POST_CACHE, defaultCacheConfig()
                        .prefixCacheNameWith(CACHE_PREFIX)
                        .serializeValuesWith(fromSerializer(postDtoJackson2JsonRedisSerializer))
                        .entryTtl(ofHours(2)))
                .withCacheConfiguration(POSTS_RESPONSE_CACHE, defaultCacheConfig()
                        .prefixCacheNameWith(CACHE_PREFIX)
                        .serializeValuesWith(fromSerializer(postsResponseDtoJackson2JsonRedisSerializer))
                        .entryTtl(ofHours(2)))
                .withCacheConfiguration(CATEGORIES_CACHE, defaultCacheConfig()
                        .prefixCacheNameWith(CACHE_PREFIX)
                        .serializeValuesWith(fromSerializer(listCategoryDtoJackson2JsonRedisSerializer))
                        .entryTtl(ofHours(2)));
    }
}
