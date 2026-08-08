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

    @Column(length = 150)
    private String carrer;

    @Column(length = 10)
    private String numero;

    @Column(length = 10)
    private String pis;

    @Column(length = 10)
    private String porta;

    @Column(columnDefinition = "TEXT")
    private String observacions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pais_id")
    private Pais pais;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poblacio_id")
    private Poblacio poblacio;

    public String getAdrecaCompleta() {
        StringBuilder sb = new StringBuilder();
        if (carrer != null && !carrer.isBlank()) {
            sb.append(carrer);
        }

        if (numero != null && !numero.isBlank()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(numero);
        }

        if (pis != null && !pis.isBlank()) {
            sb.append(" ").append(pis);
        }

        if (porta != null && !porta.isBlank()) {
            sb.append(" ").append(porta);
        }
        return sb.toString();
    }
}