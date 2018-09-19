package chenj.example.com.flux;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import chenj.example.com.flux.action.MainAction;
import chenj.example.com.flux.store.MainStore;

public class MainActivity extends BaseFluxActivity<MainStore> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvHello = findViewById(R.id.tv_hello);
        tvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dispatcher.getSingleton().dispatch(new MainAction(""));
            }
        });
    }

    @Override
    public void dataChanged(String label) {
        Toast.makeText(this, label, Toast.LENGTH_SHORT).show();
    }
}
