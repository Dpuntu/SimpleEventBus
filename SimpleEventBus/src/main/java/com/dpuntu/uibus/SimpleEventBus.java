package com.dpuntu.uibus;

import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/11/2.
 *
 * @author dpuntu
 */

public class SimpleEventBus {
    private static SimpleEventBus sMSimpleEventBus = new SimpleEventBus();

    private static final String TAG = "SimpleEventBus";

    private SimpleEventBus() {
    }

    public static SimpleEventBus getDefault() {
        return sMSimpleEventBus;
    }

    /**
     * 类 -方法缓存队列
     */
    private SimpleArrayMap<Class<?>, List<ObjMethod>> mObjMethodMap = new SimpleArrayMap<>();

    /**
     * 方法参数 -方法缓存队列
     */
    private SimpleArrayMap<Object, List<ClassMethod>> mClassMethodMap = new SimpleArrayMap<>();

    /**
     * 注册
     */
    public void register(Object subject) {
        if (subject == null) {
            throw new NullPointerException("subject is null");
        }
        List<ObjMethod> objMethods = findObjMethodList(subject);
        if (objMethods.size() > 0) {
            registerMethod(subject, objMethods);
        }
    }

    /**
     * 注册方法
     */
    private void registerMethod(Object subject, List<ObjMethod> methods) {
        for (ObjMethod method : methods) {
            List<ClassMethod> mClassMethods;
            if (mClassMethodMap.containsKey(method.getMethodType())) {
                mClassMethods = mClassMethodMap.get(method.getMethodType());
                checkClassMethods(subject, mClassMethods);
                mClassMethodMap.remove(method.getMethodType());
            } else {
                mClassMethods = new ArrayList<>();
            }
            mClassMethods.add(new ClassMethod(method.getMethodName(), subject));
            mClassMethodMap.put(method.getMethodType(), mClassMethods);
        }
    }

    /**
     * 检查规则
     */
    private void checkClassMethods(Object subject, List<ClassMethod> classMethods) {
        for (ClassMethod classMethod : classMethods) {
            if (classMethod.getCls().getClass() == subject.getClass()) {
                throw new MethodException(classMethod.getMethodName() + " params it's only create one time in " + classMethod.getCls());
            }
        }
    }

    /**
     * 查找方法
     */
    private List<ObjMethod> findObjMethodList(Object subject) {
        if (mObjMethodMap.containsKey(subject.getClass())) {
            return mObjMethodMap.get(subject.getClass());
        }
        List<ObjMethod> objMethods = createObjMethods(subject);
        mObjMethodMap.put(subject.getClass(), objMethods);
        return objMethods;
    }

    /**
     * 查找所有方法
     */
    private List<ObjMethod> createObjMethods(Object subject) {
        List<ObjMethod> objMethods = new ArrayList<>();
        Method[] mMethods = subject.getClass().getMethods();
        if (mMethods == null) {
            return objMethods;
        }
        for (Method method : mMethods) {
            EventThread eventThread = method.getAnnotation(EventThread.class);
            if (eventThread == null) {
                continue;
            }
            Class<?>[] cls = method.getParameterTypes();
            if (cls.length > 1) {
                throw new IllegalArgumentException(method.getName() + " parameter is more than one");
            }
            objMethods.add(new ObjMethod(method.getName(), cls[0]));
        }
        return objMethods;
    }

    /**
     * 取消注册
     */
    public void unRegister(Object subject) {
        if (!mObjMethodMap.containsKey(subject.getClass())) {
            return;
        }
        List<ObjMethod> mObjMethods = mObjMethodMap.get(subject.getClass());
        for (ObjMethod objMethod : mObjMethods) {
            if (!mClassMethodMap.containsKey(objMethod.getMethodType())) {
                continue;
            }
            List<ClassMethod> mClassMethods = mClassMethodMap.get(objMethod.getMethodType());
            mClassMethodMap.remove(objMethod.getMethodType());
            List<ClassMethod> mRemoveClassMethods = new ArrayList<>();
            for (ClassMethod mMethods : mClassMethods) {
                if (mMethods.getCls().getClass() == subject.getClass()) {
                    mRemoveClassMethods.add(mMethods);
                }
            }
            for (ClassMethod mMethods : mRemoveClassMethods) {
                mClassMethods.remove(mMethods);
            }
            mClassMethodMap.put(objMethod.getMethodType(), mClassMethods);
        }
        mObjMethodMap.remove(subject.getClass());
    }

    /**
     * 发送消息体
     */
    @SuppressWarnings("unchecked")
    public void post(Object event) {
        if (mClassMethodMap.size() <= 0) {
            Log.d(TAG, "you should register first ");
            return;
        }
        try {
            if (mClassMethodMap.containsKey(event.getClass())) {
                List<ClassMethod> mClassMethods = mClassMethodMap.get(event.getClass());
                for (ClassMethod methodCls : mClassMethods) {
                    Class cls = methodCls.getCls().getClass();
                    Method method = cls.getMethod(methodCls.getMethodName(), event.getClass());
                    method.invoke(cls.newInstance(), event);
                }
            } else {
                Log.d(TAG, "you should add " + event.getClass());
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
