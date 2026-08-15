package cat.informaticassa.icfact.tasca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TASCA")
@Getter
@Setter
@NoArgsConstructor
public class Tasca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titol;

    @Column
    private LocalDate dataLimit;

    @Column(nullable = false)
    private boolean feta;

    @Column(nullable = false)
    private boolean actiu;

    @Column(nullable = false)
    private LocalDateTime dataCreacio;

    @Column(nullable = false)
    private LocalDateTime dataModificacio;

    @PrePersist
    private void prePersist() {
        LocalDateTime ara = LocalDateTime.now();
        dataCreacio = ara;
        dataModificacio = ara;
        actiu = true;
        feta = false;
    }

    @PreUpdate
    private void preUpdate() {
        dataModificacio = LocalDateTime.now();
    }
}