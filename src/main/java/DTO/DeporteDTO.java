package DTO;
import lombok.Data;

@Data
public class DeporteDTO {
    private int id;
    private String nombre;
    private int jugadoresMinimos;
    private int jugadoresMaximos;
}