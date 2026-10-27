public class Habitacion {
    private final int numero;
    private final int planta;
    private final String tipo;
    private final int capacidad;
    private final double precioNoche;
    private final String vista;
    private final String accesible;
    private final String estado;

    public Habitacion(int numero, int planta, String tipo, int capacidad,
                      double precioNoche, String vista, String accesible, String estado) {
        this.numero = numero;
        this.planta = planta;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.precioNoche = precioNoche;
        this.vista = vista;
        this.accesible = accesible;
        this.estado = estado;
    }

    public int getNumero() { return numero; }
    public int getPlanta() { return planta; }
    public String getTipo() { return tipo; }
    public int getCapacidad() { return capacidad; }
    public double getPrecioNoche() { return precioNoche; }
    public String getVista() { return vista; }
    public String getAccesible() { return accesible; }
    public String getEstado() { return estado; }
}
