package cat.informaticassa.icfact.producte.model;

import cat.informaticassa.icfact.iva.model.Iva;
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
        name = "PRODUCTE",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "codi")
        }
)
public class Producte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String codi;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String descripcio;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preu;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "iva_id")
    private Iva iva;

    @Builder.Default
    @Column(nullable = false)
    private boolean actiu = true;

    @Column(nullable = false)
    private LocalDateTime dataCreacio;

    @Column(nullable = false)
    private LocalDateTime dataModificacio;

}