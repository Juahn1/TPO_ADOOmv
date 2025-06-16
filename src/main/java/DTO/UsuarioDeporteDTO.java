package DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import Model.Nivel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDeporteDTO {
    private Nivel nivel;
    private DeporteDTO deporte;
}
