package br.com.donatti.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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
import br.com.donatti.repository.PerfilAcessoRepository;
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

    @Autowired
    private PerfilAcessoRepository perfilAcessoRepository;

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
    public Boolean isCredenciaisValidas(final LoginRequest loginRequest, final BtpUsuario btpUsuario,
            final PasswordEncoder passwordEncoder)
    {
        return passwordEncoder.matches(loginRequest.senha(), btpUsuario.getUsuDscSenha());
    }

    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 12/12/2024 - 22:17:17
     * @param btpUsuario
     * @return BtpUsuario
     */
    public BtpUsuario mapperUsuarioCadastro(BtpUsuario btpUsuario)
    {
        BtpUsuario btpUsuarioPersist = new BtpUsuario();
        
        btpUsuarioPersist.setUsuDscNome(btpUsuario.getUsuDscNome().trim().toUpperCase());
        btpUsuarioPersist.setUsuDscEmail(btpUsuario.getUsuDscEmail().trim());
        btpUsuarioPersist.setUsuDscLogin(btpUsuario.getUsuDscLogin().trim());
        btpUsuarioPersist.setUsuDscSenha(passwordEncoder.encode(btpUsuario.getUsuDscSenha().trim()));
        btpUsuarioPersist.setUsuDscTelefone(btpUsuario.getUsuDscTelefone().trim());
        btpUsuarioPersist.setUsuDatNascimento(btpUsuario.getUsuDatNascimento());
        btpUsuarioPersist.setUsuFlgAceitePoliticaPrivacidade(btpUsuario.getUsuFlgAceitePoliticaPrivacidade());

        final List<BtpPerfilAcesso> lstBtpPerfilAcesso = perfilAcessoRepository.consultarTodos(BtpPerfilAcesso.class);

        if (CollectionUtil.isEmpty(lstBtpPerfilAcesso))
        {

        }

        if (CollectionUtil.isNotEmpty(btpUsuario.getRoles()))
        {
            btpUsuario.getRoles().forEach(role ->
            {
                lstBtpPerfilAcesso.stream().filter(p -> p.getId().equals(role))
                        .map(BtpPerfilAcesso::getId)
                        .forEach(p -> btpUsuarioPersist.setRoles(Arrays.asList(String.valueOf(p))));
            });
        }
        else
        {
            lstBtpPerfilAcesso.stream().filter(p -> p.getPeaDscNome().equals("BASIC"))
                    .map(BtpPerfilAcesso::getId)
                    .forEach(p -> btpUsuarioPersist.setRoles(Arrays.asList(String.valueOf(p))));
        }

        return btpUsuarioPersist;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 20:57:29
     * @param loginRequest
     */
    public LoginResponse autenticarUsuario(final LoginRequest loginRequest)
    {
        Map<String, String> mapParam = new HashMap<>();

        mapParam.put("usuDscLogin", loginRequest.login());

        List<BtpUsuario> lstBtpUsuario = new ArrayList<>();

        lstBtpUsuario = usuarioRepository.consultarPorParametro(mapParam, BtpUsuario.class);

        if (CollectionUtil.isEmpty(lstBtpUsuario))
        {
            throw new BadCredentialsException("Usuário ou senha inválidos!");
        }

        final BtpUsuario btpUsuario = lstBtpUsuario.get(0);

        if (btpUsuario != null
                && Boolean.FALSE.equals(isCredenciaisValidas(loginRequest, btpUsuario, this.passwordEncoder)))
        {
            throw new BadCredentialsException("Usuário ou senha inválidos!");
        }

        var now = Instant.now();

        var expiresIn = 300L;
        
        var scopes = btpUsuario.getRoles().stream().map(role ->
        {
            Map<String, String> mapParamBusca = new HashMap<>();
            
            mapParamBusca.put("id", role);

            List<BtpPerfilAcesso> lstBtpPerfilAcesso = perfilAcessoRepository.consultarPorParametro(mapParamBusca, BtpPerfilAcesso.class);

            return CollectionUtil.isNotEmpty(lstBtpPerfilAcesso)
                    ? lstBtpPerfilAcesso.stream().map(BtpPerfilAcesso::getPeaDscNome).collect(Collectors.joining(" "))
                    : "";
        }).collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("expertchat-ms")
                .subject(btpUsuario.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(jwtValue, expiresIn);
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 12/12/2024 - 22:20:30
     * @param btpUsuario
     * @return BtpUsuario
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public BtpUsuario salvarUsuario(final BtpUsuario btpUsuario) throws InterruptedException, ExecutionException
    {
        return usuarioRepository.salvarUsuario(mapperUsuarioCadastro(btpUsuario));
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 12/12/2024 - 22:23:11
     * @return
     */
    public List<BtpUsuario> consultarTodosUsuarios()
    {
        return usuarioRepository.consultarTodos(BtpUsuario.class);
    }

}
