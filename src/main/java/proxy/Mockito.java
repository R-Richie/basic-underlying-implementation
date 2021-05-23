package proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Mockito {
    private static Map<Invocation, Object> results = new HashMap<>();
    private static Invocation lastInvocation;

    public static <T> T mock(Class<T> clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new MockInterceptor());
        return (T)enhancer.create();
    }
    private static class MockInterceptor implements MethodInterceptor{

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            Invocation invocation = new Invocation(methodProxy, method, objects, methodProxy);
            lastInvocation = invocation;
            if (results.containsKey(invocation)) {
                return results.get(invocation);
            }
            return null;
        }
    }
    public static <T> When<T> when(T b){
        return new When<T>();
    }
    public static class When<T> {
        public void thenReturn(T retObj){
            results.put(lastInvocation, retObj);
        }
    }
}
