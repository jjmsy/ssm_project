package cn.indi.dao;

import cn.indi.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IMemberDao {
    @Select("select * from member where id= #{id}")
    Member findById(int id) throws Exception;
}
