package br.com.donatti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.donatti.model.BtpPerfilAcesso;
import br.com.donatti.repository.PerfilAcessoRepository;
import br.com.donatti.utils.LoggerUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 01:35:35
 */
@Slf4j
@RestController
@RequestMapping("/expertchat/perfil-acesso")
public class PerfilAcessoController
{
    @Autowired
    private PerfilAcessoRepository perfilAcessoRepository;
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 01:40:00
     * @param btpPerfilAcesso
     * @return
     */
    @PostMapping("/cadastro")
    public ResponseEntity<String> salvarCadastroPerfilAcesso(@RequestBody BtpPerfilAcesso btpPerfilAcesso)
    {
        try
        {            
            perfilAcessoRepository.salvarPerfilAcesso(btpPerfilAcesso);
            
            return ResponseEntity.ok("Perfil de acesso cadastrado com sucesso!");
        }
        catch (IllegalArgumentException e)
        {
            log.error(LoggerUtil.getLinhaSaidaLogException(e), e);
            return ResponseEntity.badRequest().body("Requisição inválida: " + e.getMessage());
        }
        catch (Exception e)
        {
            log.error(LoggerUtil.getLinhaSaidaLogException(e), e);
            return ResponseEntity.status(500).body("Ocorreu um erro interno ao cadastrar o perfil de acesso.");
        }   
    }
    
}
