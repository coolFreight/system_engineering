import codec.SimpleTinyUrlCodec;
import codec.TinyUrlCodec;
import dao.TinyUrlStubRepository;
import dao.UrlRepository;
import org.slf4j.Logger;
import service.SimpleTinyUrlService;
import service.TinyUrlService;
import service.model.TinyUrlClickedMetaData;
import textManipulation.TextReplaceScanner;
import textManipulation.TinyURLTextReplaceScanner;

import java.util.Scanner;

import static org.slf4j.LoggerFactory.*;


public class LinkShortenerClient {

    private static final Logger LOGGER = getLogger(LinkShortenerClient.class);

    public static void main(String[] args) {
        TinyUrlCodec<Long, String> urlEncoder = new SimpleTinyUrlCodec();
        UrlRepository urlRepository = new TinyUrlStubRepository();
        TinyUrlService tinyUrlService = new SimpleTinyUrlService(urlRepository, urlEncoder);
        TextReplaceScanner tinyUrlTextReplaceScanner = new TinyURLTextReplaceScanner(tinyUrlService);
        Scanner sc = new Scanner(System.in);
        int command = 1;
        do {
            switch (command) {
                case 1:
                    System.out.println("Converting text with web urls.....");
                    String originalText = getTestMessage();
                    LOGGER.info("Original text is {} ", originalText);
                    String convertedText = tinyUrlTextReplaceScanner.searchAndReplace(originalText);
                    LOGGER.info("Text was converted to : {} ", convertedText);
                    break;
                case 2:
                    System.out.println("Enter tinyUrl to simulate click on ");
                    String tinyUrl = sc.next();
                    TinyUrlClickedMetaData metaData = new TinyUrlClickedMetaData();
                    metaData.addMetaData("user-agent", "Chrome");
                    System.out.println("Enter your ip for simulation");
                    String ip = sc.next();
                    metaData.addMetaData("ip", ip);
                    tinyUrlService.clickTinyUrl(tinyUrl, metaData);
                    LOGGER.info("Simulating a user click : {} ", tinyUrl);
                    break;
                case 3:
                    System.out.println("enter long Url ");
                    String longUrl = sc.next();
                    String createdTinyUrl = tinyUrlService.createTinyUrl(longUrl);
                    System.out.println("Created tiny url "+createdTinyUrl);
                    break;
                case 4 :
                    System.out.println("Testing encoded message");
                    String encodedMessage = encodedMessage();
                    LOGGER.info("Original text is {} ", encodedMessage);
                    String result = tinyUrlTextReplaceScanner.searchAndReplace(encodedMessage);
                    LOGGER.info("Text was converted to : {} ", result);
                    break;
                case 5 :
                    System.out.println("Testing encoded message");
                    String bothEncodedAndNonEncoded = testMessageWithBothEncodedAndNonEncoded();
                    LOGGER.info("Original text is {} ", bothEncodedAndNonEncoded);
                    String result1 = tinyUrlTextReplaceScanner.searchAndReplace(bothEncodedAndNonEncoded);
                    LOGGER.info("Text was converted to : {} ", result1);
                    break;

            }
            printMenu();
            command = sc.nextInt();
        } while (!sc.nextLine().equals("quit"));
        System.out.println("Shutting down service registry process");
    }

    private static void printMenu() {
        System.out.println("Enter a number for a service selection: ");
        System.out.println("1) Enter text that may have web urls to be converted to tinyurls");
        System.out.println("2) Simulate tinyUrl click");
        System.out.println("3) Convert longUrl");
        System.out.println("4) test encoded message");
        System.out.println("5) test both encoded and nonEncoded message");

    }


    private static String getTestMessage() {
        return "Hey, check out this awesome typography class on Skillshare from Aaron Draplin! " +
                "https://www.skillshare.com/classes/Customizing-Type-with-Draplin-Creating-Word" +
                "marks-That-Work/1395825904";
    }

    private static String encodedMessage() {
        return "Hey, check out this awesome typography class on Skillshare from Aaron Draplin! https://sk.sh/Aa5";
    }


    private static String testMessageWithBothEncodedAndNonEncoded() {
        return "Click https://www.cnn.com/politics/index.html for more info.  Then head over to  Skillshare from Aaron Draplin! https://sk.sh/Aa5";
    }
}
