package learn.solarfarm.models;

import java.util.Objects;

public class SolarPanel {
    private int id;
    private String section;
    private int row;
    private int column;
    private int yearInstalled;
    private Material material;
    private boolean isTracking;
    private int userId;

    public SolarPanel() {
    }

    public SolarPanel(int id, String section, int row, int column, int yearInstalled, Material material, boolean isTracking, int userId) {
        this.id = id;
        this.section = section;
        this.row = row;
        this.column = column;
        this.yearInstalled = yearInstalled;
        this.material = material;
        this.isTracking = isTracking;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getYearInstalled() {
        return yearInstalled;
    }

    public void setYearInstalled(int yearInstalled) {
        this.yearInstalled = yearInstalled;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public boolean isTracking() {
        return isTracking;
    }

    public void setTracking(boolean tracking) {
        isTracking = tracking;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SolarPanel that = (SolarPanel) object;
        return id == that.id && row == that.row && column == that.column && yearInstalled == that.yearInstalled && isTracking == that.isTracking && userId == that.userId && Objects.equals(section, that.section) && material == that.material;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, section, row, column, yearInstalled, material, isTracking, userId);
    }
}