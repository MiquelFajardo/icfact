package cat.informaticassa.icfact.geografia.model;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "ADRECA")
public class Adreca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String carrer;

    @Column(length = 10)
    private String numero;

    @Column(length = 10)
    private String pis;

    @Column(length = 10)
    private String porta;

    @Column(columnDefinition = "TEXT")
    private String observacions;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "poblacio_id", nullable = false)
    private Poblacio poblacio;

    @Column(nullable = false, length = 10)
    private String codiPostal;
}