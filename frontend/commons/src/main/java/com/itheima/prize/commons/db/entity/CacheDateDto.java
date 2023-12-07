package com.itheima.prize.commons.db.entity;

import java.util.List;
import java.util.Map;

/**
 * @author zzq
 * @version 1.0
 * @description 缓存信息的dto
 * @date 2023-12-07 16:44
 */
public class CacheDateDto {


   private CardGame game;


   private List<CardProductDto> cardProductDtos;

   private Map<Object, Object> gameMaxGoal;

   private Map<Object, Object> gameMaxEnter;


   public CacheDateDto() {

   }

   public CacheDateDto(CardGame game, List<CardProductDto> cardProductDtos, Map<Object, Object> gameMaxGoal, Map<Object, Object> gameMaxEnter) {
      this.game = game;
      this.cardProductDtos = cardProductDtos;
      this.gameMaxGoal = gameMaxGoal;
      this.gameMaxEnter = gameMaxEnter;
   }

   public CardGame getGame() {
      return game;
   }

   public void setGame(CardGame game) {
      this.game = game;
   }

   public List<CardProductDto> getCardProductDtos() {
      return cardProductDtos;
   }

   public void setCardProductDtos(List<CardProductDto> cardProductDtos) {
      this.cardProductDtos = cardProductDtos;
   }

   public Map<Object, Object> getGameMaxGoal() {
      return gameMaxGoal;
   }

   public void setGameMaxGoal(Map<Object, Object> gameMaxGoal) {
      this.gameMaxGoal = gameMaxGoal;
   }

   public Map<Object, Object> getGameMaxEnter() {
      return gameMaxEnter;
   }

   public void setGameMaxEnter(Map<Object, Object> gameMaxEnter) {
      this.gameMaxEnter = gameMaxEnter;
   }
}
