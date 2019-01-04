package annoSelf.anno;

import java.lang.annotation.*;

/**
 * Created by 2P on 18-12-7.
 */
@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Info {

    //是男是女
    String sex();
}
