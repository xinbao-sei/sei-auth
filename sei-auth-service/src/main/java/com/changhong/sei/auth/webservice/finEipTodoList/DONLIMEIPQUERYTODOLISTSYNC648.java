
package com.changhong.sei.auth.webservice.finEipTodoList;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "DONLIM_EIP_QUERYTODOLISTSYNC_648", targetNamespace = "http://www.example.org/DONLIM_EIP_QUERYTODOLISTSYNC_648/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface DONLIMEIPQUERYTODOLISTSYNC648 {


    /**
     * 
     * @param svcHdr
     * @return
     *     returns com.changhong.sei.auth.webservice.finEipTodoList.SvcHdrsType
     */
    @WebMethod(operationName = "DONLIM_EIP_QUERYTODOLISTSYNC_648", action = "http://www.example.org/DONLIM_EIP_QUERYTODOLISTSYNC_648/DONLIM_EIP_QUERYTODOLISTSYNC_648")
    @WebResult(name = "SvcHdrs", targetNamespace = "")
    @RequestWrapper(localName = "DONLIM_EIP_QUERYTODOLISTSYNC_648", targetNamespace = "http://www.example.org/DONLIM_EIP_QUERYTODOLISTSYNC_648/", className = "com.changhong.sei.auth.webservice.finEipTodoList.DONLIMEIPQUERYTODOLISTSYNC648_Type")
    @ResponseWrapper(localName = "DONLIM_EIP_QUERYTODOLISTSYNC_648Response", targetNamespace = "http://www.example.org/DONLIM_EIP_QUERYTODOLISTSYNC_648/", className = "com.changhong.sei.auth.webservice.finEipTodoList.DONLIMEIPQUERYTODOLISTSYNC648Response")
    public SvcHdrsType donlimEIPQUERYTODOLISTSYNC648(
        @WebParam(name = "SvcHdr", targetNamespace = "")
        SvcHdrType svcHdr);

}