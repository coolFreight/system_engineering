package tinyurl.service;

import tinyurl.codec.TinyUrlCodec;
import tinyurl.dao.UrlRepository;
import org.slf4j.Logger;
import tinyurl.service.model.TinyUrlClickedMetaData;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@Path("/")
public class SimpleTinyUrlResource {
    private static final Logger LOGGER = getLogger(SimpleTinyUrlResource.class);


    private TinyUrlCodec<Long, String> urlEncoder;
    private UrlRepository urlRepository;
    private static final String TINY_URL_HOST = "https://sk.sh/";

    public SimpleTinyUrlResource(){}

    public SimpleTinyUrlResource(UrlRepository urlRepository, TinyUrlCodec<Long, String> urlEncoder) {
        this.urlRepository = urlRepository;
        this.urlEncoder = urlEncoder;
    }

    public void setEncoder(TinyUrlCodec<Long, String> urlEncoder) {
        this.urlEncoder = urlEncoder;
    }

    public void setUrlRepository(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @POST
    @Path("createTinyUrl")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createTinyUrl(CreateTinyUrl createTinyUrl) {
        if (urlRepository.isLongUrlPersisted(createTinyUrl.getLongUrl())) {
            LOGGER.warn("A tiny url was already created for url {} ", createTinyUrl);
            return urlRepository.getTinyUrl(createTinyUrl.getLongUrl()).get();
        }
        long urlId = urlRepository.persistLongUrl(createTinyUrl.getLongUrl());
        String tinyUrl = urlEncoder.encode(urlId);
        urlRepository.persistTinyUrl(urlId, TINY_URL_HOST.concat(tinyUrl));
        LOGGER.info("Created a tiny url={} for long url={}",createTinyUrl.getLongUrl(), tinyUrl);
        return tinyUrl;
    }

    public Optional<String> getLongUrl(String tinyUrl) {
        long urlId = urlEncoder.decode(tinyUrl);
        Optional<String> longUrl = urlRepository.getLongUrl(urlId);
        LOGGER.info("Retrieved long url = {} for tinyUrl={}", longUrl.orElse(""), tinyUrl);
        return longUrl;
    }

    public boolean clickTinyUrl(String tinyUrl, TinyUrlClickedMetaData metaData) {
        LOGGER.info("tinyURl {} was clicked from ip address = {} ", tinyUrl, metaData.getMetaDataValue("ip"));
        String[] tinyUrlTokens = tinyUrl.trim().split("//")[1].split("/");
        long urlId = urlEncoder.decode(tinyUrlTokens[1]);
        return urlRepository.updateTinyUrlMetaData(urlId, metaData);
    }
}
