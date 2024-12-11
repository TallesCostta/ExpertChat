package br.com.donatti.repository;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import br.com.donatti.firebase.repository.FirebaseRepository;
import br.com.donatti.model.BtpUsuario;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 00:43:58
 */
@Repository
public class UsuarioRepository extends FirebaseRepository
{

    
    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 01:09:57
     * @param btpUsuario
     * @param mapPerfisAcesso
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    public void salvarUsuario(final BtpUsuario btpUsuario, final Map<String, Object> mapPerfisAcesso) throws InterruptedException, ExecutionException
    {
        salvar(btpUsuario.getPath(), btpUsuario, mapPerfisAcesso);
    }

}
