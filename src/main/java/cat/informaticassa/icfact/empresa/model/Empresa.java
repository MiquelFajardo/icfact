package cat.informaticassa.icfact.empresa.model;
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
@Table(name = "EMPRESA")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(nullable = false, length = 150)
    private String descripcio;

    @Column(nullable = false, unique = true, length = 9)
    private String nif;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "adreca_id")
    private Adreca adreca;

    @Column(length = 20)
    private String telefon;

    @Column(length = 100)
    private String email;

    @Column(length = 150)
    private String web;

    @Column(length = 34)
    private String iban;

    @Column()
    private String logo;

    @Column(length = 7)
    private String color;

    @Column(columnDefinition = "TEXT")
    private String peuPdf;

    @Column(name = "contrasenya_hash")
    private String contrasenyaHash;

    @Column(nullable = false)
    private LocalDateTime dataCreacio;

    @Column(nullable = false)
    private LocalDateTime dataModificacio;

    public void actualitzarDades(Empresa altra) {
        this.nom = altra.nom;
        this.descripcio = altra.descripcio;
        this.nif = altra.nif;
        this.adreca = altra.adreca;
        this.telefon = altra.telefon;
        this.email = altra.email;
        this.web = altra.web;
        this.iban = altra.iban;
        this.logo = altra.logo;
        this.color = altra.color;
        this.peuPdf = altra.peuPdf;
    }

}