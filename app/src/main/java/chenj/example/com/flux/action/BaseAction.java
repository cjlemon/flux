package chenj.example.com.flux.action;

import chenj.example.com.flux.annotation.ActionLabel;

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

    public BaseAction(T t){
        this.mLabel = getAnnotationLabel();
        setT(t);
    }

    public BaseAction(Exception e){
        this.mLabel = getAnnotationLabel();
        setException(e);
    }

    private String getAnnotationLabel(){
        ActionLabel annotation = getClass().getAnnotation(ActionLabel.class);
        if (annotation == null){
            throw new IllegalStateException("action缺少ActionLabel注解");
        }
        return annotation.label();
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
