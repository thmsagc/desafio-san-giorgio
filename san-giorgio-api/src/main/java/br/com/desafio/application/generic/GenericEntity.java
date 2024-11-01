package br.com.desafio.application.generic;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
public class GenericEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "created_dt")
    private LocalDateTime createdDateTime;

    @Column(name = "updated_dt")
    private LocalDateTime updatedDateTime;

    @PrePersist
    public void prePersist() {
        this.createdDateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDateTime = LocalDateTime.now();
    }

}
