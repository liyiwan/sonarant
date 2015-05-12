package com.yizi.iwuse.comm.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.yizi.iwuse.comm.service.MsgInterface;
import com.yizi.iwuse.comm.service.MsgInterface.CmdInterface;
import com.yizi.iwuse.utils.ILog;

/**
 * 进行命令或邮箱消息处理的handler工厂
 * 
 */
public class HandlerFactory
{
    /** The logging tag used by this class with ILog. */
    protected static final String TAG = "HandlerFactory";

    /** 对Handler缓存 */
    public Map<CmdInterface, AbsCmdMsgHandler> handlerMap = new HashMap<CmdInterface, AbsCmdMsgHandler>();

    private static volatile HandlerFactory instance = null;

    private HandlerFactory()
    {
        ILog.d(TAG, "init the HandlerFactory......");
        // 初始化时，注册命令处理器
        registerCmdMsgHandler();
    }

    /**
     * 获取HandlerFactory的单例
     * @return HandlerFactory的实例
     */
    public static HandlerFactory getInstance()
    {
        if (instance == null)
        {
            synchronized (HandlerFactory.class)
            {
                if (instance == null)
                {
                    instance = new HandlerFactory();
                }
            }
        }

        return instance;
    }

    /**
     * 获取所有的订阅的数据
     * @return 订阅数据的数组
     */
    public List<String> getSubscribeMailData()
    {
        List<String> mailData = new ArrayList<String>();
        Iterator<Entry<CmdInterface, AbsCmdMsgHandler>> iters = handlerMap.entrySet().iterator();
        while (iters.hasNext())
        {
            Map.Entry<CmdInterface, AbsCmdMsgHandler> entry = (Map.Entry<CmdInterface, AbsCmdMsgHandler>) iters.next();
            AbsCmdMsgHandler handler = entry.getValue();
            List<String> items = handler.getSubscribeMailData();
            if (null != items && items.size() > 0)
            {
                mailData.addAll(items);
            }
        }
        return mailData;
    }

    /**
     * 获取所有的订阅的邮箱消息
     * @return 订阅数据的数组
     */
    public List<String> getSubscribeMailMsg()
    {
        List<String> mailMsgs = new ArrayList<String>();
        Iterator<Entry<CmdInterface, AbsCmdMsgHandler>> iters = handlerMap.entrySet().iterator();
        while (iters.hasNext())
        {
            Map.Entry<CmdInterface, AbsCmdMsgHandler> entry = (Map.Entry<CmdInterface, AbsCmdMsgHandler>) iters.next();
            AbsCmdMsgHandler handler = entry.getValue();
            List<String> items = handler.getSubscribeMailData();
            if (null != items && items.size() > 0)
            {
                mailMsgs.addAll(items);
            }
        }
        return mailMsgs;
    }

    /**
     * 获取对应的消息id的处理器
     * 
     * @param msgId 中控主机的邮箱消息id
     * @return 能够处理该邮箱消息的处理器
     */
    public AbsCmdMsgHandler getHostCmdMsgHandlerByMailId(int msgId)
    {
        switch (msgId)
        {
        
        }
        return null;
    }

    /**
     * 获取对应的消息id的处理器
     * @param msgId 终端邮箱消息id
     * @return 能够处理该邮箱消息的处理器
     */
    public AbsCmdMsgHandler getVCTCmdMsgHandlerByMailId(String msgId)
    {
        
        return null;
    }

    /**
     * 获取命令对应的消息处理器
     * 
     * @param cmd 发送的抽象的命令
     * @return 能够处理该命令的消息处理器
     */
    public AbsCmdMsgHandler getCmdMsgHandlerByCmd(CmdInterface cmd)
    {
        switch (cmd)
        {
            case GetMail_BoxData:
                return handlerMap.get(CmdInterface.GetMail_BoxData);
        }
        return null;
    }

    /**
     * 注册消息处理器，该消息处理器用来生产发送命令的参数，解析邮箱消息的字段
     */
    public void registerCmdMsgHandler()
    {
        // 每次注册之前清空当前的handler的注册信息
        handlerMap.clear();

        // TODO 对session的处理
        // 开始注册handler
        // 设置与显示类
//        handlerMap.put(CmdInterface.SET_GetConferCodeInfo, new HostSettingCmdMsgHandler());
    }

    /**
     * 销毁该工厂
     */
    public static void destory()
    {
        instance = null;
    }
}
