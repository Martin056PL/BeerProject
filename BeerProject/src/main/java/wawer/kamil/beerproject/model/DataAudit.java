package wawer.kamil.beerproject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class DataAudit {

    private LocalDateTime createdAt;
    private LocalDateTime updateDateTime;

    public DataAudit(LocalDateTime updateDateTime) {
        this.createdAt = LocalDateTime.now();
        this.updateDateTime = updateDateTime;
    }

}
