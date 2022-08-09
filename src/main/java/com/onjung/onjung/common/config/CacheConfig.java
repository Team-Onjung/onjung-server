package com.onjung.onjung.common.config;

import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Objects;

@EnableCaching
@Configuration
public class CacheConfig extends CachingConfigurerSupport {

    //CacacheManager의 적절한 관리 및 인스턴스를 제공하는데 필요하며 EhCache 설정 리소스를 구성한다.
    @Bean
    public EhCacheManagerFactoryBean cacheManagerFactoryBean(){
        EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
        //테스트를 위해 추가
        factoryBean.setShared(true);
        return factoryBean;
    }

    //  EhcacheManager 등록
    @Bean
    @Scope
    public EhCacheCacheManager ehCacheCacheManager(){

        // 클라이언트 피드 캐시 설정
        CacheConfiguration clientFeedConfiguration = new CacheConfiguration()
                .eternal(false) //true일 경우 timeout 관련 설정이 무시, element가 캐시에서 삭제되지 않음.
                .timeToIdleSeconds(0) //Element가 지정한 시간 동안 사용(조회)되지 않으면 캐시에서 제거된다. 이 값이 0인 경우 조회 관련 만료 시간을 지정하지 않는다.
                .timeToLiveSeconds(21600) //Element가 존재하는 시간. 이 시간이 지나면 캐시에서 제거된다. 이 시간이 0이면 만료 시간을 지정하지 않는다.
                .maxEntriesLocalHeap(0) //Heap 캐시 메모리 pool size 설정, GC대상이 됨.
                .memoryStoreEvictionPolicy("LRU") //캐시가 가득찼을때 관리 알고리즘 설정 default "LRU"
                .name("clientFeedCaching");

        // 서버 피드 캐시 설정
        CacheConfiguration severFeedConfiguration = new CacheConfiguration()
                .eternal(false) //true일 경우 timeout 관련 설정이 무시, element가 캐시에서 삭제되지 않음.
                .timeToIdleSeconds(0) //Element가 지정한 시간 동안 사용(조회)되지 않으면 캐시에서 제거된다. 이 값이 0인 경우 조회 관련 만료 시간을 지정하지 않는다.
                .timeToLiveSeconds(21600) //Element가 존재하는 시간. 이 시간이 지나면 캐시에서 제거된다. 이 시간이 0이면 만료 시간을 지정하지 않는다.
                .maxEntriesLocalHeap(0) //Heap 캐시 메모리 pool size 설정, GC대상이 됨.
                .memoryStoreEvictionPolicy("LRU") //캐시가 가득찼을때 관리 알고리즘 설정 default "LRU"
                .name("serverFeedCaching");

        // 설정을 가지고 캐시 생성
        Cache clientFeedCaching = new net.sf.ehcache.Cache(clientFeedConfiguration);
        Cache severFeedCaching = new net.sf.ehcache.Cache(severFeedConfiguration);

        // 캐시 팩토리에 생성한 eh캐시를 추가
        Objects.requireNonNull(cacheManagerFactoryBean().getObject()).addCache(clientFeedCaching);
        Objects.requireNonNull(cacheManagerFactoryBean().getObject()).addCache(severFeedCaching);

        // 캐시 팩토리를 넘겨서 eh캐시 매니저 생성
        return new EhCacheCacheManager(Objects.requireNonNull(cacheManagerFactoryBean().getObject()));
    }
}
