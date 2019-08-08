package codec;

public interface TinyUrlCodec<T, R> {
    R encode(T val);

    T decode(R val);
}
