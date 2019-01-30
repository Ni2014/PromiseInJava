package nothing.promise.java;

/**to notify data to observer
 * Created by 宸笙 on 2019/1/30.
 */
public interface DataCall<T> {

    /**
     * will be called when to notify data observer
     * @param data
     */
    void onCall(T data);
}
