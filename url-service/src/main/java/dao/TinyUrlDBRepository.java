package dao;

import service.model.TinyUrlClickedMetaData;

import java.util.Optional;

/**
 * This would contain the database implementation.
 *
 */
public class TinyUrlDBRepository implements UrlRepository {

    @Override
    public long persistLongUrl(String longUrl) {
        return 0;
    }

    @Override
    public boolean persistTinyUrl(Long primaryKey, String tinyUrl) {
        return false;
    }

    @Override
    public Optional<String> getLongUrl(Long primaryKey) {
        return Optional.empty();
    }

    @Override
    public boolean isLongUrlPersisted(String longUrl) {
        return false;
    }

    @Override
    public Optional<String> getTinyUrl(String longUrl) {
        return null;
    }

    @Override
    public boolean updateTinyUrlMetaData(Long primaryKey, TinyUrlClickedMetaData metaData) {
        return false;
    }
}
