package chenj.example.com.flux.store;

import chenj.example.com.flux.annotation.StoreLabel;
import chenj.example.com.flux.action.BaseAction;

/**
 * @author chenjun
 * create at 2018/9/18
 */
@StoreLabel(labels = {"MAIN"})
public class MainStore extends BaseStore {
    @Override
    public void changeSelfData(BaseAction action) {

    }
}
