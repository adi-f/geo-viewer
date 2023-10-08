package adif.geoviewer.application;

import javax.swing.JOptionPane;

public class Startup {
    public static void main(String[] args) {
        SwingUi ui = new SwingUi();
        ui.setOnRunListener(params -> JOptionPane.showMessageDialog(null, params.toString()));
        ui.show();
    }
}
