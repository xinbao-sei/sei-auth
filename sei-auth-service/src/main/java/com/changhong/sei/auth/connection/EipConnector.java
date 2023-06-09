package com.changhong.sei.auth.connection;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.changhong.sei.auth.dto.EipMailDto;
import com.changhong.sei.auth.dto.FindEipToDoListDto;
import com.changhong.sei.auth.util.DateUtils;
import com.changhong.sei.auth.webservice.eipMall.*;
import com.changhong.sei.auth.webservice.finEipTodoList.DONLIMEIPQUERYTODOLISTSYNC648;
import com.changhong.sei.auth.webservice.finEipTodoList.DONLIMEIPQUERYTODOLISTSYNC648_Service;
import com.changhong.sei.auth.webservice.finEipTodoList.SvcHdrsType;

import javax.xml.ws.Holder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ServiceConfigurationError;

/**
 * 对接eip测试待办接口
 *
 * @author Joe
 * @date 2022/4/26
 */
public class EipConnector {

    public static final SvcHdrType svcHdr = new SvcHdrType();
    public static final AppHdrType appHdr = new AppHdrType();
    public static final AppBodyType appBody = new AppBodyType();
    public static final Holder<SvcHdrTypes> svcHdrs = new Holder<>();
    public static final Holder<AppHdrTypes> appHdrs = new Holder<>();
    public static final Holder<AppBodyTypes> appBodys = new Holder<>();
    public static final DONLIMESAGENCYNOTICEINFOSYNC086_Service service = new DONLIMESAGENCYNOTICEINFOSYNC086_Service();
    public static final DONLIMESAGENCYNOTICEINFOSYNC086 sync = service.getDONLIMESAGENCYNOTICEINFOSYNC086SOAP();
    public static final AddNoticeType notice = new AddNoticeType();
    public static final String sourceId = "SEIPROD";
    public static final String destinationId = "EIP";
    public static final String ipAddress = "127.0.0.1";
    public static final String bo = "待办通知信息同步";
    public static final String systemName = "sei平台";
    public static final String systemSort = "A13";
    public static final String mailType = "待办";

    /*public static final DONLIMEIPQUERYTODOLISTSYNC648_Service findService = new DONLIMEIPQUERYTODOLISTSYNC648_Service();
    public static final DONLIMEIPQUERYTODOLISTSYNC648 findSync = findService.getDONLIMEIPQUERYTODOLISTSYNC648SOAP();
    private static final com.changhong.sei.auth.webservice.finEipTodoList.SvcHdrType search=new com.changhong.sei.auth.webservice.finEipTodoList.SvcHdrType();

    *//**
     * 获取EIP待办清单
     * @return
     *//*
    public static List<FindEipToDoListDto.ToDoListDTO> findTodoList() {
        search.setBO(bo);
        search.setSOURCEID(sourceId);
        search.setIPADDRESS(ipAddress);
        search.setDESTINATIONID(destinationId);
        search.setTYPE("SELECT");
        search.setBodyJson("{\"systemName\":\"sei平台\"}");
        SvcHdrsType svcHdrsType = findSync.donlimEIPQUERYTODOLISTSYNC648(search);
        JSONObject result = JSONObject.parseObject(svcHdrsType.getResultJson());
        FindEipToDoListDto dto = JSON.toJavaObject(result,FindEipToDoListDto.class);
        return dto.getTable();
    }*/

    /**
     * 增加待办
     * @param eipMailDto
     * @return
     */
    public static boolean addEipMall(EipMailDto eipMailDto) {
        svcHdr.setSOURCEID(sourceId);
        svcHdr.setDESTINATIONID(destinationId);
        svcHdr.setTYPE("ADD");
        svcHdr.setBO(bo);
        svcHdr.setIPADDRESS(ipAddress);
        notice.setAccount(eipMailDto.getAccount());
        notice.setMailBody(eipMailDto.getMailBody());
        notice.setMailID(eipMailDto.getMailID());
        notice.setMailSubject(eipMailDto.getMailSubject());
        notice.setMailType(mailType);
        notice.setSystemName(systemName);
        notice.setSystemSort(systemSort);
        notice.setUrl(eipMailDto.getUrl());
        appBody.setAddNotice(notice);
        sync.donlimESAGENCYNOTICEINFOSYNC086(svcHdr, appHdr, appBody, svcHdrs, appHdrs, appBodys);
        if ("Y".equals(svcHdrs.value.getRCODE())) {
            return true;
        }
        return false;
    }

    /**
     * 清除待办
     * @param mailId
     * @return
     */
    public static boolean deleteEipMall(String mailId) {
        svcHdr.setSOURCEID(sourceId);
        svcHdr.setDESTINATIONID(destinationId);
        svcHdr.setTYPE("DELETE");
        svcHdr.setBO(bo);
        svcHdr.setIPADDRESS(ipAddress);
        notice.setMailID(mailId);
        notice.setMailType(mailType);
        notice.setSystemName(systemName);
        notice.setSystemSort(systemSort);
        appBody.setAddNotice(notice);
        sync.donlimESAGENCYNOTICEINFOSYNC086(svcHdr, appHdr, appBody, svcHdrs, appHdrs, appBodys);
        if ("Y".equals(svcHdrs.value.getRCODE())) {
            return true;
        }
        return false;
    }

    public static boolean updateEipMall(EipMailDto eipMailDto) {
        svcHdr.setSOURCEID(sourceId);
        svcHdr.setDESTINATIONID(destinationId);
        svcHdr.setTYPE("DELETE");
        svcHdr.setBO(bo);
        svcHdr.setIPADDRESS(ipAddress);
        notice.setAccount(eipMailDto.getAccount());
        notice.setMailBody(eipMailDto.getMailBody());
        notice.setMailID(eipMailDto.getMailID());
        notice.setMailSubject(eipMailDto.getMailSubject());
        notice.setMailType(mailType);
        notice.setSystemName(systemName);
        notice.setSystemSort(systemSort);
        notice.setUrl(eipMailDto.getUrl());
        appBody.setAddNotice(notice);
        sync.donlimESAGENCYNOTICEINFOSYNC086(svcHdr, appHdr, appBody, svcHdrs, appHdrs, appBodys);
        if ("Y".equals(svcHdrs.value.getRCODE())) {
            return true;
        }
        return false;
    }
}

