package service;

import dao.UrlRepository;
import encoders.UrlEncoder;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class SimpleTinyUrlServiceTest {

    private SimpleTinyUrlService simpleTinyUrlService;
    private UrlRepository urlRepository = mock(UrlRepository.class);
    private UrlEncoder<Long, String> urlEncoder = mock(UrlEncoder.class);

    @Before
    public void setUp() {
        simpleTinyUrlService = new SimpleTinyUrlService();
        simpleTinyUrlService.setEncoder(urlEncoder);
        simpleTinyUrlService.setUrlRepository(urlRepository);
    }

    @Test
    public void shouldCallDBandEncoderServiceWhenCreatingUrl() {
        //given
        String longUrl = "www.test.com/somePath/index.html";
        Long dbValue = 98576L;
        when(urlRepository.persistLongUrl(eq(longUrl))).thenReturn(dbValue);
        when(urlEncoder.encode(any(Long.class))).thenReturn("AxTi1");

        //when
        simpleTinyUrlService.createTinyUrl(longUrl);

        //then
        verify(urlRepository, times(1)).persistLongUrl(eq(longUrl));
        verify(urlEncoder, times(1)).encode(dbValue);
    }
}
