package com.changhong.sei.auth.dao;

import com.changhong.sei.auth.dto.ChannelEnum;
import com.changhong.sei.auth.entity.Account;
import com.changhong.sei.core.dao.BaseEntityDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * 实现功能：账户实体数据访问接口
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-01-14 13:47
 */
@Repository
public interface AccountDao extends BaseEntityDao<Account> {

    /**
     * 更新密码
     *
     * @param id                 账户id
     * @param password           账户新密码
     * @param passwordExpireTime 密码过期时间
     * @return 更新结果
     */
    @Modifying
    @Query("update Account a set a.password = :password, a.passwordExpireTime = :passwordExpireTime where a.id = :id")
    int updatePassword(@Param("id") String id, @Param("password") String password, @Param("passwordExpireTime") LocalDate passwordExpireTime);

    /**
     * 更新账户冻结状态
     *
     * @param id     账户id
     * @param frozen 冻结状态
     * @return 更新结果
     */
    @Modifying
    @Query("update Account a set a.frozen = :frozen where a.id = :id")
    int updateFrozen(@Param("id") String id, @Param("frozen") boolean frozen);

    /**
     * 更新账户锁定状态
     *
     * @param id     账户id
     * @param locked 锁定状态
     * @return 更新结果
     */
    @Modifying
    @Query("update Account a set a.locked = :locked where a.id = :id")
    int updateLocked(@Param("id") String id, @Param("locked") boolean locked);

    /**
     * 根据账号,租户代码查询账户
     *
     * @param account 账号
     * @param channel 账号渠道
     * @return 存在返回账号, 不存在返回null
     */
    List<Account> findByOpenIdAndChannel(String account, ChannelEnum channel);

    /**
     * 根据用户id查询账户
     *
     * @param userId 用户id
     * @return 存在返回账号, 不存在返回null
     */
    List<Account> findByUserId(String userId);

    /**
     * 根据账号,租户代码查询账户
     *
     * @param account 账号
     * @param channel 账号渠道
     * @param tenant  租户代码
     * @return 存在返回账号, 不存在返回null
     */
    Account findByOpenIdAndChannelAndTenantCode(String account, ChannelEnum channel, String tenant);
}
