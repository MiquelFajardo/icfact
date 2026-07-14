package cat.informaticassa.icfact.pressupost.model;

import cat.informaticassa.icfact.iva.model.Iva;
import cat.informaticassa.icfact.producte.model.Producte;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "LINIA_PRESSUPOST")
public class LiniaPressupost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pressupost_id", nullable = false)
    private Pressupost pressupost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producte_id")
    private Producte producte;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcio;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantitat;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preu;

    @Builder.Default
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal dte = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "iva_id", nullable = false)
    private Iva iva;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Builder.Default
    @Column(nullable = false)
    private Boolean actiu = true;
}