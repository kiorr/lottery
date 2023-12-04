package com.itheima.prize.commons.db.mapper;

import com.itheima.prize.commons.db.entity.CardGame;
import com.itheima.prize.commons.db.entity.CardGameExample;
import java.util.List;

import com.itheima.prize.commons.db.entity.CardProductDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CardGameMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table card_game
     *
     * @mbg.generated
     */
    long countByExample(CardGameExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table card_game
     *
     * @mbg.generated
     */
    int deleteByExample(CardGameExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table card_game
     *
     * @mbg.generated
     */
    @Delete({
        "delete from card_game",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table card_game
     *
     * @mbg.generated
     */
    @Insert({
        "insert into card_game (title, pic, ",
        "info, starttime, ",
        "endtime, type, ",
        "status)",
        "values (#{title,jdbcType=VARCHAR}, #{pic,jdbcType=VARCHAR}, ",
        "#{info,jdbcType=VARCHAR}, #{starttime,jdbcType=TIMESTAMP}, ",
        "#{endtime,jdbcType=TIMESTAMP}, #{type,jdbcType=TINYINT}, ",
        "#{status,jdbcType=TINYINT})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(CardGame record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table card_game
     *
     * @mbg.generated
     */
    int insertSelective(CardGame record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table card_game
     *
     * @mbg.generated
     */
    List<CardGame> selectByExample(CardGameExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table card_game
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id, title, pic, info, starttime, endtime, type, status",
        "from card_game",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.itheima.prize.commons.db.mapper.CardGameMapper.BaseResultMap")
    CardGame selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table card_game
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") CardGame record, @Param("example") CardGameExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table card_game
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") CardGame record, @Param("example") CardGameExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table card_game
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CardGame record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table card_game
     *
     * @mbg.generated
     */
    @Update({
        "update card_game",
        "set title = #{title,jdbcType=VARCHAR},",
          "pic = #{pic,jdbcType=VARCHAR},",
          "info = #{info,jdbcType=VARCHAR},",
          "starttime = #{starttime,jdbcType=TIMESTAMP},",
          "endtime = #{endtime,jdbcType=TIMESTAMP},",
          "type = #{type,jdbcType=TINYINT},",
          "status = #{status,jdbcType=TINYINT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(CardGame record);

    List<CardGame> selectAllGame(@Param("status") int status);

    List<CardProductDto> selectProductByGameid(@Param("gameid")  int gameid);
}
