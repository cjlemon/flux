package chenj.example.com.flux;

import chenj.example.com.flux.action.BaseAction;

/**
 * @author chenjun
 * create at 2018/6/24
 */
public class BaseCreator {
    private Dispatcher mDispatcher;
    public BaseCreator(){
        mDispatcher = Dispatcher.getSingleton();
    }

    public void dispatch(BaseAction action){
        mDispatcher.dispatch(action);
    }
}
