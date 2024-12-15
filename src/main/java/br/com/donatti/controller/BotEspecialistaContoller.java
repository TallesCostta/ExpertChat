package br.com.donatti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.donatti.enums.EnumResponseStatus;
import br.com.donatti.model.BtpBotEspecialista;
import br.com.donatti.model.dto.ResponseEntityPadrao;
import br.com.donatti.service.BotEspecialistaService;
import br.com.donatti.utils.LoggerUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 14/12/2024 - 12:40:20
 */
@Slf4j
@RestController
@RequestMapping("expertchat/")
public class BotEspecialistaContoller
{
 
    @Autowired
    private BotEspecialistaService botEspecialistaService;
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 14/12/2024 - 12:48:38
     * @param idUsuario
     * @return
     */
    @GetMapping("bots/usuario")
    @PreAuthorize("hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<ResponseEntityPadrao> listarBotEspecialistaPorUsuario(@RequestParam String idUsuario)
    {
        try
        {
            final List<BtpBotEspecialista> lstBotEspecialista = botEspecialistaService.consultarTodosBotsEspecialistaPorUsuario(idUsuario);
            
            return ResponseEntity.ok(new ResponseEntityPadrao(EnumResponseStatus.OK.getCodigoHttp(), EnumResponseStatus.OK.getDescricao(), lstBotEspecialista, ""));        
        }
        catch (Exception e)
        {
            log.error(LoggerUtil.getLinhaSaidaLogException(e), e);

            return ResponseEntity.internalServerError()
                    .body(new ResponseEntityPadrao(EnumResponseStatus.INTERNAL_SERVER_ERROR.getCodigoHttp(),
                            EnumResponseStatus.INTERNAL_SERVER_ERROR.getDescricao(), null, "Ocorreu um erro ao consultar os bots do usuário."));
        }
    }
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 14/12/2024 - 13:15:29
     * @param btpBotEspecialista
     * @return
     */
    @PostMapping("bots/cadastro")
    @PreAuthorize("hasAuthority('SCOPE_BASIC')")
    public ResponseEntity<ResponseEntityPadrao> cadastrarNovoBot(@RequestBody BtpBotEspecialista btpBotEspecialista)
    {
        try
        {
            return ResponseEntity.ok(new ResponseEntityPadrao(EnumResponseStatus.OK.getCodigoHttp(),
                    EnumResponseStatus.OK.getDescricao(), botEspecialistaService.cadastrarNovoBot(btpBotEspecialista),
                    "Bot cadastrado com sucesso!"));
        }
        catch (Exception e)
        {
            log.error(LoggerUtil.getLinhaSaidaLogException(e), e);

            return ResponseEntity.internalServerError()
                    .body(new ResponseEntityPadrao(EnumResponseStatus.INTERNAL_SERVER_ERROR.getCodigoHttp(),
                            EnumResponseStatus.INTERNAL_SERVER_ERROR.getDescricao(), null, "Ocorreu um erro ao cadastrar um novo bot."));
        }
    }
    
}
