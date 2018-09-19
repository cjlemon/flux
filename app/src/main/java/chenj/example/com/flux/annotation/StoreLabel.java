package chenj.example.com.flux.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author chenjun
 * create at 2018/9/18
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface StoreLabel {
    String[] labels();
}
