package com.changhong.sei.auth.service.mq;

import com.changhong.sei.auth.entity.AccessRecord;
import com.changhong.sei.auth.service.AccessRecordService;
import com.changhong.sei.core.util.JsonUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;

/**
 * 实现功能：访问日志消费者
 * 访问日志在网关产生并推送到kafka中,在auth进行消费
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2021-01-14 09:32
 */
@Component
public class AccessRecordConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(AccessRecordConsumer.class);

    @Autowired
    private AccessRecordService service;

    @KafkaListener(topics = "${sei.topic.logger.access:SeiAccessLog}")
    public void processMessage(ConsumerRecord<String, String> record) {
        if (Objects.isNull(record)) {
            return;
        }

        String value = record.value();
        if (LOG.isDebugEnabled()) {
            LOG.debug("received key='{}' message = '{}'", record.key(), value);
        }
        Optional<String> optional = Optional.ofNullable(value);
        if (optional.isPresent()) {
            // 执行业务处理逻辑
            try {
                AccessLogVo logVo = JsonUtils.fromJson(value, AccessLogVo.class);
                if (Objects.nonNull(logVo)) {
                    //解析agent字符串
                    UserAgent userAgent = UserAgent.parseUserAgentString(logVo.getUserAgent());

                    AccessRecord accessRecord = new AccessRecord();
                    accessRecord.setType("API");

                    accessRecord.setTenantCode(logVo.getTenantCode());
                    accessRecord.setUserId(logVo.getUserId());
                    accessRecord.setUserAccount(StringUtils.isBlank(logVo.getUserAccount()) ? "anonymous" : logVo.getUserAccount());
                    accessRecord.setUserName(logVo.getUserName());
                    accessRecord.setAppModule(logVo.getAppModule());
                    accessRecord.setFeatureCode(logVo.getFeatureCode());
                    accessRecord.setFeature(logVo.getFeature());
                    accessRecord.setTraceId(logVo.getTraceId());
                    accessRecord.setPath(logVo.getPath());
                    accessRecord.setUrl(logVo.getUrl());
                    accessRecord.setMethod(logVo.getMethod());
                    accessRecord.setStatusCode(logVo.getStatusCode());
                    accessRecord.setDuration(logVo.getDuration());
                    accessRecord.setIp(logVo.getIp());
                    accessRecord.setBrowser(userAgent.getBrowser().getName());
                    accessRecord.setOsName(userAgent.getOperatingSystem().getName());
                    accessRecord.setAccessTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(logVo.getAccessTime()), ZoneId.systemDefault()));

                    service.addRecord(accessRecord);
                }
            } catch (Exception e) {
                LOG.error("访问日志消费异常!", e);
            }
        }
    }
}
