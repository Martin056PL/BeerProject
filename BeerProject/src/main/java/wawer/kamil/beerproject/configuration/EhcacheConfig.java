package wawer.kamil.beerproject.configuration;


import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EhcacheConfig extends CachingConfigurerSupport {


    @Bean
    public CacheManager ehCacheManager() {
        CacheConfiguration beerCache = new CacheConfiguration();
        beerCache.setName("ten-second-cache");
        beerCache.setMemoryStoreEvictionPolicy("LRU");
        beerCache.setMaxEntriesLocalHeap(1000);
        beerCache.setTimeToLiveSeconds(10);

        CacheConfiguration breweryCache = new CacheConfiguration();
        breweryCache.setName("twenty-second-cache");
        breweryCache.setMemoryStoreEvictionPolicy("LRU");
        breweryCache.setMaxEntriesLocalHeap(1000);
        breweryCache.setTimeToLiveSeconds(20);

        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(beerCache);
        config.addCache(breweryCache);
        return net.sf.ehcache.CacheManager.newInstance(config);
    }

    @Bean
    @Override
    public org.springframework.cache.CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }
}


//    @Bean
//    public EhCacheCacheManager cacheManager(CacheManager cacheManager) {
//        return new EhCacheCacheManager(cacheManager);
//
//    }
//
//    @Bean
//    public EhCacheManagerFactoryBean ehcache() {
//        EhCacheManagerFactoryBean ehcacheManagerFactoryBean = new EhCacheManagerFactoryBean();
//        ehcacheManagerFactoryBean.setConfigLocation(new ClassPathResource("src/main/resources/ehcache.xml"));
//        return ehcacheManagerFactoryBean;
//
//    }


