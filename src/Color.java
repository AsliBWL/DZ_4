// Класс "Цвет"
public record Color(String name, short[] rgb) {

    // Конструкторы
    public Color(String name, short[] rgb) {
        this.name = name;        // имя цвета
        this.rgb = rgb.clone();  // RGB-код цвета
    }

    // Getters
    @Override
    public short[] rgb() {
        return this.rgb.clone();
    }

    // Методы

    // Метод получения информации о цвете в формате строки
    @Override
    public String toString() {
        return "Имя цвета: " + this.name + "\n" + "RGB-код цвета: " +
                this.rgb[0] + ", " + this.rgb[1] + ", "  + this.rgb[2] + "\n";
    }

}
