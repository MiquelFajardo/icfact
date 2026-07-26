package cat.informaticassa.icfact.ui.altaEmpresa.mapper;

import cat.informaticassa.icfact.empresa.model.Empresa;
import cat.informaticassa.icfact.ui.altaEmpresa.view.AltaEmpresaView;

public class EmpresaMapper {
    public Empresa convertir(AltaEmpresaView view) {
        Empresa empresa = new Empresa();
        empresa.setNom(view.getNomEmpresa().getText().trim());
        empresa.setDescripcio(view.getDescripcio().getText().trim());
        empresa.setNif(view.getNif().getText().trim());
        empresa.setTelefon(view.getTelefon().getText().trim());
        empresa.setEmail(view.getEmail().getText().trim());
        empresa.setContrasenyaHash(view.getContrasenya().getText());
        return empresa;
    }
}