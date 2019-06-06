package com.sulongfei.jump.mapper;

import com.sulongfei.jump.model.SpreadGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SpreadGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SpreadGoods record);

    int insertSelective(SpreadGoods record);

    SpreadGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SpreadGoods record);

    int updateByPrimaryKey(SpreadGoods record);

    List<SpreadGoods> selectByClubId(@Param("remoteClubId") Long remoteClubId);
}