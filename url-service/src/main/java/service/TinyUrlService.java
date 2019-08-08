package service;

import service.model.TinyUrlClickedMetaData;

import java.util.Optional;

public interface TinyUrlService {

    String createTinyUrl(String originalUrl);

    Optional<String> getLongUrl(String tinyUrl);

    boolean clickTinyUrl(String tinyUrl, TinyUrlClickedMetaData metaData);
}
