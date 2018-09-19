package chenj.example.com.flux.action;

import chenj.example.com.flux.annotation.ActionLabel;

/**
 * @author chenjun
 * create at 2018/9/18
 */
@ActionLabel(label = "MAIN")
public class MainAction extends BaseAction<String> {
    public MainAction(String s) {
        super(s);
    }

    public MainAction(Exception e) {
        super(e);
    }
}
