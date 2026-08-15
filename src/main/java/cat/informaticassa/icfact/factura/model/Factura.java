package cat.informaticassa.icfact.factura.model;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
import cat.informaticassa.icfact.pagament.model.Pagament;
import cat.informaticassa.icfact.pressupost.model.Pressupost;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FACTURA")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false)
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstatFactura estat;

    @Column(columnDefinition = "TEXT")
    private String observacions;

    @Builder.Default
    @OneToMany(
            mappedBy = "factura",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LiniaFactura> linies = new ArrayList<>();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal iva;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_pagament_id")
    private FormaPagament formaPagament;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pressupost_id")
    private Pressupost pressupost;

    @Builder.Default
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pagament> pagaments = new ArrayList<>();

    @Column
    private LocalDate dataCobrament;

    @Builder.Default
    @Column(nullable = false)
    private boolean actiu = true;

    @Column(nullable = false)
    private LocalDateTime dataCreacio;

    @Column(nullable = false)
    private LocalDateTime dataModificacio;
}