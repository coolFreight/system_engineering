package tinyurl.service;

import tinyurl.service.model.TinyUrlClickedMetaData;

import java.util.Optional;

public interface TinyUrlService {

    String createTinyUrl(CreateTinyUrl createTinyUrl);

    Optional<String> getLongUrl(String tinyUrl);

    boolean clickTinyUrl(String tinyUrl, TinyUrlClickedMetaData metaData);
}
