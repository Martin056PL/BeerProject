package wawer.kamil.beerproject.configuration.envers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@RevisionEntity(CustomRevisionListener.class)
@Table(name = "REVISION")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Revision implements Serializable {
    private static final long serialVersionUID = -8460926448764759495L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @RevisionNumber
    private Long id;

    @RevisionTimestamp
    private long timestamp;

    private String changedBy;
}
