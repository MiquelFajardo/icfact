package cat.informaticassa.icfact.geografia.service.pais;

import cat.informaticassa.icfact.geografia.exception.PaisJaExisteixException;
import cat.informaticassa.icfact.geografia.model.Pais;
import cat.informaticassa.icfact.geografia.repository.PaisRepository;
import cat.informaticassa.icfact.geografia.service.validar.ValidarPais;

public class ModificarPaisService {

    private final PaisRepository repository = new PaisRepository();
    private final ValidarPais validar = new ValidarPais();

    public void executar(Pais pais) {

        validar.executar(pais);

        repository.buscarPerNom(pais.getNom())
                .filter(p -> !p.getId().equals(pais.getId()))
                .ifPresent(p -> {
                    throw new PaisJaExisteixException(
                            "Ja existeix un país amb aquest nom."
                    );
                });

        repository.buscarPerCodiIso(pais.getCodiIso())
                .filter(p -> !p.getId().equals(pais.getId()))
                .ifPresent(p -> {
                    throw new PaisJaExisteixException(
                            "Ja existeix un país amb aquest codi ISO."
                    );
                });

        repository.actualitzar(pais);
    }
}