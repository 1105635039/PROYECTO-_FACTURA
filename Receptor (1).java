package Facturita;

import java.time.LocalDate;

public class Receptor {
    private String nombres;
    private String apellidos;
    private String cedulaORuc;
    private String numCelular;
    private LocalDate diaCompra;

    public Receptor(String nombres, String apellidos, String cedulaORuc, String numCelular, LocalDate diaCompra) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cedulaORuc = cedulaORuc;
        this.numCelular = numCelular;
        this.diaCompra = diaCompra;
    }

    @Override
    public String toString() {
    String tipoDocumento;

    if (cedulaORuc.length() == 10) {
        tipoDocumento = "CEDULA: " + cedulaORuc;
    } else if (cedulaORuc.length() == 13) {
        tipoDocumento = "RUC: " + cedulaORuc;
    } else {
        tipoDocumento = "Documento desconocido: " +cedulaORuc;
    }
    return 
           "\n| ~ NOMBRES: "+nombres+
           "\n| ~ APELLIDOS: "+apellidos+
           "\n| ~ "+tipoDocumento+
           "\n| ~ NUMERO CELULAR: " +numCelular+
           "\n| ~ FECHA: " +diaCompra;
    }
}