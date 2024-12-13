package br.com.donatti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.donatti.enums.EnumResponseStatus;
import br.com.donatti.model.dto.LoginRequest;
import br.com.donatti.model.dto.ResponseEntityPadrao;
import br.com.donatti.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 09:15:51
 */
@Slf4j
@RestController
@RequestMapping("expertchat")
public class TokenController
{
    @Autowired
    private UsuarioService usuarioService;

    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 09:21:30
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseEntityPadrao> logar(@RequestBody LoginRequest loginRequest)
    {
        try
        {
            return ResponseEntity.ok(new ResponseEntityPadrao(EnumResponseStatus.OK.getCodigoHttp(),
                    EnumResponseStatus.OK.getDescricao(), usuarioService.autenticarUsuario(loginRequest), "Usuário autenticado com sucesso!"));
        }
        catch (BadCredentialsException e)
        {
            return ResponseEntity.status(EnumResponseStatus.UNAUTHORIZED.getCodigoHttp())
                    .body(new ResponseEntityPadrao(EnumResponseStatus.UNAUTHORIZED.getCodigoHttp(), EnumResponseStatus.UNAUTHORIZED.getDescricao(), null, e.getMessage()));
        }
    }
}
