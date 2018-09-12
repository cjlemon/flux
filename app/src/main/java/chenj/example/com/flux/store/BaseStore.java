package chenj.example.com.flux.store;

import java.util.LinkedList;
import java.util.List;

import chenj.example.com.flux.IDataChangedListener;
import chenj.example.com.flux.action.BaseAction;

/**
 * @author chenjun
 * create at 2018/6/24
 */
public abstract class BaseStore {

    private List<String> mActionLabels;
    private IDataChangedListener mListener;
    protected Exception mException;

    public BaseStore(){
        mActionLabels = new LinkedList<>();
    }

    public void setDataChangedListener(IDataChangedListener listener){
        mListener = listener;
    }

    public void addLabel(String s){
        if (s != null) {
            mActionLabels.add(s);
        }
    }

    public List<String> getActionLabels() {
        return mActionLabels;
    }

    public void notifyDataChanged(String label){
        if (mListener != null){
            mListener.onDataChanged(label);
        }
    }

    public void changeData(BaseAction action){
        String label = action.getLabel();
        changeSelfData(action);
    }

    public Exception getException() {
        return mException;
    }

    /**
     * 修改数据
     * @param action    相关的action
     */
    public abstract void changeSelfData(BaseAction action);

    public void release(){
        mListener = null;
    };

    public void deleteLabel(String label){
        mActionLabels.remove(label);
    }

    public IDataChangedListener getListener() {
        return mListener;
    }
}
