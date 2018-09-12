package chenj.example.com.flux;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import chenj.example.com.flux.action.BaseAction;
import chenj.example.com.flux.store.BaseStore;

/**
 * @author chenjun
 * create at 2018/6/15
 */
public class Dispatcher {

    public enum ThreadStrategy {
        /**
         * 当前线程
         */
        DEFAULT,
        /**
         * 主线程
         */
        MAIN,
        /**
         * 子线程
         */
        IO
    }

    private static volatile Dispatcher dispatcher;

    private Map<String, List<BaseStore>> mStoreMap;
    private Executor mExecutors;
    private Handler mHandler;

    public static Dispatcher getSingleton() {
        if (dispatcher == null) {
            synchronized (Dispatcher.class) {
                if (dispatcher == null) {
                    dispatcher = new Dispatcher();
                }
            }
        }
        return dispatcher;
    }

    private Dispatcher() {
        mStoreMap = new HashMap<>();
    }

    public void register(BaseStore store) {
        if (store == null) {
            return;
        }
        List<String> labels = store.getActionLabels();
        for (String label : labels) {
            List<BaseStore> stores = mStoreMap.get(label);
            if (stores == null) {
                stores = new ArrayList<>();
                mStoreMap.put(label, stores);
            }
            //防止重复注册
            if (stores.contains(store)) {
                continue;
            }
            stores.add(store);
        }
    }

    public void unregister(BaseStore store) {
        if (store == null) {
            return;
        }
        List<String> labels = store.getActionLabels();
        for (String label : labels) {
            if (!mStoreMap.containsKey(label)) {
                continue;
            }
            List<BaseStore> baseStores = mStoreMap.get(label);
            if (baseStores.remove(store)) {
                if (baseStores.isEmpty()) {
                    mStoreMap.remove(label);
                }
            }
        }
    }

    public void dispatch(BaseAction action) {
        dispatch(action, ThreadStrategy.DEFAULT);
    }

    public void dispatch(BaseAction action, ThreadStrategy strategy) {
        switch (strategy) {
            case DEFAULT:
                defaultDispatch(action);
                break;
            case IO:
                ioDispatch(action);
                break;
            case MAIN:
                mainDispatch(action);
                break;
            default:
                defaultDispatch(action);
                break;
        }
    }

    private void mainDispatch(final BaseAction action) {
        final String label = action.getLabel();
        final List<BaseStore> stores = mStoreMap.get(label);
        if (stores == null) {
            return;
        }
        if (mHandler == null){
            mHandler = new Handler(Looper.getMainLooper());
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                for (BaseStore store : stores) {
                    store.changeData(action);
                    store.notifyDataChanged(label);
                }
            }
        });
    }

    private void ioDispatch(final BaseAction action) {
        final String label = action.getLabel();
        final List<BaseStore> stores = mStoreMap.get(label);
        if (stores == null) {
            return;
        }
        if (mExecutors == null){
            mExecutors = new ThreadPoolExecutor(
                    0,
                    5,
                    10,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(5),
                    new ThreadFactory() {
                        @Override
                        public Thread newThread(@NonNull Runnable r) {
                            Thread thread = new Thread();
                            thread.setName(label);
                            return thread;
                        }
                    },
                    new RejectedExecutionHandler() {
                        @Override
                        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

                        }
                    }
            );
        }
        mExecutors.execute(new Runnable() {
            @Override
            public void run() {
                for (BaseStore store : stores) {
                    store.changeData(action);
                    store.notifyDataChanged(label);
                }
            }
        });
    }

    private void defaultDispatch(BaseAction action) {
        String label = action.getLabel();
        List<BaseStore> stores = mStoreMap.get(label);
        if (stores == null) {
            return;
        }
        for (BaseStore store : stores) {
            store.changeData(action);
            store.notifyDataChanged(label);
        }
    }
}
