module ni.edu.uam.elgueguense_distribuidora {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens ni.edu.uam.elgueguense_distribuidora to javafx.fxml;
    exports ni.edu.uam.elgueguense_distribuidora;
}