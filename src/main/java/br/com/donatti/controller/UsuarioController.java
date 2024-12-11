package br.com.donatti.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.donatti.model.BtpUsuario;
import br.com.donatti.repository.UsuarioRepository;
import br.com.donatti.service.UsuarioService;
import br.com.donatti.utils.LoggerUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 00:42:22
 */
@Slf4j
@RestController
@RequestMapping("expertchat/usuario")
public class UsuarioController
{
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private UsuarioService usuarioService;
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 01:14:06
     * @param btpUsuario
     * @return
     */
    @PostMapping("/cadastro")
    public ResponseEntity<String> salvarCadastroUsuario(@RequestBody BtpUsuario btpUsuario)
    {
        try
        {  
            Map<BtpUsuario, Map<String, Object>> mapParam = usuarioService.mapperUsuarioCadastro(btpUsuario);
            
            BtpUsuario btpUsuarioPersistir = null;
            
            Map<String, Object> mapPerfilAcesso = null;
            
            for (Map.Entry<BtpUsuario, Map<String, Object>> entidade : mapParam.entrySet())
            {
                btpUsuarioPersistir = entidade.getKey();
                
                mapPerfilAcesso = entidade.getValue();
            }
            
            usuarioRepository.salvarUsuario(btpUsuarioPersistir, mapPerfilAcesso);
            
            return ResponseEntity.ok("Usuário cadastrado com sucesso!");
        }
        catch (IllegalArgumentException e)
        {
            log.error(LoggerUtil.getLinhaSaidaLogException(e), e);
            return ResponseEntity.badRequest().body("Requisição inválida: " + e.getMessage());
        }
        catch (Exception e)
        {
            log.error(LoggerUtil.getLinhaSaidaLogException(e), e);
            return ResponseEntity.status(500).body("Ocorreu um erro interno ao cadastrar o usuário.");
        }   
    }
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 23:09:28
     * @return
     */
    @GetMapping("/lista-todos")
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
