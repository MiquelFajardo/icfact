package cat.informaticassa.icfact.pressupost.model;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.formaPagament.model.FormaPagament;
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
@Table(name = "PRESSUPOST")
public class Pressupost {
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
    private EstatPressupost estat;

    @Column(columnDefinition = "TEXT")
    private String observacions;

    @Builder.Default
    @OneToMany(
            mappedBy = "pressupost",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LiniaPressupost> linies = new ArrayList<>();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal iva;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_pagament_id")
    private FormaPagament formaPagament;

    @Column(nullable = false)
    private LocalDateTime dataCreacio;

    @Column(nullable = false)
    private LocalDateTime dataModificacio;

    @Builder.Default
    @Column(nullable = false)
    private Boolean actiu = true;
}