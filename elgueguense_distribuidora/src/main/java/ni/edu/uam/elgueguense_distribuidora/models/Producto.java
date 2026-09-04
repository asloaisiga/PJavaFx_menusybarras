package ni.edu.uam.elgueguense_distribuidora.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    private String codigo;
    private String nombre;
    private String categoria;
    private double precio;
    private int existencia;
}
