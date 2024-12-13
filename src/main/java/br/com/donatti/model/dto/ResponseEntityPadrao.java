package br.com.donatti.model.dto;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 12/12/2024 - 23:59:40
 */
public record ResponseEntityPadrao(Integer httpStatusCode, String httStatusDescribe, Object data, String message) {}
