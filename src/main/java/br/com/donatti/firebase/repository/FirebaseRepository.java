package br.com.donatti.firebase.repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 00:45:04
 */
@Slf4j
@Repository
public class FirebaseRepository
{
    @Autowired
    private FirebaseDatabase firebaseDatabase;
    
    private DatabaseReference databaseReference;

    @PostConstruct
    public void inicializar()
    {
        this.databaseReference = firebaseDatabase.getReference();
    }
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 01:07:23
     * @param path
     * @param data
     * @return
     */
    public CompletableFuture<Void> salvar(final String path, Object data, Map<String, Object> mapObjectCompose)
    {
        return CompletableFuture.runAsync(() ->
        {
            try
            {
                DatabaseReference dbReference = this.databaseReference.child(path).push();
                
                final String firebaseId = dbReference.getKey();

                if (firebaseId == null)
                {
                    throw new RuntimeException("Falha ao obter o ID gerado automaticamente.");
                }

                Map<String, Object> atualizacao = new HashMap<>();
                
                atualizacao.put("path", path + "/" + firebaseId);
                
                atualizacao.put("id", firebaseId);
                
                if (mapObjectCompose != null)
                {
                    for (Map.Entry<String, Object> compose : mapObjectCompose.entrySet())
                    {
                        atualizacao.put(compose.getKey(), compose.getValue());
                    }
                }

                dbReference.setValueAsync(data).get();
                
                dbReference.updateChildrenAsync(atualizacao).get();
            }
            catch (Exception e)
            {
                throw new RuntimeException("Falha ao salvar dados no Firebase: " + e);
            }
        });
    }
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 01:07:28
     * @param <T>
     * @param path
     * @param entityClass
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public <T> List<T> buscarTodos(final String path, Class<T> entityClass) throws InterruptedException, ExecutionException
    {
        CompletableFuture<List<T>> future = buscarTodosReflection(path, entityClass);
        
        List<T> lstResult = future.get();
        
        return lstResult;
    }
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 01:07:30
     * @param path
     * @return
     */
    private <T> CompletableFuture<List<T>> buscarTodosReflection(final String path, final Class<T> entidade)
    {
        CompletableFuture<List<T>> future = new CompletableFuture<>();

        this.databaseReference.child(path).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    try
                    {
                        List<T> lista = new ArrayList<>();

                        for (DataSnapshot child : dataSnapshot.getChildren())
                        {
                            T obj = processarDados(child, entidade);
                         
                            if (obj != null)
                            {
                                lista.add(obj);
                            }
                            else
                            {
                                log.warn("Não foi possível deserializar o nó: " + child.getKey());
                            }
                        }

                        future.complete(lista);
                    }
                    catch (Exception e)
                    {
                        future.completeExceptionally(new RuntimeException("Erro ao deserializar os dados: " + e.getMessage()));
                    }
                }
                else
                {
                    log.warn("Não foi encontrado nenhum registro para o nó especificado: " + path);
                    
                    future.complete(new ArrayList<>());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                future.completeExceptionally(new RuntimeException("Falha ao buscar dados: " + databaseError.getMessage()));
            }
        });

        return future;
    }

    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 19:33:22
     * @param <T>
     * @param child
     * @param entidade
     * @return
     */
    private <T> T processarDados(DataSnapshot child, Class<T> entidade)
    {
        try
        {
            T obj = entidade.getDeclaredConstructor().newInstance();

            Field[] fields = getAllFields(entidade);

            for (Field field : fields)
            {
                field.setAccessible(true);

                Object valorCampo = child.child(field.getName()).getValue();

                var id = child.getKey();

                if ("id".equals(field.getName()))
                {
                    field.set(obj, id);
                }

                if (valorCampo != null)
                {
                    mapperInstanceValue(obj, field, valorCampo);
                }
            }

            return obj;

        }
        catch (Exception e)
        {
            log.error("Erro ao processar dados para a entidade " + entidade.getSimpleName() + ": " + e.getMessage());
            
            return null;
        }
    }

    /**
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 20:44:17
     * @param <T>
     * @param obj
     * @param field
     * @param valorCampo
     * @throws IllegalAccessException
     */
    private <T> void mapperInstanceValue(T obj, Field field, Object valorCampo) throws IllegalAccessException
    {
        if (field.getType().equals(Long.class) || field.getType().equals(long.class))
        {
            if (valorCampo instanceof String)
            {
                try
                {
                    field.set(obj, Long.parseLong((String) valorCampo));
                }
                catch (NumberFormatException e)
                {
                    log.warn("Falha ao converter String para Long no campo " + field.getName() + ": " + valorCampo);
                }
            }
            else if (valorCampo instanceof Number)
            {
                field.set(obj, ((Number) valorCampo).longValue());
            }
        }
        else if (field.getType().equals(String.class))
        {
            field.set(obj, valorCampo.toString());
        }
        else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class))
        {
            field.set(obj, Boolean.parseBoolean(valorCampo.toString()));
        }
        else
        {
            field.set(obj, valorCampo);
        }
    }
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 20:47:05
     * @param clazz
     * @return
     */
    private Field[] getAllFields(Class<?> clazz)
    {
        List<Field> fields = new ArrayList<>();
        
        while (clazz != null)
        {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        
            clazz = clazz.getSuperclass();
        }
        
        return fields.toArray(new Field[0]);
    }
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 01:07:32
     * @param path
     * @param data
     * @return
     */
    public CompletableFuture<Void> atualizar(final String path, Map<String, Object> mapParam)
    {
        CompletableFuture<Void> future = new CompletableFuture<>();

        databaseReference.child(path).updateChildren(mapParam, (error, ref) ->
        {
            if (error != null)
            {
                future.completeExceptionally(
                        new RuntimeException("Falha ao atualizar dados no Firebase: " + error.getMessage()));
            }
            else
            {
                future.complete(null);
            }
        });

        return future;
    }

    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 01:07:37
     * @param path
     * @return
     */
    public CompletableFuture<Void> deletar(final String path)
    {
        return CompletableFuture.runAsync(() ->
        {
            try
            {
                this.databaseReference.child(path).removeValueAsync().get();
            }
            catch (Exception e)
            {
                throw new RuntimeException("Falha ao remover dados do Firebase: " + e);
            }
        });
    }
    
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 01:07:40
     * @param <T>
     * @param mapParam
     * @param entityClass
     * @return
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    public <T> List<T> buscarPorParametro(Map<String, String> mapParam, Class<T> entityClass) throws InterruptedException, ExecutionException
    {
        CompletableFuture<List<T>> future = buscarPorParametroReflection(mapParam, entityClass);
        
        return future.get();
    }
    
    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 19:48:38
     * @param <T>
     * @param mapParam
     * @param entityClass
     * @return
     */
    private <T> CompletableFuture<List<T>> buscarPorParametroReflection(Map<String, String> mapParam, Class<T> entityClass)
    {
        DatabaseReference ref = this.databaseReference.child(entityClass.getSimpleName());
        
        List<T> lstResult = new ArrayList<>();
        
        CompletableFuture<List<T>> future = new CompletableFuture<>();

        String key = mapParam.keySet().iterator().next();
        String value = mapParam.get(key);

        ref.orderByChild(key).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    T entity = processarDados(snapshot, entityClass);

                    if (entity != null)
                    {
                        try
                        {
                            Field field = entity.getClass().getDeclaredField(key);
                            
                            field.setAccessible(true);

                            Object fieldValue = field.get(entity);

                            if (fieldValue != null && comparaValor(fieldValue, value))
                            {
                                lstResult.add(entity);
                            }
                        }
                        catch (NoSuchFieldException | IllegalAccessException e)
                        {
                            log.error("Erro ao acessar ou processar o campo: ", e);
                        }
                    }
                }
                
                future.complete(lstResult);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                future.completeExceptionally(new RuntimeException("Erro ao buscar dados: " + databaseError.getMessage()));
            }
        });

        return future;
    }

    /**
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 10/12/2024 - 19:49:11
     * @param fieldValue
     * @param value
     * @return
     */
    private boolean comparaValor(Object fieldValue, String value)
    {
        if (fieldValue instanceof String)
        {
            return ((String) fieldValue).toUpperCase().contains(value.toUpperCase());
        }
        else if (fieldValue instanceof Long)
        {
            return fieldValue.toString().contains(value);
        }
        else if (fieldValue instanceof Integer)
        {
            return fieldValue.toString().contains(value);
        }
        else if (fieldValue instanceof Boolean)
        {
            return fieldValue.toString().contains(value);
        }
        return false;
    }
    
}
