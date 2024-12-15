package br.com.donatti.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import br.com.donatti.firebase.repository.FirebaseRepository;
import br.com.donatti.model.BtpBotEspecialista;
import br.com.donatti.model.BtpUsuario;
import br.com.donatti.utils.CollectionUtil;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 00:43:58
 */
@Repository
public class UsuarioRepository extends FirebaseRepository<BtpUsuario>
{

    
    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 01:09:57
     * @param btpUsuario
     * @return BtpUsuario
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    public BtpUsuario salvarUsuario(final BtpUsuario btpUsuario) throws InterruptedException, ExecutionException
    {
        return salvar(btpUsuario);
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 14/12/2024 - 13:22:06
     * @param btpBotEspecialista
     */
    public void vincularNovoBotUsuario(final BtpBotEspecialista btpBotEspecialista)
    {
        Map<String, String> mapParam = new WeakHashMap<>();

        mapParam.put("id", btpBotEspecialista.getIdUsuario());
        
        List<BtpUsuario> lstBtpUsuario = consultarPorParametro(mapParam, BtpUsuario.class);
        
        if (CollectionUtil.isNotEmpty(lstBtpUsuario))
        {
            BtpUsuario btpUsuario = lstBtpUsuario.get(0);
            
            if (CollectionUtil.isEmpty(btpUsuario.getBots()))
            {
                btpUsuario.setBots(new ArrayList<>());
            }
            
            Map<String, Object> mapUpdates = new WeakHashMap<>();
            
            mapUpdates.put("bots", Arrays.asList(btpBotEspecialista.getId()));
            
            atualizar(btpUsuario, mapUpdates);
        }
    }

}
