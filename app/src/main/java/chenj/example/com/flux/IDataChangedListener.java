package chenj.example.com.flux;

/**
 * @author chenjun
 * create at 2018/6/24
 */
public interface IDataChangedListener {

    /**
     * 数据发生变化的回调方法
     * @param label 当前事件标签
     */
    void onDataChanged(String label);
}
