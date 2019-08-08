package tinyurl.dao;

import tinyurl.service.model.TinyUrlClickedMetaData;

import java.util.Optional;

public interface UrlRepository {

    long persistLongUrl(String longUrl);

    boolean persistTinyUrl(Long primaryKey, String tinyUrl);

    Optional<String> getLongUrl(Long primaryKey);

    boolean isLongUrlPersisted(String longUrl);

    Optional<String> getTinyUrl(String longUrl);

    boolean updateTinyUrlMetaData(Long primaryKey, TinyUrlClickedMetaData metaData);
}
