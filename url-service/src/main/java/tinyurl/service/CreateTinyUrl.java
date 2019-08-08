package tinyurl.service;

import java.io.Serializable;

public class CreateTinyUrl implements Serializable {
    private String longUrl;
    private String tinyUrl;

    public String getTinyUrl() {
        return tinyUrl;
    }

    public void setTinyUrl(String tinyUrl) {
        this.tinyUrl = tinyUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    @Override
    public String toString() {
        return "CreateTinyUrl{" +
                "longUrl='" + longUrl + '\'' +
                ", tinyUrl='" + tinyUrl + '\'' +
                '}';
    }
}
