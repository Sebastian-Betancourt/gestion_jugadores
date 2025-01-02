public class Jugador {
    private int id;
    private String nombre;
    private String posicion;
    private String equipo;
    private int edad;

    // Constructor
    public Jugador(int id, String nombre, String posicion, String equipo, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.posicion = posicion;
        this.equipo = equipo;
        this.edad = edad;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPosicion() {
        return posicion;
    }

    public String getEquipo() {
        return equipo;
    }

    public int getEdad() {
        return edad;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
