package cat.informaticassa.icfact.tasca.service;

import cat.informaticassa.icfact.tasca.model.Tasca;
import cat.informaticassa.icfact.tasca.repository.TascaRepository;

public class MarcarTascaPendentService {

    private final TascaRepository repository = new TascaRepository();

    public void executar(Tasca tasca) {
        tasca.setFeta(false);
        repository.actualitzar(tasca);
    }

}