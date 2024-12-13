package br.com.donatti.firebase.repository;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Repository;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.donatti.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Repositório genérico para interagir com o Firebase. Utiliza reflexão para
 * facilitar a manipulação de entidades de forma genérica.
 * 
 * @author Tales Paiva [tallescosttapaiva@gmail.com] 12/12/2024 - 21:32:42
 * @param <T>
 */
@Slf4j
@Repository
public abstract class FirebaseRepository<T>
{

    private DatabaseReference databaseReference;

    /**
     * Retorna a referência do banco de dados para a classe do tipo genérico T.
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 12/12/2024 - 21:32:57
     * @return DatabaseReference
     */
    protected DatabaseReference getDatabaseReference()
    {
        if (databaseReference == null)
        {
            this.databaseReference = FirebaseDatabase.getInstance().getReference();
        }

        return databaseReference.child(getTypeClassName());
    }

    /**
     * Retorna o nome da classe do tipo genérico T.
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 12/12/2024 - 21:33:52
     * @return String
     */
    private String getTypeClassName()
    {
        Class<?> clazz = (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        
        return clazz.getSimpleName();
    }

    /**
     * Busca uma entidade pelo seu ID.
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 12/12/2024 - 21:34:08
     * @param id
     * @return DatabaseReference
     */
    protected DatabaseReference find(final String id)
    {
        return getDatabaseReference().child(id);
    }

    /**
     * Busca todas as entidades do tipo T.
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 12/12/2024 - 21:34:29
     * @param entityClass
     * @return List<T>
     */
    public List<T> consultarTodos(final Class<T> entityClass)
    {
        CompletableFuture<List<T>> future = new CompletableFuture<>();
        
        getDatabaseReference().addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                List<T> resultList = new ArrayList<>();
                
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    T item = snapshot.getValue(entityClass);
                    
                    if (item != null)
                    {
                        resultList.add(item);
                    }
                }
                future.complete(resultList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                future.completeExceptionally(databaseError.toException());
            }
        });

        return future.join();
    }

    /**
     * Salva ou atualiza uma entidade no Firebase.
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 12/12/2024 - 21:34:54
     * @param entidade
     * @return CompletableFuture<T>
     */
    public T salvar(final T entidade)
    {
        CompletableFuture<T> future = new CompletableFuture<>();

        DatabaseReference reference = getDatabaseReference();

        String id = obterId(entidade);

        if (StringUtil.isNotBlank(id))
        {
            reference = reference.child(id);
        }

        reference.setValue(entidade, (databaseError, databaseReference) ->
        {
            if (databaseError != null)
            {
                future.completeExceptionally(databaseError.toException());
            }
            else
            {
                future.complete(entidade);
            }
        });

        return future.join();
    }

    /**
     * Obtém o ID de uma entidade usando reflexão.
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 12/12/2024 - 21:35:13
     * @param entidade
     * @return String
     */
    private String obterId(final T entidade)
    {
        try
        {
            Field idField = entidade.getClass().getSuperclass().getDeclaredField("id");
            
            idField.setAccessible(true);
            
            if (idField.get(entidade) == null || idField.get(entidade).toString().isEmpty()) 
            {
                idField.set(entidade, UUID.randomUUID().toString());
            }
            
            return (String) idField.get(entidade);
        }
        catch (Exception e)
        {
            log.error("Erro ao obter ID da entidade: " + e.getMessage(), e);
            
            return null;
        }
    }

    /**
     * Busca entidades por um parâmetro específico.
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 12/12/2024 - 21:36:00
     * @param mapParam
     * @param entityClass
     * @return List<T>
     */
    public List<T> buscarPorParametro(final Map<String, String> mapParam, final Class<T> entityClass)
    {
        CompletableFuture<List<T>> future = new CompletableFuture<>();
        
        String key = mapParam.keySet().iterator().next();
        
        String value = mapParam.get(key);

        getDatabaseReference().orderByChild(key).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                List<T> resultList = new ArrayList<>();
                
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    T entity = processarDados(snapshot, entityClass);
                
                    if (entity != null && compararValor(entity, key, value))
                    {
                        resultList.add(entity);
                    }
                }
                future.complete(resultList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                future.completeExceptionally(new RuntimeException("Erro ao buscar dados: " + databaseError.getMessage()));
            }
        });

        return future.join();
    }

    /**
     * Processa os dados de um snapshot para uma entidade do tipo T.
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 12/12/2024 - 21:37:25
     * @param snapshot
     * @param entityClass
     * @return T
     */
    private T processarDados(final DataSnapshot snapshot, final Class<T> entityClass)
    {
        try
        {
            T obj = entityClass.getDeclaredConstructor().newInstance();
            
            Field[] fields = getAllFields(entityClass);

            for (Field field : fields)
            {
                field.setAccessible(true);
            
                Object valorCampo = snapshot.child(field.getName()).getValue();

                if (valorCampo != null)
                {
                    field.set(obj, valorCampo);
                }
            }

            return obj;
        }
        catch (Exception e)
        {
            log.error("Erro ao processar dados: " + e.getMessage(), e);
            
            return null;
        }
    }

    /**
     * Obtém todos os campos de uma classe, incluindo os da superclasse.
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 12/12/2024 - 21:37:43
     * @param entityClass
     * @return Field[]
     */
    private Field[] getAllFields(Class<?> entityClass)
    {
        List<Field> fields = new ArrayList<>();
        
        while (entityClass != null)
        {
            fields.addAll(Arrays.asList(entityClass.getDeclaredFields()));
        
            entityClass = entityClass.getSuperclass();
        }
        
        return fields.toArray(new Field[0]);
    }

    /**
     * Compara o valor do campo com o valor fornecido no parâmetro de busca.
     * 
     * @author Tales Paiva [tallescosttapaiva@gmail.com] 12/12/2024 - 21:38:44
     * @param entity
     * @param key
     * @param value
     * @return boolean
     */
    private boolean compararValor(final T entity, final String key, final String value)
    {
        try
        {
            Field field = null;
            
            if (key.equals("id"))
            {
                field = entity.getClass().getSuperclass().getDeclaredField(key);
            }
            else
            {
                field = entity.getClass().getDeclaredField(key);
            }
            
            field.setAccessible(true);
            
            Object fieldValue = field.get(entity);

            if (fieldValue instanceof String)
            {
                return ((String) fieldValue).toUpperCase().contains(value.toUpperCase());
            }
            else if (fieldValue instanceof Long || fieldValue instanceof Integer)
            {
                return fieldValue.toString().contains(value);
            }
            else if (fieldValue instanceof Boolean)
            {
                return fieldValue.toString().contains(value);
            }
        }
        catch (NoSuchFieldException | IllegalAccessException e)
        {
            log.error("Erro ao comparar valor: " + e.getMessage(), e);
        }
        
        return false;
    }
    
}
