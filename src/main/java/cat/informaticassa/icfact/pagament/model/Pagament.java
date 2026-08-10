package cat.informaticassa.icfact.pagament.model;

import cat.informaticassa.icfact.factura.model.Factura;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.infraestructura.model.Activable;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PAGAMENT")
public class Pagament implements Activable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pressupost_id")
    private Pressupost pressupost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id")
    private Factura factura;

    @Column(nullable = false)
    private LocalDate dataPagament;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal importPagat;

    @Column(length = 100)
    private String referencia;

    @Column(length = 500)
    private String observacions;

    @Builder.Default
    @Column(nullable = false)
    private boolean actiu = true;

    @Column(nullable = false)
    private LocalDateTime dataCreacio;

    @Column(nullable = false)
    private LocalDateTime dataModificacio;
}