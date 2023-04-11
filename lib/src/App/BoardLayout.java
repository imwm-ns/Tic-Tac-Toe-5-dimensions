package lib.src.App;

import java.awt.*;

public class BoardLayout implements LayoutManager {
    private int hgap;
    private int vgap;

    public BoardLayout() {
        this(0, 0);
    }

    public BoardLayout(int hgap, int vgap) {
        setHgap(hgap);
        setVgap(vgap);
    }

    public int getHgap() {
        return hgap;
    }

    public void setHgap(int hgap) {
        if (hgap < 0) {
            throw new IllegalArgumentException("hgap cannot be negative");
        }
        this.hgap = hgap;
    }

    public int getVgap() {
        return vgap;
    }

    public void setVgap(int vgap) {
        if (vgap < 0) {
            throw new IllegalArgumentException("vgap cannot be negative");
        }
        this.vgap = vgap;
    }

    public void addLayoutComponent(String name, Component comp) {}

    public void removeLayoutComponent(Component comp) {}

    public Dimension preferredLayoutSize(Container parent) {
        int n = parent.getComponentCount();
        if (n == 0) {
            return new Dimension(0, 0);
        }
        int rows = (int) Math.sqrt(n);
        int cols = n / rows + (n % rows == 0 ? 0 : 1);
        int cellWidth = (parent.getWidth() - (cols - 1) * hgap) / cols;
        int cellHeight = (parent.getHeight() - (rows - 1) * vgap) / rows;
        return new Dimension(cols * cellWidth + (cols - 1) * hgap,
                rows * cellHeight + (rows - 1) * vgap);
    }

    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    public void layoutContainer(Container parent) {
        int n = parent.getComponentCount();
        if (n == 0) {
            return;
        }
        int rows = (int) Math.sqrt(n);
        int cols = n / rows + (n % rows == 0 ? 0 : 1);
        int width = parent.getWidth();
        int height = parent.getHeight();
        int cellWidth = (width - (cols - 1) * hgap) / cols;
        int cellHeight = (height - (rows - 1) * vgap) / rows;
        int x = 0;
        int y = 0;
        for (int i = 0; i < n; i++) {
            Component c = parent.getComponent(i);
            x = i % cols * (cellWidth + hgap);
            y = i / cols * (cellHeight + vgap);
            c.setBounds(x, y, cellWidth, cellHeight);
        }
    }
}
