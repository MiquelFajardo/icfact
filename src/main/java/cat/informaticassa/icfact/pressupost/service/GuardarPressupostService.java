package cat.informaticassa.icfact.pressupost.service;

import cat.informaticassa.icfact.pressupost.model.Pressupost;

public class GuardarPressupostService {
    private final CrearPressupostService crearService = new CrearPressupostService();
    private final ModificarPressupostService modificarService = new ModificarPressupostService();

    public Pressupost executar(Pressupost pressupost) {
        if (pressupost.getId() == null) {
            return crearService.executar(pressupost);
        }
        return modificarService.executar(pressupost);
    }
}