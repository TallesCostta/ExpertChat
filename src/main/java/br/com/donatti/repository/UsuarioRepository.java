package br.com.donatti.repository;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import br.com.donatti.firebase.repository.FirebaseRepository;
import br.com.donatti.model.BtpUsuario;

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

}
