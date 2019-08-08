package service;

import codec.TinyUrlCodec;
import dao.UrlRepository;
import org.slf4j.Logger;
import service.model.TinyUrlClickedMetaData;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class SimpleTinyUrlService implements TinyUrlService {
    private static final Logger LOGGER = getLogger(SimpleTinyUrlService.class);


    private TinyUrlCodec<Long, String> urlEncoder;
    private UrlRepository urlRepository;
    private static final String TINY_URL_HOST = "https://sk.sh/";

    public SimpleTinyUrlService(UrlRepository urlRepository, TinyUrlCodec<Long, String> urlEncoder) {
        this.urlRepository = urlRepository;
        this.urlEncoder = urlEncoder;
    }

    public void setEncoder(TinyUrlCodec<Long, String> urlEncoder) {
        this.urlEncoder = urlEncoder;
    }

    public void setUrlRepository(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public String createTinyUrl(String longUrl) {
        if (urlRepository.isLongUrlPersisted(longUrl)) {
            LOGGER.warn("A tiny url was already created for url {} ", longUrl);
            return urlRepository.getTinyUrl(longUrl).get();
        }
        long urlId = urlRepository.persistLongUrl(longUrl);
        String tinyUrl = urlEncoder.encode(urlId);
        urlRepository.persistTinyUrl(urlId, TINY_URL_HOST.concat(tinyUrl));
        return tinyUrl;
    }

    @Override
    public Optional<String> getLongUrl(String tinyUrl) {
        long urlId = urlEncoder.decode(tinyUrl);
        Optional<String> longUrl = urlRepository.getLongUrl(urlId);
        LOGGER.info("Retrieved long url = {} for tinyUrl={}", longUrl.orElse(""), tinyUrl);
        return longUrl;
    }

    @Override
    public boolean clickTinyUrl(String tinyUrl, TinyUrlClickedMetaData metaData) {
        LOGGER.info("tinyURl {} was clicked from ip address = {} ", tinyUrl, metaData.getMetaDataValue("ip"));
        String[] tinyUrlTokens = tinyUrl.trim().split("//")[1].split("/");
        long urlId = urlEncoder.decode(tinyUrlTokens[1]);
        return urlRepository.updateTinyUrlMetaData(urlId, metaData);
    }
}
