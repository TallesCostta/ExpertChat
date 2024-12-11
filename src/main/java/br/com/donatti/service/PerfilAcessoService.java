package br.com.donatti.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import br.com.donatti.model.BtpPerfilAcesso;
import br.com.donatti.utils.CollectionUtil;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 04:32:19
 */
@Service
public class PerfilAcessoService
{
    
    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 01:47:35
     * @param btpPerfilAcesso
     * @param lsBtpPerfilAcesso
     * @return
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    public BtpPerfilAcesso mapperPerfilAcesso(BtpPerfilAcesso btpPerfilAcesso, List<BtpPerfilAcesso> lsBtpPerfilAcesso) throws InterruptedException, ExecutionException
    {
        Long versao = 1L;
        
        if (CollectionUtil.isNotEmpty(lsBtpPerfilAcesso))
        {
            versao = lsBtpPerfilAcesso.size() + 1L;
        }
        
        BtpPerfilAcesso btpPerfilAcessoPersit = new BtpPerfilAcesso();
        
        btpPerfilAcessoPersit.setPeaCodPerfilAcesso(versao);
        btpPerfilAcessoPersit.setPeaDscNome(btpPerfilAcesso.getPeaDscNome().toUpperCase().trim());
        btpPerfilAcessoPersit.setPeaFlgAtivo(Boolean.TRUE);
        
        return btpPerfilAcessoPersit;
    }
}
