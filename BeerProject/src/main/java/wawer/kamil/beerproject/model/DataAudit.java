package wawer.kamil.beerproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@ToString
public abstract class DataAudit {

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "update_date_time")
    private LocalDateTime updatedAt;

    protected DataAudit() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
