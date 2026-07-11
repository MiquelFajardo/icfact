package cat.informaticassa.icfact.geografia.model;

import cat.informaticassa.icfact.infraestructura.converter.SetStringConverter;
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

    @Column(nullable = false, length = 100)
    private String nom;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provincia_id", nullable = false)
    private Provincia provincia;

    @Builder.Default
    @Convert(converter = SetStringConverter.class)
    private Set<String> codiPostal = new TreeSet<>();
}