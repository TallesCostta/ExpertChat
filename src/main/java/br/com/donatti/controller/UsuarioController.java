package br.com.donatti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.donatti.enums.EnumResponseStatus;
import br.com.donatti.model.BtpUsuario;
import br.com.donatti.model.dto.ResponseEntityPadrao;
import br.com.donatti.service.UsuarioService;
import br.com.donatti.utils.LoggerUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 00:42:22
 */
@Slf4j
@RestController
@RequestMapping("expertchat/")
public class UsuarioController
{   
    @Autowired
    private UsuarioService usuarioService;
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 01:14:06
     * @param btpUsuario
     * @return
     */
    @PostMapping("usuario/cadastro")
    public ResponseEntity<ResponseEntityPadrao> salvarCadastroUsuario(@RequestBody final BtpUsuario btpUsuario)
    {
        try
        {   
            return ResponseEntity.ok(new ResponseEntityPadrao(EnumResponseStatus.OK.getCodigoHttp(),
                    EnumResponseStatus.OK.getDescricao(), usuarioService.salvarUsuario(btpUsuario), "Usuário cadastrado com sucesso!"));
        }
        catch (IllegalArgumentException e)
        {
            log.error(LoggerUtil.getLinhaSaidaLogException(e), e);

            return ResponseEntity.badRequest()
                    .body(new ResponseEntityPadrao(EnumResponseStatus.BAD_REQUEST.getCodigoHttp(),
                            EnumResponseStatus.BAD_REQUEST.getDescricao(), null, "Não foi possível cadastrar o novo usuário. Dados fornecidos possuem inconsistência."));
        }
        catch (Exception e)
        {
            log.error(LoggerUtil.getLinhaSaidaLogException(e), e);

            return ResponseEntity.internalServerError()
                    .body(new ResponseEntityPadrao(EnumResponseStatus.INTERNAL_SERVER_ERROR.getCodigoHttp(),
                            EnumResponseStatus.INTERNAL_SERVER_ERROR.getDescricao(), null, "Ocorreu um erro ao cadastrar o usuário."));
        }
    }
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 23:09:28
     * @return
     */
    @GetMapping("usuarios/lista-todos")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<BtpUsuario>> listarTodosUsuarios()
    {
        try
        {
            return ResponseEntity.ok(usuarioService.consultarTodosUsuarios());
        }
        catch (IllegalArgumentException e)
        {
            log.error(LoggerUtil.getLinhaSaidaLogException(e), e);
        }
        catch (Exception e)
        {
            log.error(LoggerUtil.getLinhaSaidaLogException(e), e);
        }
        
        return null;   
    }
    
}
