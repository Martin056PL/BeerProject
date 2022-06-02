package wawer.kamil.beerproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;
import static wawer.kamil.beerproject.utils.UuidGenerator.generateConfirmationToken;

@Entity
@AllArgsConstructor
@Table(name = "USER_REGISTRATION_DATA")
@Getter
@Setter
public class UserRegistrationData extends DataAudit implements Serializable {

    private static final long serialVersionUID = 5875926697055969280L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_registration_data_id")
    private Long userRegistrationDataId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "is_confirmed")
    private boolean isConfirmed;

    public UserRegistrationData() {
        super();
        this.confirmationToken = generateConfirmationToken();
        this.expiryDate = LocalDateTime.now().plusMinutes(15);
        this.isConfirmed = false;
    }
}
