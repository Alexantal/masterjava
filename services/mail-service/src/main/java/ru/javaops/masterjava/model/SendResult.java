package ru.javaops.masterjava.model;

import com.bertoncelj.jdbi.entitymapper.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendResult {

    private Integer id;
    @Column("date_time")
    private LocalDateTime dateTime;
    @Column("from_email")
    private String fromEmail;
    @Column("email_subject")
    private String emailSubject;
    @Column("is_sending_success")
    private boolean isSendingSuccess;
}
