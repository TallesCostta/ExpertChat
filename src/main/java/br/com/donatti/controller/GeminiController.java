package br.com.donatti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.donatti.model.dto.PromptRequestDTO;
import br.com.donatti.service.GeminiService;
import br.com.donatti.utils.LoggerUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 16:46:47
 */
@Slf4j
@RestController
@RequestMapping("expertchat/gemini")
public class GeminiController
{   
    @Autowired
    private GeminiService geminiService;

    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 16:50:05
     * @param prompt
     * @return
     */
    @PostMapping("/enviar/prompt")
    @PreAuthorize("hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<String> enviarPrompt(@RequestBody final PromptRequestDTO promptRequestDTO)
    {
        try
        {
            return ResponseEntity.ok(geminiService.enviarRequisicao(promptRequestDTO));
        }
        catch (IllegalArgumentException e)
        {
            log.error(LoggerUtil.getLinhaSaidaLogException(e), e);
            return ResponseEntity.badRequest().body("Requisição inválida: " + e.getMessage());
        }
        catch (Exception e)
        {
            log.error(LoggerUtil.getLinhaSaidaLogException(e), e);
            return ResponseEntity.status(500).body("Ocorreu um erro interno ao processar a requisição.");
        }
    }

}
