package br.com.donatti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.donatti.enums.EnumResponseStatus;
import br.com.donatti.model.BtpPerfilAcesso;
import br.com.donatti.model.dto.ResponseEntityPadrao;
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
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ResponseEntityPadrao> salvarCadastroPerfilAcesso(
            @RequestBody final BtpPerfilAcesso btpPerfilAcesso)
    {
        try
        {
            return ResponseEntity.ok(new ResponseEntityPadrao(EnumResponseStatus.OK.getCodigoHttp(),
                    EnumResponseStatus.OK.getDescricao(), perfilAcessoRepository.salvarPerfilAcesso(btpPerfilAcesso), "Perfil de acesso cadastrado com sucesso!"));
        }
        catch (IllegalArgumentException e)
        {
            log.error(LoggerUtil.getLinhaSaidaLogException(e), e);

            return ResponseEntity.badRequest()
                    .body(new ResponseEntityPadrao(EnumResponseStatus.BAD_REQUEST.getCodigoHttp(),
                            EnumResponseStatus.BAD_REQUEST.getDescricao(), null, "Não foi possível salvar o perfil de acesso. Dados fornecidos possuem inconsistência."));
        }
        catch (Exception e)
        {
            log.error(LoggerUtil.getLinhaSaidaLogException(e), e);

            return ResponseEntity.internalServerError()
                    .body(new ResponseEntityPadrao(EnumResponseStatus.INTERNAL_SERVER_ERROR.getCodigoHttp(),
                            EnumResponseStatus.INTERNAL_SERVER_ERROR.getDescricao(), null, "Ocorreu um erro ao salvar o perfil de acesso."));
        }
    }

    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 13/12/2024 - 00:12:50
     * @return
     */
    @GetMapping("/lista-todos")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ResponseEntityPadrao> consultarTodosPerfisAcesso()
    {
        try
        {
            return ResponseEntity.ok(new ResponseEntityPadrao(EnumResponseStatus.OK.getCodigoHttp(),
                    EnumResponseStatus.OK.getDescricao(), perfilAcessoRepository.consultarTodos(BtpPerfilAcesso.class), ""));
        }
        catch (Exception e)
        {
            log.error(LoggerUtil.getLinhaSaidaLogException(e), e);

            return ResponseEntity.internalServerError()
                    .body(new ResponseEntityPadrao(EnumResponseStatus.INTERNAL_SERVER_ERROR.getCodigoHttp(),
                            EnumResponseStatus.INTERNAL_SERVER_ERROR.getDescricao(), null, "Ocorreu um erro ao consultar todos os perfis de acesso."));
        }
    }

}
