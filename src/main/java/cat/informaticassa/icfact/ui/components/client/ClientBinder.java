package cat.informaticassa.icfact.ui.components.client;

import cat.informaticassa.icfact.client.model.Client;
import cat.informaticassa.icfact.geografia.model.Adreca;

public class ClientBinder {
    private final ClientPane formulari;

    public ClientBinder(ClientPane formulari) {
        this.formulari = formulari;
    }

    public void carregar(Client client) {
        formulari.getTxtNom().setText(client.getNom());
        formulari.getTxtNomComercial().setText(client.getNomComercial());
        formulari.getTxtNif().setText(client.getNif());
        formulari.getTxtTelefon().setText(client.getTelefon());
        formulari.getTxtMobil().setText(client.getMobil());
        formulari.getTxtEmail().setText(client.getEmail());
        formulari.getTxtWeb().setText(client.getWeb());
        formulari.getTxtObservacions().setText(client.getObservacions());
        formulari.getChkActiu().setSelected(client.isActiu());
        if (client.getAdreca() != null) {
            formulari.getAdrecaPane().mostrar(client.getAdreca());
        }
    }

    public void actualitzar(Client client) {
        client.setNom(formulari.getTxtNom().getText().trim());
        client.setNomComercial(formulari.getTxtNomComercial().getText().trim());
        client.setNif(formulari.getTxtNif().getText().trim());
        client.setTelefon(formulari.getTxtTelefon().getText().trim());
        client.setMobil(formulari.getTxtMobil().getText().trim());
        client.setEmail(formulari.getTxtEmail().getText().trim());
        client.setWeb(formulari.getTxtWeb().getText().trim());
        client.setObservacions(formulari.getTxtObservacions().getText().trim());
        client.setActiu(formulari.getChkActiu().isSelected());
        Adreca adreca = client.getAdreca();
        if (adreca == null) {
            adreca = new Adreca();
        }
        formulari.getAdrecaPane().actualitzar(adreca);
        client.setAdreca(adreca);
    }
}