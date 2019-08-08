package textManipulation;

import org.slf4j.Logger;
import service.TinyUrlService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class TinyURLTextReplaceScanner implements TextReplaceScanner{

    private static final Logger LOGGER = getLogger(TinyURLTextReplaceScanner.class);

    private TinyUrlService tinyUrlService;
    private static final String TINY_URL_HOST = "https://sk.sh/";
    private static List<Pattern> urlPatterns = new ArrayList<>();
    private Pattern patternOfTinyUrl = Pattern.compile("^".concat(TINY_URL_HOST).concat("\\S+"));

    static {
        //VERY Naive approach
        urlPatterns.add(Pattern.compile("^https://\\S+"));
        urlPatterns.add(Pattern.compile("^http://\\S+"));
        urlPatterns.add(Pattern.compile("^www.://\\S+"));

    }

    private Function<String, String> tinyUrlConverter = word -> {
        for(Pattern p : urlPatterns) {
            String trimmedText = word.trim();
            Matcher m = p.matcher(trimmedText);
            Matcher matchesTinyUrlPattern = patternOfTinyUrl.matcher(trimmedText);
            if (m.matches() && !matchesTinyUrlPattern.matches()) {
                return TINY_URL_HOST.concat(tinyUrlService.createTinyUrl(trimmedText));
            }
        }
        return  word;
    };

    public TinyURLTextReplaceScanner (TinyUrlService tinyUrlService) {
        this.tinyUrlService = tinyUrlService;
    }

    @Override
    public String searchAndReplace(String originalText) {
        if (originalText == null || originalText.isEmpty() || originalText.isBlank())
            return originalText;
        List<String> words = Arrays.stream(originalText.split(" "))
                .map(tinyUrlConverter)
                .collect(Collectors.toList());
        return String.join(" ", words);
    }
}
