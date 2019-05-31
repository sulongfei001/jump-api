package com.sulongfei.jump.mapper;

import com.sulongfei.jump.model.Record;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Record record);

    int insertSelective(Record record);

    Record selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Record record);

    int updateByPrimaryKey(Record record);
}