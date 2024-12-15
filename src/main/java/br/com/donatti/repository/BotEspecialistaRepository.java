package br.com.donatti.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.donatti.firebase.repository.FirebaseRepository;
import br.com.donatti.model.BtpBotEspecialista;
import br.com.donatti.model.BtpUsuario;
import br.com.donatti.utils.CollectionUtil;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 14/12/2024 - 12:49:30
 */
@Repository
public class BotEspecialistaRepository extends FirebaseRepository<BtpBotEspecialista>
{
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 14/12/2024 - 12:52:24
     * @param idUsuario
     * @return
     */
    public List<BtpBotEspecialista> consultarBotsUsuarioPorId(final String idUsuario)
    {
        List<BtpBotEspecialista> lstBotEspecialista = new ArrayList<>();
     
        Map<String, String> mapUsuario = new WeakHashMap<>();
        
        mapUsuario.put("id", idUsuario);
        
        BtpUsuario btpUsuarioReferencia = usuarioRepository.consultarPorParametro(mapUsuario, BtpUsuario.class).get(0);
        
        btpUsuarioReferencia.getBots().stream().forEach(bot -> 
        {
            Map<String, String> mapBot = new WeakHashMap<>();
            
            mapBot.put("id", bot);
            
            List<BtpBotEspecialista> lstResult = consultarPorParametro(mapBot, BtpBotEspecialista.class);
            
            if (CollectionUtil.isNotEmpty(lstResult))
            {
                lstResult.stream().filter(Objects::nonNull).forEach(lstBotEspecialista::add);
            }
        });
        
        return lstBotEspecialista;
    }

}
