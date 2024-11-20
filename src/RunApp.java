import view.AppView;

import javax.swing.*;

public class RunApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppView app = new AppView();
            app.setVisible(true);
        });
    }
}
