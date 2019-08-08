package tinyurl.codec;


public class SimpleTinyUrlCodec implements TinyUrlCodec<Long, String> {

    private char[] tinyUrlCharacters = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2',
            '3', '4', '5', '6', '7', '8', '9'
    };

    @Override
    public String encode(Long val) {
        StringBuilder sb = new StringBuilder();
        while (val > 0) {
            Long charIndex = val % 62;
            sb.append(tinyUrlCharacters[charIndex.intValue()]);
            val = val / 62;
        }
        return sb.reverse().toString();
    }

    @Override
    public Long decode(String tinyUrlKey) {
        long primaryKey = 0;
        for (char letter : tinyUrlKey.toCharArray()) {
            if ('a' <= letter && letter <= 'z') {
                primaryKey = primaryKey * 62 + letter - 'a';
            } else if ('A' <= letter && letter <= 'Z') {
                primaryKey = primaryKey * 62 + letter - 'A' + 26;
            } else if ('0' <= letter && letter <= '9') {
                primaryKey = primaryKey * 62 + letter - '0' + 52;
            }
        }
        return primaryKey;
    }
}
