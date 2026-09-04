module ni.edu.uam.elgueguense_distribuidora {

    requires javafx.controls;
    requires javafx.fxml;

    requires static lombok;

    opens ni.edu.uam.elgueguense_distribuidora.controllers
            to javafx.fxml;

    opens ni.edu.uam.elgueguense_distribuidora.models
            to javafx.base;

    exports ni.edu.uam.elgueguense_distribuidora;
    exports ni.edu.uam.elgueguense_distribuidora.controllers;
    exports ni.edu.uam.elgueguense_distribuidora.models;
}