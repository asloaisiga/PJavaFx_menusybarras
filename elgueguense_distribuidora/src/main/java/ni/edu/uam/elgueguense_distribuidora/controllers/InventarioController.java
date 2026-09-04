package ni.edu.uam.elgueguense_distribuidora.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import ni.edu.uam.elgueguense_distribuidora.dao.InventarioDao;
import ni.edu.uam.elgueguense_distribuidora.models.Producto;

public class InventarioController {

    private final InventarioDao inventarioDao = new InventarioDao();
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox<String> cbCategoria;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtExistencia;
    @FXML
    private TableView<Producto> tbProductos;
    @FXML
    private TableColumn<Producto, String> colCodigo;
    @FXML
    private TableColumn<Producto, String> colNombre;
    @FXML
    private TableColumn<Producto, String> colCategoria;
    @FXML
    private TableColumn<Producto, Double> colPrecio;
    @FXML
    private TableColumn<Producto, Integer> colExistencia;
    @FXML
    private Label lblEstado;
    @FXML
    private MenuItem menuNuevo;
    @FXML
    private MenuItem menuGuardar;
    @FXML
    private MenuItem menuSalir;
    @FXML
    public void initialize() {
        cbCategoria.getItems().addAll("Alimentos", "Bebidas", "Limpieza", "Hogar", "Cuidado personal");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<>("existencia"));
        tbProductos.setItems(inventarioDao.obtenerProductos());
        configurarAtajos();
        tbProductos.getSelectionModel().selectedItemProperty().addListener((observable, anterior, seleccionado)
                -> {
                    if (seleccionado != null) {cargarProducto(seleccionado);}
                });
    }

    private void configurarAtajos() {
        menuNuevo.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN)
        );
        menuGuardar.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN)
        );
        menuSalir.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN)
        );
    }


    @FXML
    private void nuevoProducto() {
        limpiarCampos();
        tbProductos.getSelectionModel().clearSelection();
        txtCodigo.requestFocus();
        lblEstado.setText("Nuevo registro.");
    }

    @FXML
    private void limpiarFormulario() {
        limpiarCampos();
        tbProductos.getSelectionModel().clearSelection();
        lblEstado.setText("Formulario limpiado.");
    }

    @FXML
    private void guardarProducto() {
        if (!validarCampos()) {
            return;
        }
        String codigo = txtCodigo.getText().trim();

        if (buscarProducto(codigo) != null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Código existente",
                    "Ya existe un producto con ese código."
            );
            return;
        }

        Producto producto = new Producto(codigo, txtNombre.getText().trim(), cbCategoria.getValue(),
                Double.parseDouble(txtPrecio.getText().trim()),
                Integer.parseInt(txtExistencia.getText().trim())
        );
        inventarioDao.agregar(producto);
        lblEstado.setText("Producto guardado correctamente.");
        limpiarCampos();
    }


    @FXML
    private void editarProducto() {
        Producto seleccionado = tbProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            sinSeleccion("editar");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        String codigo = txtCodigo.getText().trim();
        Producto encontrado = buscarProducto(codigo);
        if (
                encontrado != null && encontrado != seleccionado) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Código existente",
                    "Ese código pertenece a otro producto."
            );
            return;
        }
        seleccionado.setCodigo(codigo);

        seleccionado.setNombre(txtNombre.getText().trim());

        seleccionado.setCategoria(cbCategoria.getValue());

        seleccionado.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));

        seleccionado.setExistencia(Integer.parseInt(txtExistencia.getText().trim()));

        tbProductos.refresh();

        lblEstado.setText("Producto editado correctamente.");
        limpiarCampos();
        tbProductos.getSelectionModel().clearSelection();
    }


    @FXML
    private void eliminarProducto() {

        Producto seleccionado =
                tbProductos.getSelectionModel()
                        .getSelectedItem();

        if (seleccionado == null) {

            sinSeleccion("eliminar");

            return;
        }

        Alert confirmacion =
                new Alert(Alert.AlertType.CONFIRMATION);

        confirmacion.setTitle("Eliminar producto");

        confirmacion.setHeaderText("Confirmar eliminación");

        confirmacion.setContentText("¿Desea eliminar " + seleccionado.getNombre() + "?");

        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            inventarioDao.eliminar(seleccionado);
            limpiarCampos();
            lblEstado.setText("Producto eliminado.");
        }
    }


    @FXML
    private void verDetalle() {
        Producto seleccionado = tbProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            sinSeleccion("ver el detalle");
            return;
        }

        String informacion =
                "Código: "
                        + seleccionado.getCodigo()
                        + "\nNombre: "
                        + seleccionado.getNombre()
                        + "\nCategoría: "
                        + seleccionado.getCategoria()
                        + "\nPrecio: C$ "
                        + String.format(
                        "%.2f",
                        seleccionado.getPrecio()
                )
                        + "\nExistencia: "
                        + seleccionado.getExistencia();

        mostrarAlerta(Alert.AlertType.INFORMATION, "Detalle del producto", informacion);
        lblEstado.setText("Detalle mostrado.");
    }


    @FXML
    private void acercaDe() {

        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alert.setTitle(
                "Acerca de"
        );

        alert.setHeaderText(
                "Distribuidora El Güegüense"
        );

        alert.setContentText(
                "Sistema de inventario desarrollado en JavaFX.\n\n"
                        + "Autor: Alexa Sofia Loaisiga Torrez"
        );

        alert.showAndWait();
    }


    @FXML
    private void salir() {

        Alert confirmacion =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmacion.setTitle(
                "Salir"
        );

        confirmacion.setHeaderText(
                "Cerrar aplicación"
        );

        confirmacion.setContentText(
                "¿Está seguro de que desea salir?"
        );

        if (confirmacion.showAndWait()
                .orElse(ButtonType.CANCEL)
                == ButtonType.OK) {

            Platform.exit();
        }
    }


    private void cargarProducto(
            Producto producto) {

        txtCodigo.setText(producto.getCodigo());

        txtNombre.setText(producto.getNombre());

        cbCategoria.setValue(producto.getCategoria());

        txtPrecio.setText(String.valueOf(producto.getPrecio()));

        txtExistencia.setText(String.valueOf(producto.getExistencia()));

        lblEstado.setText("Producto seleccionado: " + producto.getNombre());
    }


    private Producto buscarProducto(String codigo) {
        for (Producto producto : inventarioDao.obtenerProductos()) {

            if (producto.getCodigo().equalsIgnoreCase(codigo)) {
                return producto;
            }
        }
        return null;
    }


    private boolean validarCampos() {
        if (txtCodigo.getText().isBlank()
                || txtNombre.getText().isBlank()
                || cbCategoria.getValue() == null
                || txtPrecio.getText().isBlank()
                || txtExistencia.getText().isBlank()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING, "Campos incompletos", "Complete todos los datos del producto."
            );
            return false;
        }

        try {

            double precio =
                    Double.parseDouble(txtPrecio.getText().trim());

            int existencia =
                    Integer.parseInt(txtExistencia.getText().trim());

            if (precio <= 0) {

                mostrarAlerta(
                        Alert.AlertType.WARNING, "Precio inválido", "El precio debe ser mayor que cero."
                );

                return false;
            }

            if (existencia < 0) {

                mostrarAlerta(
                        Alert.AlertType.WARNING, "Existencia inválida", "La existencia no puede ser negativa."
                );

                return false;
            }

        } catch (NumberFormatException e) {
            mostrarAlerta(
                    Alert.AlertType.ERROR, "Datos incorrectos", "Ingrese un precio y existencia válidos."
            );
            return false;
        }
        return true;
    }


    private void sinSeleccion(String accion) {
        mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccione un producto antes de "
                + accion + ".");
        lblEstado.setText("No hay ningún producto seleccionado.");
    }


    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        cbCategoria.setValue(null);
        txtPrecio.clear();
        txtExistencia.clear();
    }

    private void mostrarAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}