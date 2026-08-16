package cat.informaticassa.icfact.client.model;

import cat.informaticassa.icfact.geografia.model.Adreca;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(
        name = "CLIENT",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "nif")
        }
)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(length = 150)
    private String nomComercial;

    @Column(nullable = false, length = 20)
    private String nif;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "adreca_id")
    private Adreca adreca;

    @Column(length = 20)
    private String telefon;

    @Column(length = 20)
    private String mobil;

    @Column(length = 100)
    private String email;

    @Column(length = 150)
    private String web;

    @Lob
    private String observacions;

    @Builder.Default
    @Column(nullable = false)
    private boolean actiu = true;

    @Column(nullable = false)
    private LocalDateTime dataCreacio;

    @Column(nullable = false)
    private LocalDateTime dataModificacio;

    public void actualitzarDades(Client altre) {
        this.nom = altre.nom;
        this.nomComercial = altre.nomComercial;
        this.nif = altre.nif;
        this.adreca = altre.adreca;
        this.telefon = altre.telefon;
        this.mobil = altre.mobil;
        this.email = altre.email;
        this.web = altre.web;
        this.observacions = altre.observacions;
        this.actiu = altre.actiu;
    }
}
