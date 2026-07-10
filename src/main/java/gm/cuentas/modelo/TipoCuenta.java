package gm.cuentas.modelo;

public enum TipoCuenta{
    AHORRO("Ahorro"),
    INVERSION("Inversion"),
    CREDITO("Credito");

    private String descripcion;

    TipoCuenta(String tipoCuenta) {
        this.descripcion = tipoCuenta;
    }

    public String getDescripcion(){
        return descripcion;
    }
}