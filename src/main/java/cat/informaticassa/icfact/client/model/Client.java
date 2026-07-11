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

    @Column(nullable = false, length = 20)
    private String nif;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "adreca_id", nullable = false)
    private Adreca adreca;

    @Column(length = 20)
    private String telefon;

    @Column(length = 100)
    private String email;

    @Column(length = 150)
    private String web;

    @Builder.Default
    @Column(nullable = false)
    private Boolean actiu = true;

    @Column(nullable = false)
    private LocalDateTime dataCreacio;

    @Column(nullable = false)
    private LocalDateTime dataModificacio;

}
