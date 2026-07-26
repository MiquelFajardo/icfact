package cat.informaticassa.icfact.ui.components;

import cat.informaticassa.icfact.BuildInfo;
import cat.informaticassa.icfact.ConstantsAplicacio;
import cat.informaticassa.icfact.ui.tema.Tema;
import cat.informaticassa.icfact.ui.util.UiUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FooterInformaticassa extends VBox {
    private static final Logger logger = LoggerFactory.getLogger(FooterInformaticassa.class);


    public FooterInformaticassa() {
        setAlignment(Pos.CENTER);
        setSpacing(5);
        Label desenvolupat = new Label("Desenvolupat per");
        desenvolupat.setFont(Tema.TEXT_NORMAL);
        desenvolupat.setTextFill(Tema.TEXT_SECUNDARI);
        Hyperlink informaticassa = new Hyperlink(ConstantsAplicacio.DESENVOLUPADOR);
        informaticassa.setFont(Tema.ETIQUETA);
        informaticassa.setBorder(null);
        informaticassa.setOnAction(e -> UiUtils.obrirWeb(ConstantsAplicacio.WEB));
        Label versio = new Label(BuildInfo.getNomAplicacio() + " v" + BuildInfo.getVersio());
        versio.setFont(Tema.TEXT_NORMAL);
        versio.setTextFill(Tema.TEXT_SECUNDARI);
        getChildren().addAll(desenvolupat, informaticassa, versio);
    }

}