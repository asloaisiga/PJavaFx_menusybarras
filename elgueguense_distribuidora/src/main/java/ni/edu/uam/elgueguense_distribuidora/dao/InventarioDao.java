package ni.edu.uam.elgueguense_distribuidora.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ni.edu.uam.elgueguense_distribuidora.models.Producto;

public class InventarioDao {

    private final ObservableList<Producto> productos =
            FXCollections.observableArrayList();

    public ObservableList<Producto> obtenerProductos() {
        return productos;
    }

    public void agregar(Producto producto) {
        productos.add(producto);
    }

    public void eliminar(Producto producto) {
        productos.remove(producto);
    }
}