package wawer.kamil.beerproject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public abstract class DataAudit {

    private LocalDateTime createdAt;
    private LocalDateTime updateDateTime;
    private boolean isDeleted;

    public DataAudit(LocalDateTime updateDateTime, boolean isDeleted) {
        this.createdAt = LocalDateTime.now();
        this.updateDateTime = updateDateTime;
        this.isDeleted = isDeleted;
    }

}
