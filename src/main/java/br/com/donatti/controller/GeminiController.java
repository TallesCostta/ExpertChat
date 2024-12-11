package br.com.donatti.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.donatti.model.dto.PromptRequestDTO;
import br.com.donatti.service.GeminiService;
import br.com.donatti.utils.LoggerUtil;

/**
 * 
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 16:46:47
 */
@RestController
@RequestMapping("expertchat/gemini")
public class GeminiController
{
    private static final Logger log = LoggerFactory.getLogger(GeminiController.class);
    
    @Autowired
    private GeminiService geminiService;

    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 07/12/2024 - 16:50:05
     * @param prompt
     * @return
     */
    @PostMapping("/enviar/prompt")
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
