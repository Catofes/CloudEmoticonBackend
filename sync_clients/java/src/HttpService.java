/**
 * Created by KTachibanaM on 2015/5/31.
 * A service interface that is capable of HTTP operations
 */
public interface HttpService {
    interface PostFinishCallback {
        void success(String data);
        void fail();
    }

    // TODO: use json
    void post(String url, String json, PostFinishCallback callback);
}
