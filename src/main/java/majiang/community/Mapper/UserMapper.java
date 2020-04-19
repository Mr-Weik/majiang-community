package majiang.community.Mapper;

import majiang.community.model.User;
import org.apache.ibatis.annotations.Insert;

public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_modified) values (#{name},#{account_id},#{token},#{gmt_modified})")
     void insert(User user);
}
