package tinyurl.dao;

import org.slf4j.Logger;
import tinyurl.service.SimpleTinyUrlService;
import tinyurl.service.model.TinyUrlClickedMetaData;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * This class is for simulating a database persistence for TESTING ONLY.
 * <p>
 * This would never be an ideal solution for a project of this magnitude for many reasons.
 */
public class TinyUrlStubRepository implements UrlRepository {
    private static final Logger LOGGER = getLogger(SimpleTinyUrlService.class);

    private Map<Long, TinyUrlEntry> tinyURls = new ConcurrentHashMap<>();

    private AtomicLong numberGenerator = new AtomicLong(100_000);

    public boolean isLongUrlPersisted(String longUrl) {
        Optional<TinyUrlEntry> tinyUrlEntryResult = tinyURls.values()
                .stream()
                .filter(e -> e.longUrl.equals(longUrl))
                .findFirst();
        return tinyUrlEntryResult.isPresent();
    }

    @Override
    public long persistLongUrl(String longUrl) {
        Long primaryKey = numberGenerator.incrementAndGet();
        TinyUrlEntry tinyUrlEntry = new TinyUrlEntry();
        tinyUrlEntry.primaryKey = primaryKey;
        tinyUrlEntry.longUrl = longUrl;
        tinyURls.put(primaryKey, tinyUrlEntry);
        return primaryKey;
    }

    @Override
    public boolean persistTinyUrl(Long primaryKey, String tinyUrl) {
        if (!tinyURls.containsKey(primaryKey)) {
            return false;
        }
        TinyUrlEntry tinyUrlEntry = tinyURls.get(primaryKey);
        tinyUrlEntry.tinyUrl = tinyUrl;
        return true;
    }

    @Override
    public Optional<String> getLongUrl(Long primaryKey) {
        if (!tinyURls.containsKey(primaryKey)) {
            return Optional.empty();
        }
        TinyUrlEntry tinyUrlEntry = tinyURls.get(primaryKey);
        return Optional.of(tinyUrlEntry.longUrl);
    }

    @Override
    public Optional<String> getTinyUrl(String longUrl) {
        Optional<TinyUrlEntry> tinyUrlEntryResult = tinyURls.values()
                .stream()
                .filter(e -> e.longUrl.equals(longUrl))
                .findFirst();
        return tinyUrlEntryResult.isPresent() ? Optional.of(tinyUrlEntryResult.get().tinyUrl) : Optional.empty();
    }

    private Optional<TinyUrlEntry> getTinyUrlEntry(Long primaryKey) {
        return tinyURls.containsKey(primaryKey) ? Optional.of(tinyURls.get(primaryKey)) : Optional.empty();
    }

    @Override
    public boolean updateTinyUrlMetaData(Long primaryKey, TinyUrlClickedMetaData metaData) {
        Optional<TinyUrlEntry> tinyUrlEntryResult = getTinyUrlEntry(primaryKey);
        if(!tinyUrlEntryResult.isPresent()){
            LOGGER.warn("no tiny url exist for primaryKey {}  to update ", primaryKey);
        }
        TinyUrlEntry tinyUrlEntry = tinyUrlEntryResult.get();
        tinyUrlEntry.clicks += 1;
        //use the metadata to update whatever you need at this point
        return true;
    }

    private class TinyUrlEntry {
        Long primaryKey;
        String longUrl;
        String tinyUrl;
        long clicks = 0;
    }
}
