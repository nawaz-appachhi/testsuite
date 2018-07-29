package com.myntra.core.utils;

import com.myntra.scenarios.SupportTest;
import com.myntra.utils.logger.ILogger;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class DynamicLogger implements MethodInterceptor, ILogger {

    public Object intercept(Object object, Method method, Object[] methodArgs, MethodProxy methodProxy)
            throws Throwable {

        String className = method.getDeclaringClass()
                                 .getName();
        String simpleClassName = method.getDeclaringClass()
                                       .getSimpleName();
        String methodName = method.getName();
        String methodParameters = getMethodParameters(method.getParameters(), methodArgs);

        if (shouldTrackPackage(className, methodName)) {
            LOG.debug(String.format(">> Adding Step in Allure Report - %s::%s(%s)", simpleClassName, methodName,
                    methodParameters));
            SupportTest.logStep(String.format("%s.%s(%s)", simpleClassName, methodName, methodParameters));
            LOG.debug(String.format(">> Started  - %s.%s(%s)", className, methodName, methodParameters));
        }
        Object superMethodResult = methodProxy.invokeSuper(object, methodArgs);
        if (shouldTrackPackage(className, methodName)) {
            LOG.info(String.format(">> Finished - %s.%s(%s)", className, methodName, methodParameters));
        }
        return superMethodResult;
    }

    private String getMethodParameters(Parameter[] methodParameters, Object[] methodArgs) {
        String parameters = "";
        for (int numParam = 0; numParam < methodParameters.length; numParam++) {
            parameters += String.format("%s - %s, ", methodParameters[numParam].getName(),
                    methodArgs[numParam].toString());
        }
        return parameters;
    }

//    private boolean isMethodPublic(Method method) {
//        try {
//            int modifiers = method.getDeclaringClass()
//                                  .getMethod(method.getName(), null)
//                                  .getModifiers();
//            return Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers) || Modifier.isStatic(modifiers);
//        } catch (NoSuchMethodException e) {
//            LOG.debug(String.format("Method - %s - not found in class - %s", method.getName(), method.getDeclaringClass().getName()));
//            return false;
//        }
//    }

    private boolean shouldTrackPackage(String className, String methodName) {
        return (className.contains("com.myntra"))   && !(methodName.toLowerCase().contains("getlocator"))
                                                    && !(methodName.toLowerCase().contains("pagename"))
                                                    && !(methodName.toLowerCase().contains("gettestdata"))
                                                    && !(methodName.toLowerCase().contains("getchannelutils"));
    }
}
