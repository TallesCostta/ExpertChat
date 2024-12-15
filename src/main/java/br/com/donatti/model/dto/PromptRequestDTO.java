package br.com.donatti.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 17:09:58
 */
@Getter
@Setter
public class PromptRequestDTO
{
    private String idUsuario;
    
    private String prompt;

    private String categoria;

    private String especialidade;
}
