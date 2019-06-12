package com.sulongfei.jump.mapper;

import com.sulongfei.jump.model.RecordSimple;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RecordSimpleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RecordSimple record);

    int insertSelective(RecordSimple record);

    RecordSimple selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecordSimple record);

    int updateByPrimaryKey(RecordSimple record);

    RecordSimple randomResult(@Param("remoteClubId") Long remoteClubId, @Param("userId") Long userId);

    Integer countPrize(@Param("roomId") Long roomId);
}