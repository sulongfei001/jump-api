package com.sulongfei.jump.mapper;

import com.sulongfei.jump.model.RankPrize;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RankPrizeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RankPrize record);

    int insertSelective(RankPrize record);

    RankPrize selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RankPrize record);

    int updateByPrimaryKey(RankPrize record);

    List<RankPrize> selectByClubId(@Param("remoteClubId") Long remoteClubId);
}