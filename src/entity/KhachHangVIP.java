package entity;

public class KhachHangVIP {
  private String name;
  private double max;

  public KhachHangVIP() {
    super();
  }

  public KhachHangVIP(String name, double max) {
    super();
    this.name = name;
    this.max = max;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getMax() {
    return max;
  }

  public void setMax(double max) {
    this.max = max;
  }
}
