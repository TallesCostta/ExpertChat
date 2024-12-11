package br.com.donatti.repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.donatti.firebase.repository.FirebaseRepository;
import br.com.donatti.model.BtpPerfilAcesso;
import br.com.donatti.service.PerfilAcessoService;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 01:37:47
 */
@Repository
public class PerfilAcessoRepository extends FirebaseRepository
{
    @Autowired
    private PerfilAcessoService perfilAcessoService;
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 01:47:02
     * @param btpPerfilAcesso
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    public void salvarPerfilAcesso(final BtpPerfilAcesso btpPerfilAcesso) throws InterruptedException, ExecutionException
    {
        final List<BtpPerfilAcesso> lsBtpPerfilAcesso = buscarTodos(btpPerfilAcesso.getPath(), BtpPerfilAcesso.class);
        
        salvar(btpPerfilAcesso.getPath(), perfilAcessoService.mapperPerfilAcesso(btpPerfilAcesso, lsBtpPerfilAcesso), null);
    }
    
}
