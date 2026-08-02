package cat.informaticassa.icfact.geografia.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(
        name = "PROVINCIA",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"pais_id", "codi"})
        })

public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 2)
    private String codi;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name ="pais_id", nullable = false)
    private Pais pais;

    @Override
    public String toString() {
        return nom;
    }
}