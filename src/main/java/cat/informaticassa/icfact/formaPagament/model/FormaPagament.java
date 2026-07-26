package cat.informaticassa.icfact.formaPagament.model;

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
@Table(name = "FORMA_PAGAMENT")
public class FormaPagament implements Activable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nom;

    @Column(length = 500)
    private String descripcio;

    @Column(nullable = false)
    private Boolean mostrarIban;

    @Column(nullable = false)
    private boolean actiu;

    @Column(nullable = false)
    private LocalDateTime dataCreacio;

    @Column(nullable = false)
    private LocalDateTime dataModificacio;
}