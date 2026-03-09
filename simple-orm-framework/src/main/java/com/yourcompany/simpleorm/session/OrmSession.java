package com.yourcompany.simpleorm.session;

import java.util.Optional;
import com.yourcompany.simpleorm.exception.OrmException;
public interface OrmSession extends AutoCloseable{
	void save(Object entity);
	<T> Optional<T> findById(Class<T> clazz, Object id);
	void beginTransaction() throws OrmException;
	void commit() throws OrmException;
	void rollback() throws OrmException;
	
	@Override
	 void close() throws Exception;
	boolean isTransactionActive();
	void update(Object entity) throws OrmException;
	void delete(Object entity) throws OrmException;
	public <T> void deleteById(Class<T> clazz, Object id) throws OrmException;
}
