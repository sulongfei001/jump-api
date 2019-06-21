package com.sulongfei.jump.mapper;

import com.sulongfei.jump.model.SendGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SendGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SendGoods record);

    int insertSelective(SendGoods record);

    SendGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SendGoods record);

    int updateByPrimaryKey(SendGoods record);

    List<SendGoods> selectByStatus(@Param("memberId") Long memberId, @Param("remoteClubId") Long remoteClubId, @Param("status") Integer status);
}