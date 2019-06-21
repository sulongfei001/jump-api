package com.sulongfei.jump.mapper;

import com.sulongfei.jump.model.PaymentOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PaymentOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PaymentOrder record);

    int insertSelective(PaymentOrder record);

    PaymentOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PaymentOrder record);

    int updateByPrimaryKey(PaymentOrder record);

    PaymentOrder selectByOrderId(@Param("swOrderId") Long swOrderId);

    List<PaymentOrder> selectAll(@Param("userId") Long userId, @Param("remoteClubId") Long remoteClubId);
}