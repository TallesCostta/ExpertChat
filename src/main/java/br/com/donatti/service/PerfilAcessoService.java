package br.com.donatti.service;

import org.springframework.stereotype.Service;

import br.com.donatti.model.BtpPerfilAcesso;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 04:32:19
 */
@Service
public class PerfilAcessoService
{

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 01:47:35
     * @param btpPerfilAcesso
     * @return
     */
    public BtpPerfilAcesso mapperPerfilAcesso(final BtpPerfilAcesso btpPerfilAcesso)
    {
        BtpPerfilAcesso btpPerfilAcessoPersit = new BtpPerfilAcesso();
        
        btpPerfilAcessoPersit.setPeaDscNome(btpPerfilAcesso.getPeaDscNome().toUpperCase().trim());
        btpPerfilAcessoPersit.setPeaFlgAtivo(Boolean.TRUE);

        return btpPerfilAcessoPersit;
    }
}
