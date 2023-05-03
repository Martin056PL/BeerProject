package wawer.kamil.beerproject.model.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import wawer.kamil.beerproject.model.audited.JpaAuditedEntity;
import wawer.kamil.beerproject.model.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_REGISTRATION_DATA")
@Getter
@Setter
@Audited
public class UserRegistrationData extends JpaAuditedEntity implements Serializable {

    private static final long serialVersionUID = 5875926697055969280L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    @Version
    private Long version;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "is_confirmed")
    private boolean isConfirmed;

    public UserRegistrationData(LocalDateTime now, int timeToTokenExpire, String uuid) {
        this.expiryDate = now.plusMinutes(timeToTokenExpire);
        this.confirmationToken = uuid;
        this.isConfirmed = false;
    }

    public void extendExpiryDate(int extendTimeInMinutes, LocalDateTime now){
        this.expiryDate = now.plusMinutes(extendTimeInMinutes);
    }
}
