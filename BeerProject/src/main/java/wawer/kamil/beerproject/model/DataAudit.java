package wawer.kamil.beerproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public abstract class DataAudit {

    private LocalDateTime createdAt;
    private LocalDateTime updateDateTime;

    public DataAudit(LocalDateTime updateDateTime) {
        this.createdAt = LocalDateTime.now();
        this.updateDateTime = updateDateTime;
    }
}
