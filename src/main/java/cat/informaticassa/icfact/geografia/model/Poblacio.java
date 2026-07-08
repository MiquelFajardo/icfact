package cat.informaticassa.icfact.geografia.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(
        name = "POBLACIO",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"provincia_id", "nom"})
        })

public class Poblacio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provincia_id", nullable = false)
    private String nom;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provincia_id", nullable = false)
    private Provincia provincia;

    @Builder.Default
    private Set<String> codiPostal = new TreeSet<>();
}