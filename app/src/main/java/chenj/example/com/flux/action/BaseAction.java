package chenj.example.com.flux.action;

/**
 * @author chenjun
 * create at 2018/6/24
 */
public class BaseAction<T> {

    /**
     * 对于当前事件的标签
     */
    private String mLabel;

    private Exception mException;

    private T t;

    public BaseAction(String label, T t){
        this.mLabel = label;
        setT(t);
    }

    public BaseAction(String label, Exception e){
        this.mLabel = label;
        setException(e);
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String description) {
        this.mLabel = description;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public Exception getException(){
        return mException;
    }

    public void setException(Exception exception) {
        mException = exception;
    }
}
