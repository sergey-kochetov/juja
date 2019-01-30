package dbService;

import org.hibernate.HibernateException;

public class DBException extends RuntimeException {
    public DBException(HibernateException e) {
        super(e);
    }
}
