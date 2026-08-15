package cat.informaticassa.icfact.infraestructura.repository;

import java.util.Optional;

public interface CrudRepository<T, ID>{
    void guardar(T entitat);
    void actualitzar(T entitat);
    Optional<T> buscarPerId(ID id);
}
