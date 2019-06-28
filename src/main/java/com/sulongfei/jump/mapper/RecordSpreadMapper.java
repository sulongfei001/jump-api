package com.sulongfei.jump.mapper;

import com.sulongfei.jump.model.RecordSpread;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RecordSpreadMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RecordSpread record);

    int insertSelective(RecordSpread record);

    RecordSpread selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecordSpread record);

    int updateByPrimaryKey(RecordSpread record);

    Long mostTimesUser(@Param("roomId") Long roomId);
}