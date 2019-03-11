package callBackSelf;

import org.apache.commons.lang.StringUtils;

/**
 * Created by 2P on 19-3-11.
 */
public class ClassB {

    public static void tryReceive(String data,MyCallBack callBack){
        if(StringUtils.isEmpty(data)){
            callBack.onError("B");
        }else{
            callBack.onSuccess("B");
        }
    }
}
