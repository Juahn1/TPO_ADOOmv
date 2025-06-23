package DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UbicacionDTO {
    private double latitud;
    private double longitud;
    private String direccion;
    private String ciudad;
}
