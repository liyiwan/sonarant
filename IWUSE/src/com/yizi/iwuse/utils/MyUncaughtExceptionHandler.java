package com.yizi.iwuse.utils;

/**
 * 
 * 捕获异常信息类，处理一些没有处理的异常
 * @author wKF25648
 */
import java.lang.Thread.UncaughtExceptionHandler;

import com.yizi.iwuse.AppContext;


public class MyUncaughtExceptionHandler implements UncaughtExceptionHandler
{
    /** The logging tag used by this class with ILog. */
    protected static final String TAG = "MyUncaughtExceptionHandler";

    /**
     * 定义单例对象
     */
    private static MyUncaughtExceptionHandler myException;

    private MyUncaughtExceptionHandler()
    {
    }

    /**
     * 处理异常
     */
    @Override
    public void uncaughtException(Thread arg0, Throwable arg1)
    {
        ILog.e(TAG, "uncaughtException hid restart.");
        ILog.e(TAG, arg1);
        IWuseUtil.restartIWuse(AppContext.instance().globalContext);
    }

    public static MyUncaughtExceptionHandler getInstance()
    {
        if (myException == null)
        {
            return new MyUncaughtExceptionHandler();
        }
        else
        {
            return myException;
        }
    }

    /**
     * 清除全局信息。
     */
    public static void cleanResource()
    {
        myException = null;
    }

}
