package wawer.kamil.beerproject.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
public abstract class DateAudit implements Serializable {

    private static final long serialVersionUID = 5976944071228893750L;

    @CreatedDate
    @Column(nullable = false, updatable = false, name = "create_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false, name = "updated_at")
    private Instant updatedAt;


}
