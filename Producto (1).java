package Facturita;

public class Producto {
    private String codigoProd;
    private String nombre;
    private float precio;
    private int cantidad;

    public Producto(String codigoProd, String nombre, float precio, int cantidad) {
        this.codigoProd = codigoProd;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    public String getcodigoProd(){
        return codigoProd;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void agregarCantidad(int cantidad) {
        this.cantidad += cantidad;
    }

    public boolean vender(int cantidad) {
        if (this.cantidad >= cantidad) {
            this.cantidad -= cantidad;
            return true;
        }
        return false;
    }

    public float total(int cantidad) {
        return (precio * cantidad);
    }
    @Override
    public String toString() {
        return "| "+codigoProd+"  | "+nombre+"   |  $"+precio+"  |  "+cantidad+"  |";
    }
}