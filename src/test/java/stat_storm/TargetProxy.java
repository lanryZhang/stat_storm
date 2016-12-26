/*
* TargetProxy.java 
* Created on  202016/10/14 17:10 
* Copyright © 2012 Phoenix New Media Limited All Rights Reserved 
*/
package stat_storm;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Method;

/**
 * Class Description Here
 *
 * @author zhanglr
 * @version 1.0.1
 */
public class TargetProxy implements MethodInterceptor {
    private Object target;
    public  TargetProxy(Object obj){
        this.target = obj;
    }

    public Object newInstance(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(NoOp.INSTANCE);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        Object result = methodProxy.invokeSuper(target,objects);
        return result;
    }
}
