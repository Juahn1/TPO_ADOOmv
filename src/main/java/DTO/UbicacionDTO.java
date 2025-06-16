package DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionDTO {
    private double latitud;
    private double longitud;
    private String direccion;
    private String ciudad;
}
