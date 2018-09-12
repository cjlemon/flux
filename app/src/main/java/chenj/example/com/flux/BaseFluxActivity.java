package chenj.example.com.flux;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import chenj.example.com.flux.store.BaseStore;

/**
 * @author chenjun
 * create at 2018/6/19
 */
public abstract class BaseFluxActivity<S extends BaseStore> extends AppCompatActivity implements IDataChangedListener {

    protected S mStore;
    protected Context mContext;
    private Dispatcher mDispatcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        initStore(savedInstanceState == null ? extras : savedInstanceState);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mStore == null) {
            initStore(savedInstanceState);
        }
    }

    private void initStore(Bundle savedInstanceState) {
        mDispatcher = Dispatcher.getSingleton();
        mStore = initCurrentStore(savedInstanceState);
        if (mStore == null) {
            mStore = TypeUtil.getType(0, this);
        }
        if (mStore != null) {
            mStore.setDataChangedListener(this);
        }
        mDispatcher.register(mStore);
    }

    protected S initCurrentStore(Bundle bundle) {
        return null;
    }

    @Override
    public void onDataChanged(String label) {
        dataChanged(label);
    }

    /**
     * 数据发生改变
     *
     * @param label 相对应标签
     */
    public abstract void dataChanged(String label);

    @Override
    protected void onResume() {
        super.onResume();
        if (unregisterInBackground()) {
            mDispatcher.register(mStore);
        }
    }

    protected boolean showLoadingDialog(){
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (unregisterInBackground()) {
            mDispatcher.unregister(mStore);
        }
    }

    /**
     * 在不可见的时候是否解除注册
     *
     * @return true 接触 false 不解除
     */
    public boolean unregisterInBackground() {
        return false;
    }

    @Override
    protected void onDestroy() {
        mDispatcher.unregister(mStore);
        if (mStore != null) {
            mStore.release();
        }
        super.onDestroy();
    }
}
