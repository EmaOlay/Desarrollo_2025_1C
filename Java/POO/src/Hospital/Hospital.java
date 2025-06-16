package Hospital;

import java.util.ArrayList;
import java.util.List;

public class Hospital {
    private String nombre;
    private String direccion;
    private List<Area> areas = new ArrayList<>();

    public void agregarArea(Area area) {
        areas.add(area);
    }
    public List<Area> getAreas() {
        return areas;
    }
    public Area getAreaPorNombre(String nombre) {
        for (Area area : areas) {
            if (area.getNombre().equals(nombre)) {
                return area;
            }
        }
        return null;
    }
}
