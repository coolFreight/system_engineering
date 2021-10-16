
import org.junit.Before;
import org.junit.Test;
import tinyurl.codec.TinyUrlCodec;
import tinyurl.dao.UrlRepository;
import tinyurl.service.CreateTinyUrl;
import tinyurl.service.SimpleTinyUrlResource;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class SimpleTinyUrlResourceTest {

    private SimpleTinyUrlResource simpleTinyUrlService;
    private UrlRepository urlRepository = mock(UrlRepository.class);
    private TinyUrlCodec<Long, String> urlEncoder = mock(TinyUrlCodec.class);

    @Before
    public void setUp() {
//        simpleTinyUrlService = new SimpleTinyUrlService();
        simpleTinyUrlService.setEncoder(urlEncoder);
        simpleTinyUrlService.setUrlRepository(urlRepository);
    }

    @Test
    public void shouldCallDBandEncoderServiceWhenCreatingUrl() {
        //given
        String longUrl = "www.test.com/somePath/index.html";
        var createUrl = new CreateTinyUrl();
        Long dbValue = 98576L;
        when(urlRepository.persistLongUrl(eq(longUrl))).thenReturn(dbValue);
        when(urlEncoder.encode(any(Long.class))).thenReturn("AxTi1");

        //when
        simpleTinyUrlService.createTinyUrl(createUrl);

        //then
        verify(urlRepository, times(1)).persistLongUrl(eq(longUrl));
        verify(urlEncoder, times(1)).encode(dbValue);
    }
}
