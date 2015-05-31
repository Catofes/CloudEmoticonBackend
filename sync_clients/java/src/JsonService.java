/**
 * Created by KTachibanaM on 2015/5/31.
 * A service interface that is capable of decoding json
 */
public interface JsonService {
    String getFieldAsString(String json, String fieldName);
    int getFieldAsInteger(String json, String fieldName);
}
