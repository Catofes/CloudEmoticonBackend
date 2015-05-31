/**
 * Created by KTachibanaM on 2015/5/31.
 * A client interface that is capable of HTTP operations
 */
public interface HttpClient {
    interface PostFinishCallback {
        void finish(boolean status);
    }

    void post(String url, String json, PostFinishCallback callback);
}
