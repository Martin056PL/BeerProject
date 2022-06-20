package wawer.kamil.beerproject.model.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wawer.kamil.beerproject.model.DataAudit;
import wawer.kamil.beerproject.model.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static javax.persistence.GenerationType.IDENTITY;
import static wawer.kamil.beerproject.utils.UuidGenerator.generateConfirmationToken;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_REGISTRATION_DATA")
@Getter
@Setter
public class UserRegistrationData extends DataAudit implements Serializable {

    private static final long serialVersionUID = 5875926697055969280L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_registration_data_id")
    private Long userRegistrationDataId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "is_confirmed")
    private boolean isConfirmed;

    public UserRegistrationData(int timeToTokenExpire) {
        super();
        this.confirmationToken = generateConfirmationToken();
        this.expiryDate = now().plusMinutes(timeToTokenExpire);
        this.isConfirmed = false;
    }

    public void extendExpiryDate(int extendTimeInMinutes){
        this.expiryDate = now().plusMinutes(extendTimeInMinutes);
    }
}
