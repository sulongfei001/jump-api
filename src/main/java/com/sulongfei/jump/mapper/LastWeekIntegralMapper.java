package com.sulongfei.jump.mapper;

import com.sulongfei.jump.model.LastWeekIntegral;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LastWeekIntegralMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LastWeekIntegral record);

    int insertSelective(LastWeekIntegral record);

    LastWeekIntegral selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LastWeekIntegral record);

    int updateByPrimaryKey(LastWeekIntegral record);

    int batchInsert(List<LastWeekIntegral> integrals);

    int deleteAll();

    List<LastWeekIntegral> selectByClubId(@Param("remoteClubId") Long remoteClubId);
}