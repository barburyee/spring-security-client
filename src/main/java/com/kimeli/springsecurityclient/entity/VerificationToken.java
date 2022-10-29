package com.kimeli.springsecurityclient.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;

@Entity
@Data
@NoArgsConstructor
public class VerificationToken {
    //Static expiration time
    private static final int EXPIRATION_TIME = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expirationTime;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false, foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
    private User user;

    public VerificationToken(String token){
        super();
        this.token = token;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    public VerificationToken(User user, String token) {
        super();
        this.user=user;
        this.token =token;
        //calculate expiration time
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);

    }

    private Date calculateExpirationDate(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new java.util.Date().getTime());
        calendar.add(calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }
}
