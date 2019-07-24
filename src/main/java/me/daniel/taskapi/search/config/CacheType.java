package me.daniel.taskapi.search.config;

import lombok.Getter;

/**
 * 캐시 타입 매핑 Enumeration
 */
@Getter
public enum CacheType {
    SEARCH("SEARCH", 30, 100);
    CacheType(String cacheName, int expiredAfterWrite, int maximumSize) {
        this.cacheName = cacheName;
        this.expiredAfterWrite = expiredAfterWrite;
        this.maximumSize = maximumSize;
    }

    private String cacheName;
    private int expiredAfterWrite;
    private int maximumSize;
}
