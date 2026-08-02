package cat.informaticassa.icfact.client.model;

import cat.informaticassa.icfact.geografia.model.Adreca;
import cat.informaticassa.icfact.infraestructura.model.Activable;
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
public class Client implements Activable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(nullable = false, length = 20)
    private String nif;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "adreca_id")
    private Adreca adreca;

    @Column(length = 20)
    private String telefon;

    @Column(length = 100)
    private String email;

    @Column(length = 150)
    private String web;

    @Builder.Default
    @Column(nullable = false)
    private boolean actiu = true;

    @Column(nullable = false)
    private LocalDateTime dataCreacio;

    @Column(nullable = false)
    private LocalDateTime dataModificacio;

    public void actualitzarDades(Client altre) {
        this.nom = altre.nom;
        this.nif = altre.nif;
        this.adreca = altre.adreca;
        this.telefon = altre.telefon;
        this.email = altre.email;
        this.web = altre.web;
        this.actiu = altre.actiu;
    }

}
