package ru.javaops.masterjava.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.model.SendResult;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class MailResultDao implements AbstractMailDao {

    @SqlUpdate("TRUNCATE email_sending_results CASCADE ")
    @Override
    public abstract void clean();

    @SqlUpdate("INSERT INTO email_sending_results (date_time, from_email, email_subject, is_sending_success) " +
            "VALUES (:dateTime, :fromEmail, :emailSubject, :isSendingSuccess) ")
    @GetGeneratedKeys
    abstract int insert(@BindBean SendResult result);
}
