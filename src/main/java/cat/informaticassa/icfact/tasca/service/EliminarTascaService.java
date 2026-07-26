package cat.informaticassa.icfact.tasca.service;

import cat.informaticassa.icfact.tasca.model.Tasca;
import cat.informaticassa.icfact.tasca.repository.TascaRepository;

public class EliminarTascaService {

    private final TascaRepository repository = new TascaRepository();

    public void executar(Tasca tasca) {
        repository.desactivar(tasca);            }

}