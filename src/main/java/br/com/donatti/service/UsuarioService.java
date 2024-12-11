package br.com.donatti.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import br.com.donatti.model.BtpPerfilAcesso;
import br.com.donatti.model.BtpUsuario;
import br.com.donatti.model.dto.LoginRequest;
import br.com.donatti.model.dto.LoginResponse;
import br.com.donatti.repository.UsuarioRepository;
import br.com.donatti.utils.CollectionUtil;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 09:37:39
 */
@Service
public class UsuarioService
{
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private final JwtEncoder jwtEncoder;

    public UsuarioService(JwtEncoder jwtEncoder)
    {
        this.jwtEncoder = jwtEncoder;
    }

    
    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 09:38:43
     * @param loginRequest
     * @return
     */
    public Boolean isCredenciaisValidas(final LoginRequest loginRequest, 
            final BtpUsuario btpUsuario, final PasswordEncoder passwordEncoder)
    {
        return passwordEncoder.matches(loginRequest.senha(), btpUsuario.getUsuDscSenha());
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 14:21:23
     * @param btpUsuario
     * @return
     */
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 01:11:50
     * @param btpUsuario
     * @return
     */
    public Map<BtpUsuario, Map<String, Object>> mapperUsuarioCadastro(BtpUsuario btpUsuario)
    {
        BtpUsuario btpUsuarioPersist = new BtpUsuario();

        btpUsuarioPersist.setUsuDscNome(btpUsuario.getUsuDscNome().trim().toUpperCase());
        btpUsuarioPersist.setUsuDscLogin(btpUsuario.getUsuDscLogin().trim());
        btpUsuarioPersist.setUsuDscSenha(passwordEncoder.encode(btpUsuario.getUsuDscSenha().trim()));
        btpUsuarioPersist.setUsuDscTelefone(btpUsuario.getUsuDscTelefone().trim());
        btpUsuarioPersist.setUsuDatNascimento(btpUsuario.getUsuDatNascimento());
        btpUsuarioPersist.setUsuFlgAceitePoliticaPrivacidade(btpUsuario.getUsuFlgAceitePoliticaPrivacidade());
        
        Map<String, Object> mapRegraAcesso = new WeakHashMap<>();
        
        if(CollectionUtil.isEmpty(btpUsuario.getLstBtpPerfisAcesso()))
        {
            mapRegraAcesso.put("perfisAcesso", new BtpPerfilAcesso(2L, "BASIC", Boolean.TRUE));
        }
        else
        {
            mapRegraAcesso.put("perfisAcesso", new BtpPerfilAcesso(1L, "ADMIN", Boolean.TRUE));
        }
        
        Map<BtpUsuario, Map<String, Object>> mapParam = new WeakHashMap<>();
        
        mapParam.put(btpUsuarioPersist, mapRegraAcesso);
        
        return mapParam;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 20:57:29
     * @param loginRequest
     */
    public LoginResponse autenticarUsuario(LoginRequest loginRequest)
    {
        Map<String, String> mapParam = new HashMap<>();
    
        mapParam.put("usuDscLogin", loginRequest.login());
    
        List<BtpUsuario> lstBtpUsuario = new ArrayList<>();
        
        try
        {
            lstBtpUsuario = usuarioRepository.buscarPorParametro(mapParam, BtpUsuario.class);
        }
        catch (InterruptedException | ExecutionException e)
        {
            throw new RuntimeException("Ocorreu um erro ao carregar dados do usuário."); 
        }
        
        if(CollectionUtil.isEmpty(lstBtpUsuario))
        {
            throw new BadCredentialsException("Usuário ou senha inválidos!");
        }

        final BtpUsuario btpUsuario = lstBtpUsuario.get(0);
        
        if (btpUsuario != null && Boolean.FALSE.equals(isCredenciaisValidas(loginRequest, btpUsuario, this.passwordEncoder))) 
        {
            throw new BadCredentialsException("Usuário ou senha inválidos!");
        }
        
        var now = Instant.now();
        
        var expiresIn = 300L;
 
        var claims = JwtClaimsSet.builder()
                .issuer("expertchat-ms")
                .subject(btpUsuario.getId())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();
        
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        
        return new LoginResponse(jwtValue, expiresIn);
    }


    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 23:06:03
     * @return
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    public List<BtpUsuario> consultarTodosUsuarios() throws InterruptedException, ExecutionException
    {
        return usuarioRepository.buscarTodos(BtpUsuario.class.getSimpleName(), BtpUsuario.class);
    }
    
}
