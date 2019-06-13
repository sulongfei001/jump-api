package com.sulongfei.jump.mapper;

import com.sulongfei.jump.model.Integral;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IntegralMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Integral record);

    int insertSelective(Integral record);

    Integral selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Integral record);

    int updateByPrimaryKey(Integral record);

    Integral selectByUserIdClubId(@Param("userId") Long userId, @Param("remoteClubId") Long remoteClubId);

    List<Integral> rankListTop(@Param("remoteClubId") Long remoteClubId, @Param("entryIntegral") Integer entryIntegral, @Param("entryNum") Integer entryNum);

    Integer findRankByIntegral(@Param("remoteClubId") Long remoteClubId, @Param("integral") Integer integral);

    void resetRankList();

    Integer findRankByUserId(@Param("remoteClubId") Long remoteClubId, @Param("userId") Long userId);
}