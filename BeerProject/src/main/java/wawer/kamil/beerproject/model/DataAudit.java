package wawer.kamil.beerproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
abstract class DataAudit {

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "update_date_time")
    private LocalDateTime updatedAt;

    public DataAudit(LocalDateTime updatedAt) {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = updatedAt;
    }
}
