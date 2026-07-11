package cat.informaticassa.icfact.iva.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(
        name = "IVA",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "percentatge")
        }
)
public class Iva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal percentatge;

    @Builder.Default
    @Column(nullable = false)
    private Boolean actiu = true;

    @Column(nullable = false)
    private LocalDateTime dataCreacio;

    @Column(nullable = false)
    private LocalDateTime dataModificacio;
}
