package cat.informaticassa.icfact.empresa.model;
import cat.informaticassa.icfact.geografia.model.Adreca;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    private Long id;
    private String nom;
    private String nif;
    private Adreca adreca;
    private String telefon;
    private String email;
    private String web;
    private String iban;
    private String logo;
    private String peuPdf;
    private LocalDateTime dataCreacio;
    private LocalDateTime dataModificacio;
}