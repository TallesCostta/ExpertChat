package br.com.donatti.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.donatti.model.BtpBotEspecialista;
import br.com.donatti.repository.BotEspecialistaRepository;
import br.com.donatti.repository.UsuarioRepository;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 14/12/2024 - 12:42:13
 */
@Service
public class BotEspecialistaService
{
    @Autowired
    private BotEspecialistaRepository botEspecialistaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 14/12/2024 - 12:47:16
     * @param idUsuario
     * @return
     */
    public List<BtpBotEspecialista> consultarTodosBotsEspecialistaPorUsuario(final String idUsuario)
    {   
        return botEspecialistaRepository.consultarBotsUsuarioPorId(idUsuario);
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 14/12/2024 - 13:09:51
     * @param btpBotEspecialista
     */
    public BtpBotEspecialista cadastrarNovoBot(BtpBotEspecialista btpBotEspecialista)
    {
        final BtpBotEspecialista botEspecialista = botEspecialistaRepository.salvar(mapperBotEspecialista(btpBotEspecialista));
        
        usuarioRepository.vincularNovoBotUsuario(botEspecialista);
        
        return botEspecialista;
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 14/12/2024 - 13:16:05
     * @param btpBotEspecialista
     * @return
     */
    private BtpBotEspecialista mapperBotEspecialista(BtpBotEspecialista btpBotEspecialista)
    {
        BtpBotEspecialista botEspecialistaPersistir = new BtpBotEspecialista();
        
        botEspecialistaPersistir.setBoeDscNome(btpBotEspecialista.getBoeDscNome().trim());
        botEspecialistaPersistir.setBoeDscCategoria(btpBotEspecialista.getBoeDscCategoria().toUpperCase().trim());
        botEspecialistaPersistir.setBoeDscEspecialidade(btpBotEspecialista.getBoeDscEspecialidade().toUpperCase().trim());
        botEspecialistaPersistir.setBotDatCadastro(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        botEspecialistaPersistir.setIdUsuario(btpBotEspecialista.getIdUsuario());
        
        return botEspecialistaPersistir;
    }

}
