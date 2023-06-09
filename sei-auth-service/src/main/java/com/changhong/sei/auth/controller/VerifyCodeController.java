package com.changhong.sei.auth.controller;

import com.changhong.sei.auth.api.VerifyCodeApi;
import com.changhong.sei.auth.dto.ChannelEnum;
import com.changhong.sei.auth.service.ValidateCodeService;
import com.changhong.sei.core.context.ContextUtil;
import com.changhong.sei.core.dto.ResultData;
import com.changhong.sei.core.log.annotation.AccessLog;
import com.changhong.sei.util.EnumUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-09-04 13:25
 */
@RestController
@AccessLog(AccessLog.FilterReply.DENY)
@Api(value = "VerifyCodeApi", tags = "验证码服务")
@RequestMapping(path = VerifyCodeApi.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class VerifyCodeController implements VerifyCodeApi {

    @Autowired
    private ValidateCodeService validateCodeService;

    /**
     * 验证码
     *
     * @param reqId 请求id
     * @return 返回验证码
     */
    @Override
    public ResultData<String> generate(@NotBlank String reqId) {
        return validateCodeService.generate(reqId);
    }

    /**
     * 验证码
     *
     * @param reqId   请求id
     * @param channel 通道
     * @return 返回验证码
     */
    @Override
    public ResultData<String> sendVerifyCode(String reqId, String channel, String operation) {
        ChannelEnum channelEnum = EnumUtils.getEnum(ChannelEnum.class, channel);
        if (Objects.nonNull(channelEnum)) {
            return validateCodeService.sendVerifyCode(reqId, reqId, ContextUtil.getUserName(), channelEnum, operation);
        } else {
            return ResultData.fail(ContextUtil.getMessage("verify_code_0001", channel));
        }
    }

    /**
     * 验证码
     *
     * @param reqId 请求id
     * @param code  校验值
     * @return 返回验证码
     */
    @Override
    public ResultData<String> check(@NotBlank String reqId, @NotBlank String code) {
        return validateCodeService.check(reqId, code);
    }
}
