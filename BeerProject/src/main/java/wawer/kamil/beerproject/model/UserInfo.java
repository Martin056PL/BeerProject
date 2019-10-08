package wawer.kamil.beerproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "user_info")
@EqualsAndHashCode(exclude = "user")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -2589943970364233627L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Size(min = 2, max = 30)
    @Column(name = "first_name")
    @NotBlank
    private String firstName;

    @Size(min = 2, max = 30)
    @Column(name = "last_name")
    @NotBlank
    private String lastName;

    @NotBlank
    @Column(name = "phone_number", nullable = false)
    private Long phoneNumber;
}
