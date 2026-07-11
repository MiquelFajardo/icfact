package cat.informaticassa.icfact.infraestructura.repository;

import java.util.List;
import java.util.Optional;

public interface Repository <T,ID>{
    void guardar(T entitat);

    void actualitzar(T entitat);

    Optional<T> buscarPerId(ID id);

    List<T> buscarTots();

    void eliminar(T entitat);

    void activar(T enitat);

}
